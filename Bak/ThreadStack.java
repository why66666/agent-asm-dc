package com.weaver.agent.asm.entity;

import java.util.Stack;

/**
 * @author w
 * @date 2020-01-16 01:25
 */
public class ThreadStack {
    private static ThreadLocal<Stack<Span>> threadStack = new ThreadLocal<Stack<Span>>(){
        @Override
        protected Stack<Span> initialValue() {
            return new Stack<>();
        }
    };
    public static Stack get(){
        return threadStack.get();
    }
    public static void set(Span span){
        threadStack.get().push(span);
    }
    public static void initial(){
        threadStack.set(new Stack<>());
    }
}
