package com.weaver.agent.asm.filecheck;

import net.bytebuddy.asm.Advice;

import java.io.File;

public class FileDelAdvice {
    @Advice.OnMethodEnter()
    public static void enter(@Advice.This() File file) {
        FileAop.methodBefore(file);
    }
}
