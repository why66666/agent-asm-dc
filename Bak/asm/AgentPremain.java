package com.weaver.agent.asm;


import com.weaver.agent.asm.task.InstTask;
import com.weaver.agent.asm.util.ClientInit;
import com.weaver.agent.asm.adapter.FileClassAdapter;
import com.weaver.agent.asm.task.ClientStatusTask;
import com.weaver.agent.asm.util.PropUtil;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AgentPremain {
    private static ExecutorService executor = Executors.newFixedThreadPool(5);
    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) throws IOException, ClassNotFoundException {
        //初始化服务端连接
        PropUtil.initProp();
        ClientInit.initConnect();
        //定时任务
        initTask();
        //重写File.class
        /*try {
            inst.redefineClasses(redefineIoFile());
        } catch (ClassNotFoundException | UnmodifiableClassException e) {
            e.printStackTrace();
        }*/
        //
        /*executor.submit(new Thread(){
            @Override
            public void run() {
                inst.addTransformer(new ScanSqlTrans());
            }
        });*/
        executor.submit(new InstTask(inst));
    }

    private static void initTask() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new ClientStatusTask(), 5000, 30000, TimeUnit.MILLISECONDS);
    }

    private static ClassDefinition redefineIoFile() {
        System.out.println(System.getenv());
        try {
            JarFile jarFile = new JarFile("D:\\Work\\rt.jar");
            JarEntry jentry = jarFile.getJarEntry("java/io/File.class");
            InputStream inputStream = jarFile.getInputStream(jentry);
            ClassReader reader = new ClassReader(inputStream);
            // 构建一个ClassWriter对象，并设置让系统自动计算栈和本地变量大小
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            //加入自己的适配器
            FileClassAdapter fileClassAdapter = new FileClassAdapter(Opcodes.ASM5, classWriter);
            reader.accept(fileClassAdapter, ClassReader.EXPAND_FRAMES);
            byte[] code = classWriter.toByteArray();

            Class clazz = Thread.currentThread().getContextClassLoader().loadClass("java.io.File");
            ClassDefinition c = new ClassDefinition(clazz, code);
            return c;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
