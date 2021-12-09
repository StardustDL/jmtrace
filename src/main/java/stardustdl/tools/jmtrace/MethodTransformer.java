package stardustdl.tools.jmtrace;

import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class MethodTransformer extends MethodVisitor {
    public MethodTransformer(final MethodVisitor methodVisitor) {
        super(ASM9, methodVisitor);
    }

    @Override
    public void visitInsn(final int opcode) {
        if (opcode >= IALOAD && opcode <= SALOAD) {
            // ... arrref index
            mv.visitInsn(DUP2);
            // ... arrref index arrref index
            mv.visitLdcInsn(true);
            // ... arrref index arrref index true
            mv.visitMethodInsn(INVOKESTATIC, Tracer.getInternalClassName(), "traceAccessArray",
                    "(Ljava/lang/Object;IZ)V", false);
            // ... arrref index
        } else if (opcode >= IASTORE && opcode <= SASTORE) {
            if (isTwoSlotsValue(opcode)) {
                // ... arrref index v1 v2
                swap112();
                // ... v1 v2 arrref index
            } else {
                // ... arrref index v
                swap21();
                // ... v arrref index
            }

            // ... arrref index
            mv.visitInsn(DUP2);
            // ... arrref index arrref index
            mv.visitLdcInsn(false);
            // ... arrref index arrref index false
            mv.visitMethodInsn(INVOKESTATIC, Tracer.getInternalClassName(), "traceAccessArray",
                    "(Ljava/lang/Object;IZ)V", false);

            // ... arrref index
            if (isTwoSlotsValue(opcode)) {
                // ... v1 v2 arrref index
                swap211();
                // ... arrref index v1 v2
            } else {
                // ... v arrref index
                swap12();
                // ... arrref index v
            }
        }

        super.visitInsn(opcode);
    }

    @Override
    public void visitFieldInsn(final int opcode, final String owner, final String name, final String descriptor) {
        switch (opcode) {
            case GETFIELD: {
                // ... objref
                mv.visitInsn(DUP);
                // ... objref objref
                mv.visitLdcInsn(true);
                // ... objref objref true
                swap11();
                // ... objref true objref
                mv.visitLdcInsn(name);
                // ... objref true objref name
                mv.visitMethodInsn(INVOKESTATIC, Tracer.getInternalClassName(), "traceAccessField",
                        "(ZLjava/lang/Object;Ljava/lang/String;)V", false);
                // ... objref
            }
                break;
            case GETSTATIC: {
                // ...
                mv.visitLdcInsn(true);
                // ... true
                mv.visitLdcInsn(owner);
                // ... true onwer
                mv.visitLdcInsn(name);
                // ... true onwer name
                mv.visitMethodInsn(INVOKESTATIC, Tracer.getInternalClassName(), "traceAccessStatic",
                        "(ZLjava/lang/String;Ljava/lang/String;)V", false);
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
                mv.visitLdcInsn(false);
                // ... objref objref false
                swap11();
                // ... objref false objref
                mv.visitLdcInsn(name);
                // ... objref false objref name
                mv.visitMethodInsn(INVOKESTATIC, Tracer.getInternalClassName(), "traceAccessField",
                        "(ZLjava/lang/Object;Ljava/lang/String;)V", false);

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
                mv.visitLdcInsn(false);
                // ... false
                mv.visitLdcInsn(owner);
                // ... false onwer
                mv.visitLdcInsn(name);
                // ... false onwer name
                mv.visitMethodInsn(INVOKESTATIC, Tracer.getInternalClassName(), "traceAccessStatic",
                        "(ZLjava/lang/String;Ljava/lang/String;)V", false);
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

    private void swap112() {
        // ... i j l1 l2
        mv.visitInsn(DUP2_X2);
        // ... l1 l2 i j l1 l2
        mv.visitInsn(POP2);
        // ... l1 l2 i j
    }

    private void swap211() {
        // ... l1 l2 i j
        mv.visitInsn(DUP2_X2);
        // ... i j l1 l2 i j
        mv.visitInsn(POP2);
        // ... i j l1 l2
    }

    private boolean isTwoSlotsValue(String descriptor) {
        return descriptor.equals("D") || descriptor.equals("J");
    }

    private boolean isTwoSlotsValue(int opcode) {
        return opcode == LASTORE || opcode == DASTORE;
    }
}
