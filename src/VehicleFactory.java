import Abstracts.Vehicle;
import Core.*;
import Exceptions.InvalidIdentificationException;
import Interfaces.*;
import Validators.IdentityValidator;
import  Utility.*;

import java.util.Scanner;

public class VehicleFactory {

    public static Vehicle createFromConsole(Scanner scanner) {
        // ... (no changes needed here)
        MenuWriter vehicleMenu = new MenuWriter();
        vehicleMenu.menu.add("CAR");
        vehicleMenu.menu.add("BUS");
        vehicleMenu.menu.add("TRUCK");
        vehicleMenu.menu.add("AIRPLANE");
        vehicleMenu.menu.add("CARGO SHIP");
        vehicleMenu.write("Select vehicle type to ADD");

        int vType = (int) InputWriter.safeIn(scanner, Integer.class, "Vehicle TYPE\t\t:");
        if (vType < 1 || vType > 5) {
            ExceptionWriter.write("Invalid Vehicle Type, aborting [Add Vehicle]");
            return null;
        }

        String id = (String) InputWriter.safeIn(scanner, String.class, "Vehicle ID\t\t\t:");
        try {
            IdentityValidator.validate(id);
        } catch (InvalidIdentificationException e) {
            ExceptionWriter.write(e.getMessage());
            return null;
        }
        String model = (String) InputWriter.safeIn(scanner, String.class, "Vehicle MODEL\t\t:");
        double speed = (double) InputWriter.safeIn(scanner, Double.class, "Vehicle SPEED\t\t:");
        double mileage = (double) InputWriter.safeIn(scanner, Double.class, "Vehicle MILEAGE\t\t:");

        try {
            switch (vType) {
                case 1:
                    return new Car(id, model, speed, mileage);
                case 2:
                case 3:
                    int wheels = (int) InputWriter.safeIn(scanner, Integer.class, "Vehicle WHEELS\t\t:");
                    return (vType == 2)
                            ? new Bus(id, model, speed, mileage, wheels)
                            : new Truck(id, model, speed, mileage, wheels);
                case 4:
                    double altitude = (double) InputWriter.safeIn(scanner, Double.class, "Vehicle ALTITUDE\t:");
                    return new Airplane(id, model, speed, mileage, altitude);
                case 5:
                    boolean sails = (boolean) InputWriter.safeIn(scanner, Boolean.class, "Vehicle HAS SAILS\t:");
                    return new CargoShip(id, model, speed, mileage, sails);
                default:
                    WarningWriter.write("Invalid Vehicle Type");
                    return null;
            }
        } catch (InvalidIdentificationException e) {
            ExceptionWriter.write("Failed to create vehicle: " + e.getMessage());
            return null;
        }
    }

    public static Vehicle createFromCsv(String[] data) throws Exception {
        String type = data[0];
        String id = data[1];
        String model = data[2];
        double speed = Double.parseDouble(data[3]);
        double mileage = 0.0; // Mileage is not saved, so default to 0.

        Vehicle vehicle;
        int stateIndex = 4; // Start reading from index 4 after the common properties

        switch (type) {
            case "Airplane":
                double altitude = Double.parseDouble(data[stateIndex++]);
                vehicle = new Airplane(id, model, speed, mileage, altitude);
                break;
            case "Truck":
                int truckWheels = Integer.parseInt(data[stateIndex++]);
                vehicle = new Truck(id, model, speed, mileage, truckWheels);
                break;
            case "Car":
                int carWheels = Integer.parseInt(data[stateIndex++]);
                vehicle = new Car(id, model, speed, mileage);
                break;
            case "Bus":
                int busWheels = Integer.parseInt(data[stateIndex++]);
                vehicle = new Bus(id, model, speed, mileage, busWheels);
                break;
            case "CargoShip":
                boolean hasSail = Boolean.parseBoolean(data[stateIndex++]);
                vehicle = new CargoShip(id, model, speed, mileage, hasSail);
                break;
            default:
                throw new IllegalArgumentException("Unknown vehicle type in CSV: " + type);
        }

        if (vehicle instanceof FuelConsumable) {
            double fuel = Double.parseDouble(data[stateIndex++]);
            if (fuel > 0) ((FuelConsumable) vehicle).refuel(fuel);
        }
        if (vehicle instanceof PassengerCarrier) {
            stateIndex++; // Skip loading passengerCapacity
            int passengers = Integer.parseInt(data[stateIndex++]);
            if (passengers > 0) ((PassengerCarrier) vehicle).boardPassengers(passengers);
        }
        if (vehicle instanceof CargoCarrier) {
            stateIndex++; // Skip loading cargoCapacity
            double cargo = Double.parseDouble(data[stateIndex++]);
            if (cargo > 0) ((CargoCarrier) vehicle).loadCargo(cargo);
        }

        return vehicle;
    }
}