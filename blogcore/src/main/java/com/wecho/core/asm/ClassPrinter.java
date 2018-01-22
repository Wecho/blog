package com.wecho.core.asm;

import org.objectweb.asm.*;

import java.io.IOException;

public class ClassPrinter  extends ClassVisitor {

    public ClassPrinter() {
        super(262144);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        System.out.println(name+"extnd"+superName+"{");
    }

    @Override
    public void visitSource(String source, String debug) {
        super.visitSource(source, debug);
    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {
        super.visitOuterClass(owner, name, desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    @Override
    public void visitAttribute(Attribute attr) {
        super.visitAttribute(attr);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println("  "+desc+" "+name);
        return super.visitField(access, name, desc, signature, value);

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("  "+name+desc);
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println("}");
    }

    public static void main(String[] args) {

        try {
            ClassPrinter classPrinter = new ClassPrinter();
            ClassReader ClassReader = new ClassReader("java.lang.Runnable");
            ClassReader.accept(classPrinter,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
