package com.weaver.agent.asm.trace;


import com.weaver.agent.asm.entity.IndexHashMap;
import com.weaver.agent.asm.entity.TrackCounter;
import com.weaver.agent.asm.task.SendTraceTask;
import com.weaver.agent.asm.util.ClientConstant;
import com.weaver.agent.asm.util.PropUtil;


import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author w
 * @date 2020-03-14 15:48
 */
public class TraceAop {
    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static long methodBefore(String className, String methodName) {
        Stack<Span> spanStack = ThreadLocalData.methodStack.get();
        IndexHashMap<String, MethodData> methodDataMap = ThreadLocalData.methodMap.get();
        //过滤配置的类调用的所有方法不收集数据
        if(!spanStack.empty()){
            List<String> list = PropUtil.getListValue("data.ban");
            if(list!=null){
                for(String s:list){
                    if(methodDataMap.index.get(spanStack.peek().getP()).contains(s)){
                        return -1;
                    }
                }
            }
        }

        ThreadLocalData.addCount();
        int count = ThreadLocalData.spanCounter.get();
        String spanname = className + "." + methodName;
        MethodData methodData = methodDataMap.get(spanname);
        if (!methodDataMap.isEmpty()) {
            int size = methodDataMap.size();
            String l = spanStack.empty() ? "" : spanStack.peek().getL() + "_";
            if (methodData != null) {
                spanStack.push(new Span(count, methodData.getP(), l + methodData.getP(), 0));
            } else {
                spanStack.push(new Span(count, size, l + size, 0));
                methodDataMap.put(spanname, new MethodData(size));
            }
        } else {
            spanStack.push(new Span(count, 0, "0", 0));
            methodDataMap.put(spanname, new MethodData(0));
        }
        return System.currentTimeMillis();
    }

    public static void methodAfter(long startTime) {
        //过滤配置的类调用的所有方法不收集数据
        if (startTime == -1) {
            return;
        }
        long runtime = System.currentTimeMillis() - startTime;
        Stack<Span> spanStack = ThreadLocalData.methodStack.get();
        IndexHashMap<String, TraceSpan> trace = ThreadLocalData.traceMap.get();
        IndexHashMap<String, MethodData> methodDataMap = ThreadLocalData.methodMap.get();
        if (spanStack == null || spanStack.empty()) {
            System.out.println("null-Stack");
        } else {
            Span span = (spanStack.pop());
            span.setT(runtime);
            TraceSpan tmp = trace.get(span.getL());
            if (tmp != null) {
                tmp.setS(span.getS());
                tmp.setT(tmp.getT() + runtime);
                tmp.addC();
            } else {
                trace.put(span.getL(), new TraceSpan(span));
            }

            //完全出栈发送数据
            if (!TrackCounter.requesturl.get().isEmpty() && spanStack.empty() && TrackCounter.staticEx.get()) {
                System.out.println("size:" + trace.size() + "---" + methodDataMap.size());

                executor.submit(new SendTraceTask(ClientConstant.CONS_INFO.get(2), trace, methodDataMap, TrackCounter.requesturl.get(),ThreadLocalData.sqlData.get()));
            }
        }
    }
}
