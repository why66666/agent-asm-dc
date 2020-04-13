package com.weaver.agent.asm.trace;

import net.bytebuddy.asm.Advice;

/**
 * @author w
 * @date 2020-03-16 10:30
 */
public class FilterAdvice {
    @Advice.OnMethodEnter()
    public static void enter(@Advice.Argument(value = 0) Object req) {
        FilterAop.methodBefore(req);
    }
}
