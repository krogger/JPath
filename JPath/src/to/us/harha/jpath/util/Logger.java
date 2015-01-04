package to.us.harha.jpath.util;

public class Logger
{

    private String prefix;

    public Logger(String prefix)
    {
        this.prefix = prefix;
        printMsg("Logger has started!");
    }

    public void printMsg(String msg)
    {
        System.out.println("[" + prefix + "]: " + msg);
    }

    public void printErr(String msg)
    {
        System.err.println("[" + prefix + "]: ERROR: " + msg);
    }

}
