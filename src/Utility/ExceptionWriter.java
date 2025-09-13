package Utility;

public class ExceptionWriter
{
    private static final String COLOR = "\033[48;2;255;150;150m";
    private static final String BLACK = "\033[38;2;0;0;0m";
    private static final String CLEAN = "\033[0m";

    public static void write(String message){
        System.out.println(COLOR + BLACK + "[ ERR ]" + CLEAN + ": " + message);
    }

    public static void draws(String message){
        System.out.print(COLOR + BLACK + "[ ERR ]" + CLEAN + ": " + message);
    }
}
