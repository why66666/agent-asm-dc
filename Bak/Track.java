package com.weaver.agent.asm.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author w
 * @date 2020-01-16 01:25
 */
public class Track {
    private static ThreadLocal<List<Span>> track = new ThreadLocal<List<Span>>(){
        @Override
        protected List<Span> initialValue() {
            return new ArrayList<>();
        }
    };
    public static List get(){
        return track.get();
    }
    public static void set(Span span){
        track.get().add(span);
    }
    public static void initial(){
        track.set(new ArrayList<>());
    }
}
