package com.weaver.agent.asm.entity;

import java.util.List;

/**
 * @author w
 * @date 2020-01-16 09:30
 */
public class TrackCounter {
    /*
    排除静态资源请求标记
     */
    public static ThreadLocal<Boolean> staticEx = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return true;
        }
    };
    /*
    请求url
     */
    public static ThreadLocal<String> requesturl = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return "";
        }
    };
    /*
    调用链计数器，为span提供id
     */
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
