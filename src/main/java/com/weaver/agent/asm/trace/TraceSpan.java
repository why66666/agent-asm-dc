package com.weaver.agent.asm.trace;

/**
 * @author w
 * TraceSpan 最终发送trace节点数据 除去调用链
 * @date 2020-03-14 17:09
 */
public class TraceSpan {
    /**
     * spanId
     */
    private int s;
    /**
     * 指向spandata id
     */
    private int p;
    /**
     * 执行时间
     */
    private long t;
    /**
     * 循环次数
     */
    private int c = 1;

    public TraceSpan(Span span) {
        this.s = span.getS();
        this.p = span.getP();
        this.t = span.getT();
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

    void addC() {
        this.setC(this.getC()+1);
    }

    @Override
    public String toString() {
        return "TraceSpan{" +
                "s=" + s +
                ", p=" + p +
                ", t=" + t +
                ", c=" + c +
                '}';
    }
}
