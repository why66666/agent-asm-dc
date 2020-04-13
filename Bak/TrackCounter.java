package com.weaver.agent.asm.entity;

import java.util.List;

/**
 * @author w
 * @date 2020-01-16 09:30
 */
public class TrackCounter {
    private static ThreadLocal<Integer> counter = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    public static Integer get(){
        return counter.get();
    }
    public static void set(){
        counter.set(counter.get()+1);
    }
    public static void initial(){
        counter.set(0);
    }
}
