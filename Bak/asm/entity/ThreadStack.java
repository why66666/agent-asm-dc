package com.weaver.agent.asm.entity;

import java.util.Stack;

/**
 * @author w
 * @date 2020-02-11 12:15
 */
public class ThreadStack {
    /*
    辅助栈
     */
    private static ThreadLocal<Stack<Span>> threadStack = new ThreadLocal<Stack<Span>>(){
        @Override
        protected Stack<Span> initialValue() {
            return new Stack<>();
        }
    };
    public static Stack<Span> get(){
        return threadStack.get();
    }
    public static void set(Span span){
        threadStack.get().push(span);
    }
    public static void initial(){
        threadStack.set(new Stack<>());
    }
}
