package demo;

public class Main {

    public boolean[] az = new boolean[2];
    public char[] ac = new char[2];
    public byte[] ab = new byte[2];
    public short[] as = new short[2];
    public int[] ai = new int[2];
    public long[] al = new long[2];
    public float[] af = new float[2];
    public double[] ad = new double[2];
    public String[] astr = new String[2];

    public static boolean sz;
    public static char sc;
    public static byte sb;
    public static short ss;
    public static int si;
    public static long sl;
    public static float sf;
    public static double sd;
    public static String sstr;

    public static void main(String[] args) {
        Main.sz = true;
        Main.sc = 'a';
        Main.sb = 1;
        Main.ss = 2;
        Main.si = 3;
        Main.sl = 4;
        Main.sf = 5.0f;
        Main.sd = 6.0;
        Main.sstr = "Hello";

        Main obj = new Main();
        obj.az[0] = Main.sz;
        obj.ab[0] = Main.sb;
        obj.ac[0] = Main.sc;
        obj.as[0] = Main.ss;
        obj.ai[0] = Main.si;
        obj.al[0] = Main.sl;
        obj.af[0] = Main.sf;
        obj.ad[0] = Main.sd;
        obj.astr[0] = Main.sstr;

        Main obj2 = new Main();

        obj2.az[1] = obj.az[0];
        obj2.ab[1] = obj.ab[0];
        obj2.ac[1] = obj.ac[0];
        obj2.as[1] = obj.as[0];
        obj2.ai[1] = obj.ai[0];
        obj2.al[1] = obj.al[0];
        obj2.af[1] = obj.af[0];
        obj2.ad[1] = obj.ad[0];
        obj2.astr[1] = obj.astr[0];

        System.out.println("Hello, world!");
    }

}