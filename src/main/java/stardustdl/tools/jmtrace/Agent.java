package stardustdl.tools.jmtrace;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain was called.");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                    Class<?> clazz,
                    ProtectionDomain protectionDomain,
                    byte[] byteCode) throws IllegalClassFormatException {
                if (loader == null)
                    return byteCode;
                ClassReader reader = new ClassReader(byteCode);
                Transformer transformer = new Transformer();
                reader.accept(transformer, 0);
                System.out.println(String.format("Process by ClassFileTransformer,target class = %s", className));
                return byteCode;
            }
        });
    }
}
