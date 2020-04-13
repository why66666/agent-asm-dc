package com.weaver.agent.asm.trace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author w
 * @date 2020-03-14 15:59
 */

public class Span {
    /**
     * spanId
     */
    private int s;
    /**
     * 指向spandata id
     */
    private int p;
    /**
     * 调用链
     */
    private String l;
    /**
     * 执行时间
     */
    private long t;
    /**
     * 循环次数
     */
    private int c = 1;


    public Span(int s, int p, String l, long t) {
        this.s = s;
        this.p = p;
        this.l = l;
        this.t = t;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Span{" +
                "s=" + s +
                ", p=" + p +
                ", l='" + l + '\'' +
                ", t=" + t +
                ", c=" + c +
                '}';
    }
}
