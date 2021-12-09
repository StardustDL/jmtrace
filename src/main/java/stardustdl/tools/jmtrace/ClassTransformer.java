package stardustdl.tools.jmtrace;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class ClassTransformer extends ClassVisitor {
    public ClassTransformer(final ClassVisitor classVisitor) {
        super(ASM9, classVisitor);
    }

    @Override
    public void visit(
            final int version,
            final int access,
            final String name,
            final String signature,
            final String superName,
            final String[] interfaces) {
        // System.out.printf("!Class: %s\n", name);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(
            final int access,
            final String name,
            final String descriptor,
            final String signature,
            final String[] exceptions) {
        // System.out.printf("!Method: %s\n", name);
        return new MethodTransformer(super.visitMethod(access, name, descriptor, signature, exceptions));
    }
}
