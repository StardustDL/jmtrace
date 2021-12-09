package demo;

public class Main {

    public boolean[] az = new boolean[1];
    public char[] ac = new char[1];
    public byte[] ab = new byte[1];
    public short[] as = new short[1];
    public int[] ai = new int[1];
    public long[] al = new long[1];
    public float[] af = new float[1];
    public double[] ad = new double[1];
    public String[] astr = new String[1];

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

        System.out.println("Hello, world!");
    }

}