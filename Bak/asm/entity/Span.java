package com.weaver.agent.asm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author w
 * @date 2020-02-11 12:11
 */
@JsonIgnoreProperties({"d"})
public class Span {
    /*
    spanid
     */
    private int s;
    /*
    指向spandata id
     */
    private int p;
    /*
    调用链
     */
    private String l;
    /*
    栈深
     */
    private int d;
    /*
    执行时间
     */
    private long t;
    /*
    循环次数
     */
    private int c = 1;
    public Span() {
    }

    public Span(int s, int p, String l, int d, long t, int c) {
        this.s = s;
        this.p = p;
        this.l = l;
        this.d = d;
        this.t = t;
        this.c = c;
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

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public void setT(long t) {
        this.t = this.t + t;
    }

    public void addcount() {
        this.c++;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "Span{" +
                "s=" + s +
                ", p=" + p +
                ", l='" + l + '\'' +
                ", d=" + d +
                ", t=" + t +
                ", c=" + c +
                '}';
    }
}
