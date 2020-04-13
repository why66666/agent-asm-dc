package com.weaver.agent.asm;


import weaver.dscanAgent.org.objectweb.asm.ClassVisitor;
import weaver.dscanAgent.org.objectweb.asm.Label;
import weaver.dscanAgent.org.objectweb.asm.MethodVisitor;
import weaver.dscanAgent.org.objectweb.asm.Opcodes;

public class WeaverClassAdapter extends ClassVisitor {


    private String className;
    private String methodName;

    public WeaverClassAdapter(int api, ClassVisitor cv) {
        super(api, cv);
    }


    public void visit(int version, int access, String name, String signature,
                      String superName, String[] interfaces) {
        className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        methodName = name;
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (!name.equals("<init>")) {
            return new ThreadAdapter(api, mv);
        }
        return mv;
    }


    class ThreadAdapter extends MethodVisitor implements Opcodes {


        public ThreadAdapter(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitCode() {
//			mv.visitMethodInsn(INVOKESTATIC, "weaver/dscanAgent/rule/MethodUtil", "methodStart", "()V", false);
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(9, l0);
            mv.visitLdcInsn(className);
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(INVOKESTATIC, "com/weaver/agent/asm/depend/MethodUtil", "methodStart", "(Ljava/lang/String;Ljava/lang/String;)V", false);
        }


        @Override
        public void visitInsn(int opcode) {
            if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
                mv.visitMethodInsn(INVOKESTATIC, "com/weaver/agent/asm/depend/MethodUtil", "methodEnd", "()V", false);
            }
            mv.visitInsn(opcode);
        }

    }


}