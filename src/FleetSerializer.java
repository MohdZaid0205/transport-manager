import java.io.*;
import java.util.*;

import Abstracts.Vehicle;
import Core.*;
import Exceptions.InvalidOperationException;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Utility.ExceptionWriter;
import Utility.WarningWriter;
import Validators.IdentityValidator;

public class FleetSerializer {

    public static void save(List<Vehicle> fleet, String path) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            for (Vehicle v : fleet) {
                StringBuilder sb = new StringBuilder();
                sb.append(v.getClass().getSimpleName()).append(",");
                sb.append(v.getId()).append(",");
                sb.append(v.getModel()).append(",");
                sb.append(v.getMaxSpeed()).append(",");
                sb.append(v.getCurrentMileage());

                if (v instanceof Car) {
                    // nothing extra
                } else if (v instanceof Bus) {
                    sb.append(",").append(((Bus) v).getNumWheels());
                } else if (v instanceof Truck) {
                    sb.append(",").append(((Truck) v).getNumWheels());
                } else if (v instanceof Airplane) {
                    sb.append(",").append(((Airplane) v).getMaxAltitude());
                } else if (v instanceof CargoShip) {
                    sb.append(",").append(((CargoShip) v).getHasSail());
                }

                out.println(sb.toString());
            }
        }
    }

    public static List<Vehicle> load(String path) throws IOException {
        List<Vehicle> fleet = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String type = tokens[0];
                String id = tokens[1];
                String model = tokens[2];
                double speed = Double.parseDouble(tokens[3]);
                double mileage = Double.parseDouble(tokens[4]);

                Vehicle v = null;
                switch (type) {
                    case "Car":
                        v = new Car(id, model, speed, mileage);
                        break;
                    case "Bus":
                        int busWheels = Integer.parseInt(tokens[5]);
                        v = new Bus(id, model, speed, mileage, busWheels);
                        break;
                    case "Truck":
                        int truckWheels = Integer.parseInt(tokens[5]);
                        v = new Truck(id, model, speed, mileage, truckWheels);
                        break;
                    case "Airplane":
                        double altitude = Double.parseDouble(tokens[5]);
                        v = new Airplane(id, model, speed, mileage, altitude);
                        break;
                    case "CargoShip":
                        boolean sails = Boolean.parseBoolean(tokens[5]);
                        v = new CargoShip(id, model, speed, mileage, sails);
                        break;
                    default:
                        WarningWriter.write("Unknown vehicle type in file: " + type);
                }
                if (v != null) fleet.add(v);
            }
        }
        return fleet;
    }
}
