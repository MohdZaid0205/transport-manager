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

    // methods for manipulating fleet and query fleet.

    public void addVehicle(Vehicle v) { fleet.add(v); }

    public void removeVehicle(String id) throws InvalidOperationException
    {
        if (IdentityValidator.validate(id))
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
                        "Vehicle id: " + v.getId() + exc.getMessage()
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
        return fleet.stream().filter(
                v -> v.getClass().equals(type)
        ).collect(Collectors.toList());
    }

    public void sortFleetByEfficiency()
    {
        fleet.sort(
                (a, b) -> Double.compare(
                        b.calculateFuelEfficiency(), a.calculateFuelEfficiency()
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
        report.append("Total vehicles: ").append(fleet.size()).append("\n");

        Map<String, Integer> countByType = new HashMap<>();
        for (Vehicle v : fleet) {
            String type = v.getClass().getSimpleName();
            if (!countByType.containsKey(type)) {
                countByType.put(type, 1);
            } else {
                countByType.put(type, countByType.get(type) + 1);
            }
        }

        report.append("Count by type:\n");
        for (String type : countByType.keySet()) {
            report.append(" - ").append(type).append(": ").append(countByType.get(type)).append("\n");
        }

        double totalEfficiency = 0;
        int countEfficiency = 0;
        for (Vehicle v : fleet) {
            totalEfficiency += v.calculateFuelEfficiency();
            countEfficiency++;
        }
        double averageEfficiency = (countEfficiency > 0) ? totalEfficiency / countEfficiency : 0;
        report.append("Average efficiency: ").append(averageEfficiency).append(" km/l\n");

        double totalMileage = 0;
        for (Vehicle v : fleet) {
            totalMileage += v.getCurrentMileage();
        }
        report.append("Total mileage: ").append(totalMileage).append(" km\n");

        int maintenanceCount = 0;
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable m) {
                if (m.needsMaintenance()) {
                    maintenanceCount++;
                }
            }
        }
        report.append("Vehicles needing maintenance: ").append(maintenanceCount).append("\n");

        return report.toString();
    }
}
