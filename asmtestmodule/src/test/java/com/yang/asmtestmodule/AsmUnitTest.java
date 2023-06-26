package com.yang.asmtestmodule;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;

public class AsmUnitTest {

    @Test
    public void testAsm() throws Exception {
        InjectTest.main(null);
        Collections.synchronizedMap(new HashMap<String, String>());
//        new Hashtable<String, String>();
        System.out.println("======> " + Integer.toBinaryString(0x7FFFFFFF).length());
    }

    @Test
    public void testAsmInJava() throws Exception {
        String classPath = "build/intermediates/javac/debugUnitTest/classes/com/yang/asmtestmodule/InjectTest.class";
        FileInputStream fis = new FileInputStream(new File(classPath));
        ClassReader classReader = new ClassReader(fis);

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        classReader.accept(new MyClassVisitor(Opcodes.ASM9, classWriter), ClassReader.EXPAND_FRAMES);
        byte[] bytes = classWriter.toByteArray();

//        String outPath = "src/test/java/com/yang/asmtestmodule/InjectTest.class";
        String outPath = "build/intermediates/javac/debugUnitTest/classes/com/yang/asmtestmodule/InjectTest.class";
        isExitsPath(outPath);
        File outFile = new File(outPath);
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(bytes);
        fos.close();
    }

    static class MyClassVisitor extends ClassVisitor {

        public MyClassVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            System.out.println("----- visitMethod: " + name);
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
            return new MyMethodVisitor(api, methodVisitor, access, name, descriptor);
        }
    }

    static class MyMethodVisitor extends AdviceAdapter {

        /**
         * Constructs a new {@link AdviceAdapter}.
         *
         * @param api           the ASM API version implemented by this visitor. Must be one of {@link
         *                      Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
         * @param methodVisitor the method visitor to which this adapter delegates calls.
         * @param access        the method's access flags (see {@link Opcodes}).
         * @param name          the method's name.
         * @param descriptor    the method's descriptor (see {@link Type Type}).
         */
        protected MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }
        int s;
        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            //long s =  System.currentTimeMillis
            invokeStatic(Type.getType("L/java/lang/System;"), new Method("currentTimeMillis", "()J"));
            s = newLocal(Type.LONG_TYPE);
            storeLocal(s);
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);

            // long e =  System.currentTimeMillis
            invokeStatic(Type.getType("L/java/lang/System;"), new Method("currentTimeMillis", "()J"));
            int e = newLocal(Type.LONG_TYPE);
            storeLocal(e);

            // 调用static方法java/lang/System.PrintStream out
            getStatic(Type.getType("L/java/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"));

            // 创建实例 new StringBuilder()
            newInstance(Type.getObjectType("Ljava/lang/StringBuilder;"));
            dup();

            // 调用StringBuilder的无参构造方法
            invokeConstructor(Type.getType("Ljava/lang/StringBuilder"), new Method("<init>", "()V"));

            // 将“cost”从常量池推送至栈顶
            visitLdcInsn("cost:");

            // 调用方法StringBuilder.append（StringBuilder）
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder"),
                    new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));

            // e - s
            loadLocal(e);
            loadLocal(s);
            math(SUB, Type.LONG_TYPE);

            invokeVirtual(Type.getType("Ljava/lang/StringBuilder"),
                    new Method("append", "(J)Ljava/lang/StringBuilder;"));

            visitLdcInsn("ms.");
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder"),
                    new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder"),
                    new Method("toString", "()Ljava/lang/String;"));
            invokeVirtual(Type.getType("Ljava/io/PrintStream"),
                    new Method("println", "(Ljava/lang/String;)V"));
        }

    }

    public boolean isExitsPath(String path) throws InterruptedException {
        String[] paths = path.split("\\/");
        StringBuffer fullPath = new StringBuffer();
        for (int i = 0; i < paths.length; i++) {
            fullPath.append(paths[i]).append("\\\\");
            File file = new File(fullPath.toString());
            if (paths.length - 1 != i) {
                if (!file.exists()) {
                    boolean result = file.mkdir();
                    System.out.println("创建目录为：" + fullPath.toString() + ", success:" + result);
                }
            }
        }

        File file = new File(fullPath.toString());
        if (!file.exists()) {
            return true;
        } else {
            return false;
        }

    }
}
