package Utility;

public class WarningWriter
{
    private static final String COLOR = "\033[48;2;255;255;175m";
    private static final String CLEAN = "\033[0m";

    public static void write(String message){
        System.out.println(COLOR + "[ WRN ]" + CLEAN + ": " + message);
    }
}
