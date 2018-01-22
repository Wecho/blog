package com.wecho.core.asm;

        import org.objectweb.asm.ClassWriter;
        import org.objectweb.asm.Opcodes;

public class MyClassWriter {
    public byte[] generateClassByteArray() {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE, "com/wecho/core/asm/testInterface",
                null, "java/lang/Object", new String[]{"com/wecho/core/asm/TestInterface"});
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE,"LESS","I",null,
                new Integer(-1)).visitEnd();
        cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE,"compareTo",
                "(Ljava/lang/Object;)I",null,null).visitEnd();
        cw.visitEnd();
        return cw.toByteArray();
    }

    public static void main(String[] args) {
        MyClassWriter myClassWriter = new MyClassWriter();
        System.out.println(myClassWriter.generateClassByteArray().getClass());
    }
}
