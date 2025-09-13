package Utility;

public class WarningWriter
{
    private static final String COLOR = "\033[48;2;255;255;150m";
    private static final String BLACK = "\033[38;2;0;0;0m";
    private static final String CLEAN = "\033[0m";

    public static void write(String message){
        System.out.println(COLOR + BLACK + "[ WRN ]" + CLEAN + ": " + message);
    }
}
