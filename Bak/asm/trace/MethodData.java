package com.weaver.agent.asm.trace;

/**
 * @author w
 * @date 2020-03-14 16:15
 */
public class MethodData {
    private int p = 0;

    public MethodData(int p) {
        this.p = p;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "MethodData{" +
                "p=" + p +
                '}';
    }
}
