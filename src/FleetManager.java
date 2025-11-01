import Abstracts.Vehicle;
import Exceptions.InvalidOperationException;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Utility.ExceptionWriter;
import Validators.IdentityValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FleetManager
{
    public ArrayList<Vehicle> fleet = new ArrayList<>();
    public void addVehicle(Vehicle v) { fleet.add(v); }

    public void removeVehicle(String id) throws InvalidOperationException
    {
        if (!IdentityValidator.find(id))
            throw new InvalidOperationException("Vehicle id is not valid");
        fleet.removeIf(v -> v.getId().equals(id));
        IdentityValidator.remove(id);
    }

    public void startAllJourneys(double distance)
    {
        for (Vehicle v : fleet) {
            try{ v.move(distance); }
            catch(Exception exc){
                ExceptionWriter.write(
                        "Vehicle id: " + v.getId() + " " + exc.getMessage()
                );
            }
        }
    }

    public double getTotalFuelConsumption(double distance)
    {
        double total = 0;
        for (Vehicle v: fleet) {
            try { total += ((FuelConsumable) v).consumeFuel(distance); }
            catch(Exception exc){
                ExceptionWriter.write(
                        "Vehicle id: " + v.getId() + exc.getMessage()
                );
            }
        }
        return total;
    }

    public List<Vehicle> searchByType(Class<?> type)
    {
        List<Vehicle> vehicles = new ArrayList<>();
        for (Vehicle v : fleet) {
            if (type.isInstance(v))
                vehicles.add(v);
        }
        return vehicles;
    }

    public Vehicle getVehicalWithMaximumSpeed()
    {
        return fleet.stream()
                    .max((a, b) -> Double.compare(a.getMaxSpeed(), b.getMaxSpeed()))
                    .orElse(null);
    }

    public Vehicle getVehicleWithMinimumSpeed()
    {
        return fleet.stream()
                .min((a, b) -> Double.compare(a.getMaxSpeed(), b.getMaxSpeed()))
                .orElse(null);
    }

    public Vehicle getVehicleWithMaximumMileage()
    {
        return fleet.stream()
                .max((a, b) -> Double.compare(a.getCurrentMileage(), b.getCurrentMileage()))
                .orElse(null);
    }

    public Vehicle getVehicleWithMinimumMileage()
    {
        return fleet.stream()
                .min((a, b) -> Double.compare(a.getCurrentMileage(), b.getCurrentMileage()))
                .orElse(null);
    }

    public Vehicle getVehicleWithMaximumEfficiency()
    {
        return fleet.stream()
                .max((a, b) -> Double.compare(a.calculateFuelEfficiency(), b.calculateFuelEfficiency()))
                .orElse(null);
    }
    
    public Vehicle getVehicleWithMinimumEfficiency()
    {
        return fleet.stream()
                .min((a, b) -> Double.compare(a.calculateFuelEfficiency(), b.calculateFuelEfficiency()))
                .orElse(null);
    }


    // Fleet sort by efficiency
    public void sortFleetByEfficiency()
    {
        fleet.sort(
                (a, b) -> Double.compare(
                        b.calculateFuelEfficiency(), a.calculateFuelEfficiency()
                )
        );
    }

    // Fleet sort by id
    public void sortFleetById()
    {
        fleet.sort(
                (a, b) -> a.getId().compareTo(b.getId())
        );
    }

    // Sort by model name
    public void sortFleetByModel()
    {
        fleet.sort(
                (a, b) -> a.getModel().compareTo(b.getModel())
        );
    }

    // Sort fleet by speed
    public void sortFleetBySpeed()
    {
        fleet.sort(
                (a, b) -> Double.compare(
                        b.getMaxSpeed(), a.getMaxSpeed()
                )
        );
    }

    public List<Vehicle> getVehiclesNeedingMaintenance() {
        return fleet.stream()
                .filter(
                        v->v instanceof Maintainable &&
                        ((Maintainable) v).needsMaintenance()
                )
                .collect(Collectors.toList());
    }

    public String generateReport()
    {
        StringBuilder report = new StringBuilder();
        report.append("Fleet Report:\n");

        Map<String, Integer> countByType = new HashMap<>();
        for (Vehicle v : fleet) {
            String type = v.getClass().getSimpleName();
            if (!countByType.containsKey(type)) {
                countByType.put(type, 1);
            } else {
                countByType.put(type, countByType.get(type) + 1);
            }
        }

        report.append("\t\t\t\t+-------------+").append("\n");
        report.append("\t\t\t\t|             | Total vehicles: ").append(fleet.size()).append("\n");
        report.append("\t\t\t\t+~~~~~~~~~~~~~+ Count by type:\n");


        for (String type : countByType.keySet()) {
            report.append("\t\t\t\t|   REPORT    |  -> ")
                    .append(type).append(": ")
                    .append(countByType.get(type))
                    .append("\n");
        }

        double totalEfficiency = 0;
        int countEfficiency = 0;
        for (Vehicle v : fleet) {
            totalEfficiency += v.calculateFuelEfficiency();
            countEfficiency++;
        }
        double averageEfficiency = (countEfficiency > 0) ? totalEfficiency / countEfficiency : 0;

        double totalMileage = 0;
        for (Vehicle v : fleet) {
            totalMileage += v.getCurrentMileage();
        }

        int maintenanceCount = 0;
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable m) {
                if (m.needsMaintenance()) {
                    maintenanceCount++;
                }
            }
        }

        report.append("\t\t\t\t+~~~~~~~~~~~~~+ Average efficiency: ").append(averageEfficiency).append(" km/l\n");
        report.append("\t\t\t\t|             | Total mileage: ").append(totalMileage).append(" km\n");
        report.append("\t\t\t\t+-------------+ Vehicles needing maintenance: ").append(maintenanceCount).append("\n");

        return report.toString();
    }

    public Vehicle findVehicle(String id) {
        Vehicle v = null;
        for (Vehicle u : fleet) {
            if (u.getId().equals(id)) {
                v = u;
            }
        }
        return v;
    }
}
