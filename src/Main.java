import Abstracts.LandVehicle;
import Core.Car;
import Exceptions.InvalidIdentificationException;
import Exceptions.InvalidOperationException;
import Extension.Highway;
import Simulation.SimulatorGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Highway highway = new Highway("NH-01", "National Highway 01", 0);
            List<LandVehicle> vehicles = new ArrayList<>();

            Car car1 = new Car("V-001", "Toyota Camry", 180, 0, highway);
            car1.refuel(10);
            vehicles.add(car1);

            Car car2 = new Car("V-002", "Honda Civic", 160, 0, highway);
            car2.refuel(15);
            vehicles.add(car2);

            Car car3 = new Car("V-003", "Ford Mustang", 220, 0, highway);
            car3.refuel(5);
            vehicles.add(car3);

            SwingUtilities.invokeLater(() -> {
                SimulatorGUI gui = new SimulatorGUI(highway, vehicles);
                gui.setVisible(true);
            });

        } catch (InvalidIdentificationException | InvalidOperationException e) {
            e.printStackTrace();
        }
    }
}
