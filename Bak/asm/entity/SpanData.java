package com.weaver.agent.asm.entity;

/**
 * @author w
 * @date 2020-02-11 12:13
 */
public class SpanData {
    /*
    id
     */
    private int i;
    public SpanData() {
    }

    public SpanData(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "SpanData{" +
                "i=" + i +
                '}';
    }
}
