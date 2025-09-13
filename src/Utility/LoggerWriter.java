package Utility;

public class LoggerWriter
{
    private static final String COLOR = "\033[38;2;175;175;175m";
    private static final String CLEAN = "\033[0m";

    public static void write(String message){
        System.out.println( "[ " + COLOR + "LOG" + CLEAN + " ] : " + message);
    }
}
