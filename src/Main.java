
import Abstracts.LandVehicle;
import Core.Truck;
import Extension.Highway;
import Utility.ExceptionWriter;
import Utility.MessageWriter;
import Utility.WarningWriter;
import javax.swing.text.Highlighter;

public class Main {
    public static void main(String[] args) {
        Highway NH1 = new Highway("NH1", "National Highway 1 Delhi to Mumbai", 0);
        Highway NH2 = new Highway("NH2", "National Highway 1 Delhi to Kolkata", 0);

        Truck TR1 = new Truck("TR1", "Indian trucks limited 101", 100, 0, 16, NH1);
        Truck TR2 = new Truck("TR2", "Indian trucks limited 101", 100, 0, 16, NH1);

        TR1.refuel(100000);
        TR2.refuel(100000);
        // LandVehicle TR1 = new Truck("TR1", "Indian trucks limited 101", 100, 0, 16, NH1);

        Thread tTR1 = new Thread(TR1);
        Thread tTR2 = new Thread(TR2);

        tTR1.start();
        tTR2.start();

        try {
            tTR1.join();
            tTR2.join();
        } catch (InterruptedException e) {
            ExceptionWriter.write("joining failed due to Interruption Exception");
        }

        TR1.printInfo();
        TR2.printInfo();
        MessageWriter.write("Highway id:" + NH1.getId() + " name:" + NH1.getName() + " milage:" + NH1.getMileage());

        if (TR1.getCurrentMileage() + TR2.getCurrentMileage() != NH1.getMileage()){
            WarningWriter.write("Mileage of vehicles do not add upto mileage of highway.");
        }
    }
}
