package com.weaver.agent.asm.entity;

import java.util.*;

/**
 * @author w
 * @date 2020-01-16 01:12
 */
public class Span {
    public int getSpanId() {
        return spanId;
    }

    public void setSpanId(int spanId) {
        this.spanId = spanId;
    }

    public String getSpanName() {
        return spanName;
    }

    public void setSpanName(String spanName) {
        this.spanName = spanName;
    }

    public long getRunTime() {
        return runTime;
    }

    public Span setRunTime(long runTime) {
        this.runTime = runTime;
        return this;
    }


    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public List<Integer> getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index.add(index);
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public Span(int spanId, String spanName, int line, long runTime, int pointer, List<Integer> index) {
        this.spanId = spanId;
        this.spanName = spanName;
        this.line = line;
        this.runTime = runTime;
        this.pointer = pointer;
        this.index = index;
    }

    @Override
    public String toString() {
        return "{" +
                "\"spanId\":\"" + spanId +"\""+
                ",\"spanName\":\"" + spanName + "\""+
                ",\"line\":\"" + line + "\"" +
                ",\"runTime\":\"" + runTime + "\"" +
                ",\"pointer\":\"" + pointer + "\"" +
                ",\"index\":\"" + index + "\"" +
                '}';
    }

    private int spanId;
    private String spanName;
    private int line;
    private long runTime;
    private int pointer;
    private List<Integer> index;
}
