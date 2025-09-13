import Utility.*;

public class Main {

    public static void main(String[] args) {
        FleetManager manager = new FleetManager();

        MenuWriter.menu.add("ADD Vehicle"       );
        MenuWriter.menu.add("REMOVE Vehicle"    );
        MenuWriter.menu.add("START Journey"     );
        MenuWriter.menu.add("REFUEL Vehicles"   );
        MenuWriter.menu.add("MAINTAIN Vehicles" );
        MenuWriter.menu.add("GENERATE Report"   );
        MenuWriter.menu.add("SAVE Fleet"        );
        MenuWriter.menu.add("LOAD Fleet"        );
        MenuWriter.menu.add("SEARCH by Type"    );
        MenuWriter.menu.add("MAINTENANCE List"  );
        MenuWriter.menu.add("EXIT"              );

        while (true) {
            MenuWriter.write("Menu for CLI is as follows:");
            // TODO: Handle Input and process further with it.
            // TODO: Make CSV write method and load method.
            break;
        }

    }
}