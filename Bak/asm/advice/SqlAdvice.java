package com.weaver.agent.asm.advice;

import com.weaver.agent.asm.depend.MethodUtil;
import com.weaver.agent.asm.depend.SqlUtil;
import com.weaver.agent.asm.entity.ThreadStack;
import com.weaver.agent.asm.entity.Track;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author w
 * @date 2020-03-05 16:32
 */
public class SqlAdvice {
    @Advice.OnMethodEnter()
    public static void enter(@Advice.Argument(value = 0) String s) {
        SqlUtil.setSqls(s);
    }
}
