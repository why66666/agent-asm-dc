package com.weaver.agent.asm.entity;

import java.util.*;

/**
 * @author w
 * @date 2020-01-16 01:25
 */
public class Track {
    /*
    trace数据IndexHashMap<调用栈，span>
     */
    private static ThreadLocal<IndexHashMap<String,Span>> track = new ThreadLocal<IndexHashMap<String,Span>>(){
        @Override
        protected IndexHashMap<String,Span> initialValue() {
            return new IndexHashMap<String,Span>();
        }
    };
    /*
    sql
     */
    public static ThreadLocal<HashMap<Integer,Set<String>>> sqlData = new ThreadLocal<HashMap<Integer,Set<String>>>(){
        @Override
        protected HashMap<Integer,Set<String>> initialValue() {
            return new HashMap<Integer,Set<String>>();
        }
    };
    public static IndexHashMap<String,Span> get(){
        return track.get();
    }
    public static void set(String pd,Span span){
        track.get().put(pd,span);
    }
    public static void initial(){
        track.set(new IndexHashMap<String,Span>());
    }
}
