package com.weaver.agent.asm.depend;


import com.weaver.agent.asm.entity.*;
import com.weaver.agent.asm.task.SendTrackTask;
import com.weaver.agent.asm.util.ClientConstant;
import com.weaver.agent.asm.util.PropUtil;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MethodUtil {
    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static long methodStart(String className, String methodName) {
        Stack<Span> spanStack = ThreadStack.get();
        //过滤配置的类调用的所有方法不收集数据
        if(!spanStack.empty()){
            List<String> list = PropUtil.getListValue("data.ban");
            if(list!=null){
                for(String s:list){
                    if(DataLocal.get().index.get(spanStack.peek().getP()).contains(s)){
                        return -2;
                    }
                }
            }
        }

        HashMap<String, SpanData> dataHashMap = DataLocal.get();
        TrackCounter.set();
        className = className.replaceAll("/", ".");
        String spanname = className + "." + methodName;
        SpanData spanData = dataHashMap.get(spanname);
        if (!dataHashMap.isEmpty()) {
            int size = dataHashMap.size();
            String l = spanStack.empty() ? "" : spanStack.peek().getL() + "_";
            if (spanData != null) {
                ThreadStack.set(new Span(TrackCounter.get(), spanData.getI(), l + spanData.getI(),spanStack.size()+1,0,1));
            } else {
                ThreadStack.set(new Span(TrackCounter.get(), size, l + size,spanStack.size()+1,0,1));
                DataLocal.set(spanname, new SpanData(size));
            }
        } else {
            ThreadStack.set(new Span(TrackCounter.get(), 0, "0",spanStack.size()+1,0,1));
            DataLocal.set(spanname, new SpanData(0));
        }
        return System.currentTimeMillis();
    }

    public static void methodEnd(long startTime) {
        //过滤配置的类调用的所有方法不收集数据
        if (startTime == -2) {
            return;
        }

        long runtime = System.currentTimeMillis() - startTime;
        Stack stack = ThreadStack.get();
        if (stack == null || stack.empty()) {
            System.out.println("null-Stack");
        } else {
            Span span = ((Span) stack.pop());
            IndexHashMap<String, Span> track = Track.get();
            IndexHashMap<String, SpanData> data = DataLocal.get();
            Span tmp = track.get(span.getL());
            int tlen = track.size();
            //调用链判断
            if (tmp != null) {
                //相同元素位置
                Integer inn = track.getIndex(span.getL());
                //尾部元素栈深判断
                if (track.getByIndex(tlen - 1).getD() > span.getD()) {
                    //子元素插入位置
                    int in = tlen - 1;
                    while (true) {
                        if (track.getByIndex(in--).getS() < span.getS()) {
                            break;
                        }
                    }
                    for (int j = inn - 1; j > -1; j--) {
                        Span tmp1 = track.getByIndex(j);
                        if (tmp1.getS() > tmp.getS()) {
                            track.put(in--, tmp1.getL(), track.remove(tmp1.getL()));
                            inn--;
                        } else {
                            break;
                        }
                    }
                    Span inns = track.getByIndex(inn);
                    inns.setT(runtime);
                    inns.addcount();
                    track.put(inns.getL(), track.remove(inns.getL()));
                }else {
                    Span inns = track.getByIndex(inn);
                    inns.setT(runtime);
                    inns.addcount();
                    Set<String> spanset = Track.sqlData.get().get(span.getS());
                    if(spanset!=null&&spanset.size()>0){
                        Set<String> innsset = Track.sqlData.get().get(inns.getS());
                        if(innsset!=null){
                            innsset.addAll(Track.sqlData.get().remove(span.getS()));
                        }else {
                            Track.sqlData.get().put(inns.getS(),Track.sqlData.get().remove(span.getS()));
                        }
                    }
                }
            } else {
                span.setT(runtime);
                track.put(span.getL(), span);
            }
            //完全出栈发送数据
            if (!TrackCounter.requesturl.get().isEmpty() && stack.empty() && TrackCounter.staticEx.get()) {
                System.out.println("size:" + track.size() + "---" + data.size());
                executor.submit(new SendTrackTask(ClientConstant.CONS_INFO.get(2),TrackCounter.requesturl.get(),track,Track.sqlData.get(),track.index,data));
            }
        }
    }

}
