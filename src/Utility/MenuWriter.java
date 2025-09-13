package Utility;

import java.util.ArrayList;

public class MenuWriter
{
    private static final String FORES = "\033[38;2;150;255;150m";
    private static final String BACKS = "\033[48;2;150;255;150m";
    private static final String BLACK = "\033[38;2;0;0;0m";
    private static final String CLEAN = "\033[0m";
    public ArrayList<String> menu = new ArrayList<>();

    public void write(String message){
        System.out.println( BACKS + BLACK + "[ MEN ]" + CLEAN + ": " + message);
        for (int i = 0; i < menu.size(); i++) {
            System.out.println("\t\t" + FORES + "{\t" + (i + 1) + "\t}" + CLEAN + " : " + menu.get(i));
        }
    }
}
