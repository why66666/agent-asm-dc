package com.weaver.agent.asm;


import weaver.dscanAgent.org.objectweb.asm.ClassReader;
import weaver.dscanAgent.org.objectweb.asm.ClassWriter;
import weaver.dscanAgent.org.objectweb.asm.Opcodes;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ScanSqlTrans implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.indexOf("weaver") != -1 || className.indexOf("com/cloudstore/") != -1 || className.indexOf("com/api/") != -1 || className.indexOf("com/converter/") != -1 || className.indexOf("com/customization/") != -1 || className.indexOf("com/engine/") != -1 || className.indexOf("com/goldgrid/") != -1 || className.indexOf("com/simplerss/") != -1 || className.indexOf("com/wellcom/") != -1 || className.indexOf("wscheck/") != -1) {
            if (className.indexOf("com/weaver/agent/asm/") == -1) {
                //System.out.println("监听到类：" + className);
                ExecutorService executor = Executors.newFixedThreadPool(10);
                try {
                    InputStream sbs = new ByteArrayInputStream(classfileBuffer);
                    ClassReader reader = new ClassReader(sbs);
                    // 构建一个ClassWriter对象，并设置让系统自动计算栈和本地变量大小
                    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    //加入自己的适配器
                    WeaverClassAdapter fileClassAdapter = new WeaverClassAdapter(Opcodes.ASM5, classWriter);
                    reader.accept(fileClassAdapter, ClassReader.EXPAND_FRAMES);
                    byte[] code = classWriter.toByteArray();
                    /*try {
                        FileOutputStream fos = new FileOutputStream("/usr/WEAVER/1/" + className.replace("/", "_") + ".class");    // 将二进制流写到本地磁盘上
                        fos.write(code);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    return code;
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
        return null;
    }

}
