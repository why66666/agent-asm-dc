package com.weaver.agent.asm.entity;

import java.util.*;

/**
 * @author w
 * @date 2020-02-11 12:19
 */
public class DataLocal {
    /*
    DataLocal<spanname,SpanData>
     */
    private static ThreadLocal<IndexHashMap<String,SpanData>> track = new ThreadLocal<IndexHashMap<String,SpanData>>(){
        @Override
        protected IndexHashMap<String,SpanData> initialValue() {
            return new IndexHashMap<String,SpanData>();
        }
    };
    public static IndexHashMap<String,SpanData> get(){
        return track.get();
    }
    public static void set(String spanname,SpanData spanData){
        track.get().put(spanname,spanData);
    }
    public static void initial(){
        track.set(new IndexHashMap<String,SpanData>());
    }
}
