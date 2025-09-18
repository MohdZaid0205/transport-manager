import Abstracts.*;
import Core.*;
import Exceptions.*;
import Interfaces.CargoCarrier;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Utility.*;
import Validators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static FleetManager manager = new FleetManager();
    private static MenuWriter menuWriter = new MenuWriter();
    private static Scanner scanner = new Scanner(System.in);

    public static void vehicleMenu() {
        MenuWriter vehicleMenu = new MenuWriter();
        vehicleMenu.menu.add("ADD Vehicle");
        vehicleMenu.menu.add("REMOVE Vehicle");
        vehicleMenu.menu.add("EDIT Vehicle Params");
        vehicleMenu.menu.add("REFUEL Vehicle");
        vehicleMenu.menu.add("MAINTAIN Vehicle");
        vehicleMenu.menu.add("BACK to Main Menu");

        boolean loop = true;
        while (loop) {
            vehicleMenu.write("Vehicle Menu:");
            int option = (int) InputWriter.safeIn(
                    scanner, Integer.class, "OPTION_: ");
            switch (option) {
                case 1:
                    // This now calls the refactored method below
                    Vehicle v = addVehicleMenu();
                    if (v != null) {
                        try {
                            manager.addVehicle(v);
                            LoggerWriter.write("Vehicle ADD Successful");
                        } catch (Exception e) {
                            WarningWriter.write("Vehicle ADD Failed: " + e.getMessage());
                        }
                    } else {
                        WarningWriter.write("Vehicle ADD Aborted");
                    }
                    break;
                case 2:
                    String id = removeVehicleMenu();
                    if (id != null) {
                        try {
                            manager.removeVehicle(id);
                            LoggerWriter.write("Vehicle REMOVE Successful");
                        } catch (Exception e) {
                            WarningWriter.write("Vehicle REMOVE Failed: " + e.getMessage());
                        }
                    }
                    break;
                case 3:
                    editVehicleParams();
                    break;
                case 4:
                    refuelVehicles();
                    LoggerWriter.write("REFUELING Vehicles Complete");
                    break;
                case 5:
                    maintainVehicles();
                    LoggerWriter.write("MAINTENANCE Complete for Fleet");
                    break;
                case 6:
                    LoggerWriter.write("Returning to MAIN Menu");
                    loop = false;
                    break;
                default:
                    WarningWriter.write("Invalid Vehicle Menu Option");
            }
        }
    }

    public static Vehicle addVehicleMenu() {
        return VehicleFactory.createFromConsole(scanner);
    }


    public static String removeVehicleMenu() {
        String id = (String) InputWriter.safeIn(
                scanner, String.class, "Vehicle ID\t\t\t:");
        if (!IdentityValidator.find(id)) {
            WarningWriter.write("Invalid Vehicle ID, aborting [Remove Vehicle]");
            return null;
        }
        return id;
    }

    public static void beginJourney() {
        double distance = (double) InputWriter.safeIn(
                scanner, Double.class, "Distance\t\t\t:");
        manager.startAllJourneys(distance);
    }

    public static void refuelVehicles() {
        double amount = (double) InputWriter.safeIn(
                scanner, Double.class, "Amount\t\t\t:");
        for (Vehicle v : manager.fleet) {
            try {
                if (v instanceof FuelConsumable)
                    ((FuelConsumable) v).refuel(amount);
            } catch (Exception e) {
                WarningWriter.write(e.getMessage());
            }
        }
    }

    public static void editVehicleParams() {
        String id = (String) InputWriter.safeIn(scanner, String.class, "Vehicle ID\t\t\t:");
        Vehicle v = manager.findVehicle(id);
        if (v == null) {
            WarningWriter.write("Vehicle not found, aborting [EDIT]");
            return;
        }

        LoggerWriter.write("Editing Vehicle ID: " + id);

        if (v instanceof CargoCarrier) {
            double cargo = (double) InputWriter.safeIn(scanner, Double.class, "Cargo to LOAD\t:");
            try {
                ((CargoCarrier) v).loadCargo(cargo);
                LoggerWriter.write("Cargo LOAD Successful for Vehicle ID: " + id);
            } catch (Exception e) {
                WarningWriter.write(e.getMessage());
            }
        }

        if (v instanceof Maintainable) {
            boolean doMaint = (boolean) InputWriter.safeIn(scanner, Boolean.class, "Perform maintenance (true/false):");
            if (doMaint) {
                ((Maintainable) v).performMaintenance();
                LoggerWriter.write("Maintenance PERFORMED for Vehicle ID: " + id);
            }
        }
    }

    // This method is kept exactly as you provided it.
    public static void maintainVehicles() {
        for (Vehicle v : manager.fleet) {
            if (v instanceof Maintainable) {
                ((Maintainable) v).performMaintenance();
            }
        }
    }


    public static void journeyMenu() {
        MenuWriter jMenu = new MenuWriter();
        jMenu.menu.add("START Journey for Fleet");
        jMenu.menu.add("BACK to Main Menu");

        boolean loop = true;
        while (loop) {
            jMenu.write("Journey Menu:");
            int option = (int) InputWriter.safeIn(
                    scanner, Integer.class, "OPTION_: ");
            switch (option) {
                case 1:
                    beginJourney();
                    LoggerWriter.write("Journey BEGIN for Fleet");
                    break;
                case 2:
                    LoggerWriter.write("Returning to MAIN Menu");
                    loop = false;
                    break;
                default:
                    WarningWriter.write("Invalid Journey Menu Option");
            }
        }
    }

    public static void fleetMenu() {
        MenuWriter fMenu = new MenuWriter();
        fMenu.menu.add("SAVE Fleet");
        fMenu.menu.add("LOAD Fleet");
        fMenu.menu.add("MAINTENANCE List");
        fMenu.menu.add("DISPLAY Info/Report");
        fMenu.menu.add("BACK to Main Menu");

        boolean loop = true;
        while (loop) {
            fMenu.write("Fleet Menu:");
            int option = (int) InputWriter.safeIn(
                    scanner, Integer.class, "OPTION_: ");

            switch (option) {
                case 1:
                    saveFleet();
                    break;
                case 2:
                    loadFleet();
                    break;
                case 3:
                    showMaintenanceList();
                    break;
                case 4:
                    displayFleetReport();
                    break;
                case 5:
                    LoggerWriter.write("Returning to MAIN Menu");
                    loop = false;
                    break;
                default:
                    WarningWriter.write("Invalid Fleet Menu Option");
            }
        }
    }

    public static void saveFleet() {
        String path = (String) InputWriter.safeIn(
                scanner, String.class, "Enter path to SAVE fleet\t:");

        if (manager.fleet == null || manager.fleet.isEmpty()) {
            WarningWriter.write("Fleet is empty. Nothing to save.");
            return; // Stops the method here if there's nothing to do
        }

        try {
            LoggerWriter.write("Saving " + manager.fleet.size() + " vehicles to " + path + "...");
            FleetSerializer.save(manager.fleet, path);
            LoggerWriter.write("Fleet SAVE Successful to " + path);
        } catch (Exception e) {
            WarningWriter.write("Fleet SAVE Failed: " + e.getMessage());
        }
    }

    public static void loadFleet() {
        String path = (String) InputWriter.safeIn(
                scanner, String.class, "Enter path to LOAD fleet\t:");

        try {
            manager.fleet = (ArrayList<Vehicle>) FleetSerializer.load(path);
            LoggerWriter.write("Fleet LOAD Successful from " + path);
        } catch (Exception e) {
            WarningWriter.write("Fleet LOAD Failed: " + e.getMessage());
        }
    }

    // This method is kept exactly as you provided it.
    public static void showMaintenanceList() {
        LoggerWriter.write("Maintenance List:");
        for (Vehicle v : manager.fleet) {
            if (v instanceof Maintainable) {
                Maintainable m = (Maintainable) v;
                if (m.needsMaintenance()) {
                    MessageWriter.write("Vehicle ID: " + v.getId() + " requires maintenance.");
                }
            }
        }
    }

    public static void displayFleetReport() {
        LoggerWriter.write("Fleet Report:");
        for (Vehicle v : manager.fleet) {
            v.printInfo();
        }
        MessageWriter.write(manager.generateReport());
    }

    public static void queryMenu() {
        MenuWriter qMenu = new MenuWriter();
        qMenu.menu.add("SEARCH by ID");
        qMenu.menu.add("SEARCH by Type");
        qMenu.menu.add("SEARCH by Model");
        qMenu.menu.add("BACK to Main Menu");

        boolean loop = true;
        while (loop) {
            qMenu.write("Query Menu:");
            int option = (int) InputWriter.safeIn(
                    scanner, Integer.class, "OPTION_: ");
            switch (option) {
                case 1:
                    String id = (String) InputWriter.safeIn(
                            scanner, String.class, "Enter ID:");
                    Vehicle v = manager.findVehicle(id);

                    if (v != null) {
                        LoggerWriter.write("Search SUCCESS: Found Vehicle");
                        v.printInfo(); // Using printInfo to match your displayFleetReport method
                    } else
                        WarningWriter.write("Search FAIL: No Vehicle with ID " + id);

                    break;
                case 2:
                    String typeName = (String) InputWriter.safeIn(scanner, String.class, "Enter vehicle type (Car, Bus, Truck, Airplane, CargoShip):");

                    try {
                        Class<?> typeClass = switch (typeName.toLowerCase()) {
                            case "car" -> Car.class;
                            case "bus" -> Bus.class;
                            case "truck" -> Truck.class;
                            case "airplane" -> Airplane.class;
                            case "cargoship" -> CargoShip.class;
                            default -> throw new ClassNotFoundException("Unknown vehicle type: " + typeName);
                        };

                        List<Vehicle> results = manager.searchByType(typeClass);
                        if (results.isEmpty()) {
                            MessageWriter.write("No vehicles found of type: " + typeName);
                        } else {
                            LoggerWriter.write("Vehicles of type " + typeName + ":");
                            for (Vehicle u : results) {
                                MessageWriter.write(
                                        "ID: " + u.getId() + ", Model: " + u.getModel() +
                                                ", Mileage: " + u.getCurrentMileage());
                            }
                        }
                    } catch (Exception e) {
                        WarningWriter.write(e.getMessage());
                    }
                    break;
                case 3:
                    LoggerWriter.write("Searching by MODEL (not yet implemented)");
                    break;
                case 4:
                    LoggerWriter.write("Returning to MAIN Menu");
                    loop = false;
                    break;
                default:
                    WarningWriter.write("Invalid Query Menu Option");
            }
        }
    }

    public static void main(String[] args) {
        menuWriter.menu.add("VEHICLE Menu");
        menuWriter.menu.add("JOURNEY Menu");
        menuWriter.menu.add("FLEET Menu");
        menuWriter.menu.add("QUERY Menu");
        menuWriter.menu.add("EXIT Program");

        boolean running = true;
        LoggerWriter.write("Fleet Management System STARTED");
        while (running) {
            menuWriter.write("Main Menu:");
            // Using safeIn here for consistency and to prevent scanner issues.
            int option = (int) InputWriter.safeIn(scanner, Integer.class, "OPTION_: ");
            switch (option) {
                case 1:
                    vehicleMenu();
                    break;
                case 2:
                    journeyMenu();
                    break;
                case 3:
                    fleetMenu();
                    break;
                case 4:
                    queryMenu();
                    break;
                case 5:
                    running = false;
                    LoggerWriter.write("Fleet Management System EXITED");
                    break;
                default:
                    WarningWriter.write("Invalid Main Menu Option");
            }
        }
        scanner.close();
    }
}