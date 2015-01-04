package to.us.harha.jpath.util;

public class TimeUtils
{

    private static final double NANOS_PER_SEC = 1_000_000_000.0;

    public static double getTime()
    {
        return (double) System.nanoTime() / NANOS_PER_SEC;
    }

}
