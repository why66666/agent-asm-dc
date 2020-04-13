package com.weaver.agent.asm.task;

import com.weaver.agent.asm.advice.FilterAdvice;
import com.weaver.agent.asm.trace.SqlAdvice;
import com.weaver.agent.asm.trace.TraceAdvice;
import com.weaver.agent.asm.util.PropUtil;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.util.List;

/**
 * @author w
 * @date 2020-02-18 14:03
 */
public class InstTask implements Runnable {
    private Instrumentation inst;

    public InstTask(Instrumentation inst) {
        this.inst = inst;
    }

    @Override
    public void run() {
        List<String> allowList = PropUtil.getListValue("element.allow");
        List<String> banList = PropUtil.getListValue("element.ban");
        System.out.println(allowList);
        System.out.println(banList);
        ElementMatcher.Junction<NamedElement> allowMatcher = ElementMatchers.nameContains("weaver.");
        ElementMatcher.Junction<NamedElement> banMatcher = ElementMatchers.not(ElementMatchers.nameContains("com.weaver.agent.asm."));
        if(allowList!=null){
            for (String s : allowList) {
                System.out.println("element.allow============================="+s);
                allowMatcher = allowMatcher.or(ElementMatchers.nameContains(s));
            }
        }
        if(banList!=null){
            for (String s : banList) {
                System.out.println("element.ban============================="+s);
                banMatcher = banMatcher.and(ElementMatchers.not(ElementMatchers.nameContains(s)));
            }
        }
        AgentBuilder agentBuilder = new AgentBuilder.Default()
                .disableClassFormatChanges()
                .type(ElementMatchers.any())
                //包名类名过滤
                .and(allowMatcher)
                .and(banMatcher)
                .transform(((builder, typeDescription, classLoader, javaModule) -> builder.visit(
                        Advice.to(TraceAdvice.class)
                                .on(ElementMatchers
                                        //方法名过滤
                                        .isMethod().and(ElementMatchers.not(ElementMatchers.nameContains("<init>")))
                                )))).transform(((builder, typeDescription, classLoader, javaModule) -> builder.visit(
                        Advice.to(FilterAdvice.class)
                                .on(ElementMatchers
                                        .isMethod().and(ElementMatchers.nameContains("doFilter"))
                                )))).transform(((builder, typeDescription, classLoader, javaModule) -> builder.visit(
                        Advice.to(SqlAdvice.class)
                                .on(ElementMatchers
                                        .isMethod().and(ElementMatchers.nameContains("executeSql")))))).asDecorator();
        agentBuilder.installOn(inst);
    }
}
