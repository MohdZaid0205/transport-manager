package Utility;

public class ExceptionWriter
{
    private static final String COLOR = "\033[38;2;255;175;175m";
    private static final String CLEAN = "\033[0m";

    public static void write(String message){
        System.out.println( "[ " + COLOR + "ERR" + CLEAN + " ] : " + message);
    }
}
