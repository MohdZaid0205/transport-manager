package Utility;

public class MessageWriter
{
    private static final String COLOR = "\033[38;2;175;175;255m";
    private static final String CLEAN = "\033[0m";

    public static void write(String message){
        System.out.println( "[ " + COLOR + "MSG" + CLEAN + " ] : " + message);
    }
}
