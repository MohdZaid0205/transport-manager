package Utility;

import java.util.Scanner;

public class InputWriter {
    private static final String COLOR = "\t\t\033[48;2;150;255;255m";
    private static final String BLACK = "\033[38;2;0;0;0m";
    private static final String CLEAN = "\033[0m";

    public static void write(String message){
        System.out.println(COLOR + BLACK + "[ INPUT ]" + CLEAN + " : " + message);
    }

    public static void draws(String message){
        System.out.print(COLOR + BLACK + "[ INPUT ]" + CLEAN + " : " + message);
    }

    public static Object safeIn(Scanner scanner, Class<?> type, String message) {
        while (true) {
            draws(message);
            try {
                if (type == Integer.class) {
                    return scanner.nextInt();
                } else if (type == Double.class) {
                    return scanner.nextDouble();
                } else if (type == Boolean.class) {
                    return scanner.nextBoolean();
                } else if (type == String.class) {
                    String pre = scanner.nextLine();
                    if (pre.isBlank())
                        return scanner.nextLine();
                    return pre;
                } else {
                    throw new IllegalArgumentException("Unsupported type: " + type.getSimpleName());
                }
            } catch (Exception e) {
                ExceptionWriter.write("Invalid " + type.getSimpleName() + " input. Try again.");
                scanner.nextLine(); // clear bad input
            }
        }
    }
}
