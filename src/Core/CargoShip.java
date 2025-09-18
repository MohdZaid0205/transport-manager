package Core;

import Abstracts.WaterVehicle;
import Exceptions.InsufficientFuelException;
import Exceptions.InvalidIdentificationException;
import Exceptions.InvalidOperationException;
import Exceptions.OverloadException;
import Interfaces.CargoCarrier;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Utility.LoggerWriter;
import Utility.MessageWriter;

public class CargoShip extends WaterVehicle
        implements FuelConsumable, Maintainable, CargoCarrier
{
    private double fuelLevel = 0.0;
    private final double cargoCapacity = 50000;
    private double currentCargo = 0;
    private boolean maintenanceNeeded;

    /**
     * ABC (Abstract Base Class) Vehicle to provide basic functionalities.
     *
     * @param id      : String used for identification of current Vehicle.
     * @param model   : String representing model of current Vehicle.
     * @param speed   : double value representing maximum speed of current Vehicle.
     * @param mileage : double value representing mileage of current Vehicle.
     * @param sail    : boolean value representing that water vehicle has sails.
     */
    public CargoShip(String id, String model, double speed, double mileage, boolean sail)
            throws InvalidIdentificationException
    {
        super(id, model, speed, mileage, sail);
        maintenanceNeeded = mileage >= 10000;
    }

    // encapsulation for private properties, getters and setters.
    @Override
    public double getCargoCapacity() { return cargoCapacity; }
    @Override
    public double getCurrentCargo() { return currentCargo; }

    // public void setCargoCapacity(double capacity) { cargoCapacity = capacity; }
    public void setCurrentCargo(int capacity) { currentCargo = capacity; }

    // implementations for abstract class AirVehicle.

    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException
    {
        if (distance < 0)
            throw new InvalidOperationException("Distance cannot be smaller than zero");
        double consumed = consumeFuel(distance);
        fuelLevel -= consumed;
        setCurrentMileage(getCurrentMileage() + distance);
        LoggerWriter.write("CargoShip id:" + getId() + " is Sailing with cargo...");
    }

    @Override
    public double calculateFuelEfficiency()
    {
        if (getHasSail())
            return 0;
        return 5.0;
    }

    @Override
    public void printInfo() {
        System.out.println("\t\t\t\t+-------------+");
        System.out.println("\t\t\t\t|             | id\t\t\t:" + getId());
        System.out.println("\t\t\t\t|             | model\t\t:" + getModel());
        System.out.println("\t\t\t\t|             | speed\t\t:" + getMaxSpeed());
        System.out.println("\t\t\t\t|  CARGOSHIP  | mileage\t\t:" + getCurrentMileage());
        System.out.println("\t\t\t\t|  CARGOSHIP  | maintained\t:" + !maintenanceNeeded);
        System.out.println("\t\t\t\t|  CARGOSHIP  | fuel\t\t:" + getFuelLevel());
        System.out.println("\t\t\t\t|             | sail\t\t:" + getHasSail());
        System.out.println("\t\t\t\t|             | c capacity\t:" + cargoCapacity );
        System.out.println("\t\t\t\t|             | c contains\t:" + currentCargo);
        System.out.println("\t\t\t\t+-------------+");
    }

    // implementation for FuelConsumable interface and it deals on fuelLevel

    @Override
    public void refuel(double amount) throws InvalidOperationException
    {
        if (getHasSail())
            throw new InvalidOperationException(
                    "Cargo Ship id:" + getId() +
                    " cannot refuel cargo which runs on sails."
            );
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
        if (getHasSail())
            return 0.0;
        double requiredFuel = distance/calculateFuelEfficiency();
        if (fuelLevel <= requiredFuel)
            throw new InsufficientFuelException(
                    "Not enough fuel for CargoShip to travel " + distance +
                    "km needed:" + requiredFuel + ", has:" + fuelLevel
            );
        return requiredFuel;
    }

    // implementations for Maintainable Interface methods that works with maintenanceNeeded
    @Override
    public void scheduleMaintenance() { maintenanceNeeded = true; }
    @Override
    public boolean needsMaintenance() { return getCurrentMileage() > 10000; }

    @Override
    public void performMaintenance()
    {
        if (needsMaintenance())
            MessageWriter.write("Maintenance performed for CargoShip id:" + getId());
        maintenanceNeeded = false;
        setCurrentMileage(0x0000);
    }

    // implementation for CargoCarrier Interface method works with cargo*.

    @Override
    public void loadCargo(double weight) throws OverloadException
    {
        if (weight + currentCargo >= cargoCapacity)
            throw new OverloadException(
                    "cargoShip load capacity exceeds the maximum cargo limit: "
                    + cargoCapacity + "kg, amount given: " + weight + "kg"
            );
        currentCargo += weight;
    }

    @Override
    public void unloadCargo(double weight) throws InvalidOperationException {
        if (weight > currentCargo)
            throw new InvalidOperationException(
                    "cargoShip cannot unload more than what it contains. contains: "
                    + currentCargo + "kg,  given: " + weight + "kg."
            );
        currentCargo -= weight;
    }
}
