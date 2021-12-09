package stardustdl.tools.jmtrace;

import java.io.PrintStream;

public class Tracer {
    public enum AccessMode {
        Read,
        Write
    }

    private static String internalClassName = Tracer.class.getCanonicalName().replace('.', '/');

    public static String getInternalClassName() {
        return internalClassName;
    }

    private static PrintStream Output = System.err;

    public static void trace(AccessMode mode, Object obj, String descriptor) {
        Output.printf("%c %d %x %s\n", mode.name().charAt(0), Thread.currentThread().getId(),
                System.identityHashCode(obj), descriptor);
    }

    public static void traceGetField(Object obj, String fieldName) {
        trace(AccessMode.Read, obj, String.format("%s.%s", obj.getClass().getCanonicalName(), fieldName));
    }

    public static void tracePutField(Object obj, String fieldName) {
        trace(AccessMode.Write, obj, String.format("%s.%s", obj.getClass().getCanonicalName(), fieldName));
    }

    public static void traceGetStatic(String owner, String fieldName) {
        owner = owner.replace('/', '.');
        Class<?> cls = null;
        try {
            cls = Class.forName(owner);
        } catch (ClassNotFoundException ex) {
            System.err.printf("Failed to load class %s", owner);
            return;
        }

        trace(AccessMode.Read, cls, String.format("%s.%s", cls.getCanonicalName(), fieldName));
    }

    public static void tracePutStatic(String owner, String fieldName) {
        owner = owner.replace('/', '.');
        Class<?> cls = null;
        try {
            cls = Class.forName(owner);
        } catch (ClassNotFoundException ex) {
            System.err.printf("Failed to load class %s", owner);
            return;
        }

        trace(AccessMode.Write, cls, String.format("%s.%s", cls.getCanonicalName(), fieldName));
    }
}
