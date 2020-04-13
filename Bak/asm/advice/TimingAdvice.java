package com.weaver.agent.asm.advice;


import com.weaver.agent.asm.depend.MethodUtil;
import net.bytebuddy.asm.Advice;


/**
 * @author w
 * @date 2019-12-02 01:00
 */
public class TimingAdvice {
    @Advice.OnMethodEnter()
    public static long enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        return MethodUtil.methodStart(className, methodName);
    }

    @Advice.OnMethodExit()
    public static void exit( @Advice.Enter long startTime) {
        MethodUtil.methodEnd(startTime);
    }
}

