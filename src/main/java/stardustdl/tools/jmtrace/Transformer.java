package stardustdl.tools.jmtrace;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class Transformer extends ClassVisitor {
    public Transformer() {
        super(ASM4);
    }

    public MethodVisitor visitMethod(
            final int access,
            final String name,
            final String descriptor,
            final String signature,
            final String[] exceptions) {
        System.out.println(name);
        if (cv != null) {
            return cv.visitMethod(access, name, descriptor, signature, exceptions);
        }
        return null;
    }
}
