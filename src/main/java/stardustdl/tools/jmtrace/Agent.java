package stardustdl.tools.jmtrace;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                    Class<?> clazz,
                    ProtectionDomain protectionDomain,
                    byte[] byteCode) throws IllegalClassFormatException {
                if (loader == null || className.startsWith("stardustdl/tools/jmtrace/") || className.startsWith("java") || className.startsWith("sun") || className.startsWith("javax") || className.startsWith("com/sun"))
                    return byteCode;
                ClassReader reader = new ClassReader(byteCode);
                ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);
                reader.accept(new ClassTransformer(writer), 0);
                return writer.toByteArray();
            }
        });
    }
}
