package stardustdl.tools.jmtrace;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.ClassFileTransformer;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain was called.");
    }
}
