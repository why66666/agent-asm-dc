package com.weaver.agent.asm.trace;


import com.weaver.agent.asm.collection.IndexHashMap;

import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

/**
 * @author w
 * @date 2020-03-14 15:52
 */
public class ThreadLocalData {
    /**
    辅助栈
     */
    static ThreadLocal<Stack<Span>> methodStack = new ThreadLocal<Stack<Span>>() {
        @Override
        protected Stack<Span> initialValue() {
            return new Stack<>();
        }

        @Override
        public Stack<Span> get() {
            return super.get();
        }
    };
    /**
     方法数据Map<方法名,数据>
     */
    static ThreadLocal<IndexHashMap<String,MethodData>> methodMap = new ThreadLocal<IndexHashMap<String,MethodData>>() {
        @Override
        protected IndexHashMap<String,MethodData> initialValue() {
            return new IndexHashMap<String,MethodData>();
        }

        @Override
        public IndexHashMap<String, MethodData> get() {
            return super.get();
        }
    };
    /**
     Trace数据<调用链,TraceSpan>
     */
    static ThreadLocal<IndexHashMap<String, TraceSpan>> traceMap = new ThreadLocal<IndexHashMap<String,TraceSpan>>() {
        @Override
        protected IndexHashMap<String,TraceSpan> initialValue() {
            return new IndexHashMap<String,TraceSpan>();
        }

        @Override
        public IndexHashMap<String, TraceSpan> get() {
            return super.get();
        }
    };
    /**
     *调用链计数器，为span提供id
     */
    static ThreadLocal<Integer> spanCounter = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }

        @Override
        public Integer get() {
            return super.get();
        }
    };
    /**
     *sql
     */
    public static ThreadLocal<HashMap<Integer, Set<String>>> sqlData = new ThreadLocal<HashMap<Integer,Set<String>>>(){
        @Override
        protected HashMap<Integer,Set<String>> initialValue() {
            return new HashMap<Integer,Set<String>>();
        }
    };
    /**
     *请求url
     */
    public static ThreadLocal<String> requesturl = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return "";
        }
    };

    public static ThreadLocal<Stack<Span>> getMethodStack() {
        return methodStack;
    }

    static void addCount() {
        spanCounter.set(spanCounter.get()+1);
    }
}
