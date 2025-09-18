import Abstracts.Vehicle;
import Core.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FleetSerializer {

    public static void save(List<Vehicle> fleet, String path) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (Vehicle v : fleet) {
                String csvLine = vehicleToCsv(v);
                // Only write non-empty lines to the file
                if (csvLine != null && !csvLine.isEmpty()) {
                    writer.println(csvLine);
                }
            }
        }
    }

    /**
     * This is the complete version that handles ALL vehicle types.
     */
    private static String vehicleToCsv(Vehicle v) {
        // --- THIS IS THE PART THAT WAS INCOMPLETE ---
        if (v instanceof Airplane) {
            Airplane plane = (Airplane) v;
            return String.join(",",
                    "Airplane",
                    plane.getId(),
                    plane.getModel(),
                    String.valueOf(plane.getMaxSpeed()),
                    String.valueOf(plane.getMaxAltitude()),
                    String.valueOf(plane.getFuelLevel()),
                    String.valueOf(plane.getPassengerCapacity()),
                    String.valueOf(plane.getCurrentPassengers()),
                    String.valueOf(plane.getCargoCapacity()),
                    String.valueOf(plane.getCurrentCargo())
            );
        } else if (v instanceof Truck) {
            Truck truck = (Truck) v;
            return String.join(",",
                    "Truck",
                    truck.getId(),
                    truck.getModel(),
                    String.valueOf(truck.getMaxSpeed()),
                    String.valueOf(truck.getNumWheels()),
                    String.valueOf(truck.getFuelLevel()),
                    String.valueOf(truck.getCargoCapacity()),
                    String.valueOf(truck.getCurrentCargo())
            );
        } else if (v instanceof Car) {
            Car car = (Car) v;
            return String.join(",",
                    "Car",
                    car.getId(),
                    car.getModel(),
                    String.valueOf(car.getMaxSpeed()),
                    String.valueOf(car.getNumWheels()),
                    String.valueOf(car.getFuelLevel()),
                    String.valueOf(car.getPassengerCapacity()),
                    String.valueOf(car.getCurrentPassengers())
            );
        } else if (v instanceof Bus) {
            Bus bus = (Bus) v;
            return String.join(",",
                    "Bus",
                    bus.getId(),
                    bus.getModel(),
                    String.valueOf(bus.getMaxSpeed()),
                    String.valueOf(bus.getNumWheels()),
                    String.valueOf(bus.getFuelLevel()),
                    String.valueOf(bus.getPassengerCapacity()),
                    String.valueOf(bus.getCurrentPassengers()),
                    String.valueOf(bus.getCargoCapacity()),
                    String.valueOf(bus.getCurrentCargo())
            );
        } else if (v instanceof CargoShip) {
            CargoShip ship = (CargoShip) v;
            return String.join(",",
                    "CargoShip",
                    ship.getId(),
                    ship.getModel(),
                    String.valueOf(ship.getMaxSpeed()),
                    String.valueOf(ship.getHasSail()),
                    String.valueOf(ship.getFuelLevel()),
                    String.valueOf(ship.getCargoCapacity()),
                    String.valueOf(ship.getCurrentCargo())
            );
        }

        // Return empty for any type not explicitly handled
        return "";
    }

    public static List<Vehicle> load(String path) throws IOException {
        List<Vehicle> loadedFleet = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    if (line.trim().isEmpty()) continue;
                    String[] data = line.split(",");
                    Vehicle vehicle = VehicleFactory.createFromCsv(data);
                    loadedFleet.add(vehicle);
                } catch (Exception e) {
                    System.err.println("Skipping malformed line: " + line + " | Error: " + e.getMessage());
                }
            }
        }
        return loadedFleet;
    }
}