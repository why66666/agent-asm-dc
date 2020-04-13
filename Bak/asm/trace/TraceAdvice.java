package com.weaver.agent.asm.trace;

import com.weaver.agent.asm.depend.MethodUtil;
import net.bytebuddy.asm.Advice;

/**
 * @author w
 * @date 2020-03-14 15:21
 */
public class TraceAdvice {
    @Advice.OnMethodEnter()
    public static long enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        return TraceAop.methodBefore(className, methodName);
    }

    @Advice.OnMethodExit()
    public static void exit( @Advice.Enter long startTime) {
        TraceAop.methodAfter(startTime);
    }
}
