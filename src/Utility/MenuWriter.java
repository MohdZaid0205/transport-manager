package Utility;

import java.util.ArrayList;

public class MenuWriter
{
    private static final String FORES = "\033[38;2;255;175;255m";
    private static final String BACKS = "\033[48;2;255;175;255m";
    private static final String CLEAN = "\033[0m";
    public static ArrayList<String> menu = new ArrayList<>();

    public static void write(String message){
        System.out.println( BACKS + "[ MEN ]" + CLEAN + ": " + message);
        for (int i = 0; i < menu.size(); i++) {
            System.out.println("\t" + FORES + "{" + (i + 1) + "}" + CLEAN + ":" + menu.get(i));
        }
    }
}
