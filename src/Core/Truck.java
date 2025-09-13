package Core;

import Abstracts.LandVehicle;
import Exceptions.InsufficientFuelException;
import Exceptions.InvalidIdentificationException;
import Exceptions.InvalidOperationException;
import Exceptions.OverloadException;
import Interfaces.CargoCarrier;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Utility.MessageWriter;

public class Truck extends LandVehicle
        implements FuelConsumable, Maintainable, CargoCarrier
{
    private double fuelLevel = 0.0;
    private final double cargoCapacity = 5000.0;
    private double currentCargo = 0;
    private boolean maintenanceNeeded;

    /**
     * Truck cargo to move weighted amount of cargo load for transport.
     *
     * @param id      : String used for identification of current Vehicle.
     * @param model   : String representing model of current Vehicle.
     * @param speed   : double value representing maximum speed of current Vehicle.
     * @param mileage : double value representing mileage of current Vehicle.
     * @param wheels  : int value representing number of wheels in the vehicle.
     */
    public Truck(String id, String model, double speed, double mileage, int wheels)
            throws InvalidIdentificationException
    {
        super(id, model, speed, mileage, wheels);
        maintenanceNeeded = mileage >= 10000;
    }

    // encapsulation for private properties, getters and setters.
    @Override
    public double getCargoCapacity() { return cargoCapacity; }
    @Override
    public double getCurrentCargo() { return currentCargo; }

    // public void setCargoCapacity(double capacity) { cargoCapacity = capacity; }
    public void setCurrentCargo(int capacity) { currentCargo = capacity; }

    // implementations for abstract class LandVehicle.

    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException
    {
        if (distance < 0)
            throw new InvalidOperationException("Distance cannot be smaller than zero");
        double consumed = consumeFuel(distance);
        fuelLevel -= consumed;
        setCurrentMileage(getCurrentMileage() + distance);
        MessageWriter.write("Truck id:" + getId() + " Hauling Cargo ...");
    }

    @Override
    public double calculateFuelEfficiency()
    {
        if (currentCargo/cargoCapacity > 0.5)
            return 8.0*0.9;
        return 8.0;
    }

    // implementation for FuelConsumable interface and it deals on fuelLevel

    @Override
    public void refuel(double amount) throws InvalidOperationException
    {
        if (amount <= 0)
            throw new InvalidOperationException(
                    "refuel amount must be greater than 0 given:" + amount
            );
        fuelLevel += amount;
    }

    @Override
    public double getFuelLevel() {  return fuelLevel; }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException
    {
        double requiredFuel = distance/calculateFuelEfficiency();
        if (fuelLevel <= requiredFuel)
            throw new InsufficientFuelException(
                    "Not enough fuel for truck to travel " + distance +
                    "km needed:" + requiredFuel + ", has:" + fuelLevel
            );
        return requiredFuel;
    }

    // implementations for Maintainable Interface methods that works with maintenanceNeeded
    @Override
    public void scheduleMaintenance() { maintenanceNeeded = true; }
    @Override
    public boolean needsMaintenance() { return maintenanceNeeded; }

    @Override
    public void performMaintenance()
    {
        if (needsMaintenance())
            MessageWriter.write("Maintenance performed for car id:" + getId());
        maintenanceNeeded = false;
    }

    // implementation for CargoCarrier Interface method works with cargo*.

    @Override
    public void loadCargo(double weight) throws OverloadException
    {
        if (weight + currentCargo >= cargoCapacity)
            throw new OverloadException(
                    "truck load capacity exceeds the maximum cargo limit: "
                    + cargoCapacity + "kg, amount given: " + weight + "kg"
            );
        currentCargo += weight;
    }

    @Override
    public void unloadCargo(double weight) throws InvalidOperationException
    {
        if (weight > currentCargo)
            throw new InvalidOperationException(
                    "truck cannot unload more than what it contains. contains: "
                    + currentCargo + "kg,  given: " + weight + "kg."
            );
        currentCargo -= weight;
    }
}
