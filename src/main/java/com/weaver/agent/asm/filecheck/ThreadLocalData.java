package com.weaver.agent.asm.filecheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ThreadLocalData {
    /**
     *请求url
     */
    static ThreadLocal<Map<Integer,Set<String>>> filedelSpan = new ThreadLocal<Map<Integer,Set<String>>>(){
        @Override
        protected Map<Integer,Set<String>> initialValue() {
            return new HashMap<Integer,Set<String>>();
        }
    };


    public static ThreadLocal<Map<Integer,Set<String>>> getFiledelSpan() {
        return filedelSpan;
    }
}
