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
                if (isTwoSlotsValue(descriptor)) {
                    // ... objref v1 v2
                    swap12();
                    // ... v1 v2 objref
                } else {
                    // ... objref v
                    swap11();
                    // ... v objref
                }
                
                // ... objref
                mv.visitInsn(DUP);
                
                // ... objref objref
                mv.visitLdcInsn(name);
                // ... objref objref name
                mv.visitMethodInsn(INVOKESTATIC, Tracer.getInternalClassName(), "tracePutField",
                        "(Ljava/lang/Object;Ljava/lang/String;)V", false);
                
                // ... objref
                if (isTwoSlotsValue(descriptor)) {
                    // ... v1 v2 objref
                    swap21();
                    // ... objref v1 v2
                } else {
                    // v objref
                    swap11();
                    // objref v
                }
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

    private void swap11() {
        // ... i j
        mv.visitInsn(SWAP);
        // ... j i
    }

    private void swap12() {
        // ... i d1 d2
        mv.visitInsn(DUP2_X1);
        // ... d1 d2 i d1 d2
        mv.visitInsn(POP2);
        // ... d1 d2 i
    }

    private void swap21() {
        // ... d1 d2 i
        mv.visitInsn(DUP_X2);
        // ... i d1 d2 i
        mv.visitInsn(POP);
        // ... i d1 d2
    }

    private boolean isTwoSlotsValue(String descriptor) {
        return descriptor.equals("D") || descriptor.equals("J");
    }
}
