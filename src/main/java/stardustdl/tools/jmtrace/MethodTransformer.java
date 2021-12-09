package stardustdl.tools.jmtrace;

import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class MethodTransformer extends MethodVisitor {
    public MethodTransformer(final MethodVisitor methodVisitor) {
        super(ASM9, methodVisitor);
    }

    @Override
    public void visitInsn(final int opcode) {
        if (mv != null) {
            mv.visitInsn(opcode);
        }
    }

    @Override
    public void visitFieldInsn(final int opcode, final String owner, final String name, final String descriptor) {
        switch (opcode) {
            case GETFIELD: {
                // ... objref
                mv.visitInsn(DUP);
                // ... objref objref
                mv.visitLdcInsn(name);
                // ... objref objref name
                mv.visitMethodInsn(INVOKESTATIC, Tracer.getInternalClassName(), "traceGetField",
                        "(Ljava/lang/Object;Ljava/lang/String;)V", false);
                // ... objref
            }
                break;
            case GETSTATIC: {
                // ...
                mv.visitLdcInsn(owner);
                // ... onwer
                mv.visitLdcInsn(name);
                // ... onwer name
                mv.visitMethodInsn(INVOKESTATIC, Tracer.getInternalClassName(), "traceGetStatic",
                        "(Ljava/lang/String;Ljava/lang/String;)V", false);
                // ...
            }
                break;
            case PUTFIELD: {
                System.out.print("putfield\n");
            }
                break;
            case PUTSTATIC: {
                // ...
                mv.visitLdcInsn(owner);
                // ... onwer
                mv.visitLdcInsn(name);
                // ... onwer name
                mv.visitMethodInsn(INVOKESTATIC, Tracer.getInternalClassName(), "tracePutStatic",
                        "(Ljava/lang/String;Ljava/lang/String;)V", false);
                // ...
            }
                break;
        }
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }
}
