package com.weaver.agent.asm.trace;

import net.bytebuddy.asm.Advice;

/**
 * @author w
 * @date 2020-03-16 09:47
 */
public class SqlAdvice {
    @Advice.OnMethodEnter()
    public static void enter(@Advice.Argument(value = 0) String s) {
        RecordSetAop.setSqls(s);
    }
}
