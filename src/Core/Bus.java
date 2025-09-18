package Core;

import Abstracts.LandVehicle;
import Exceptions.InsufficientFuelException;
import Exceptions.InvalidIdentificationException;
import Exceptions.InvalidOperationException;
import Exceptions.OverloadException;
import Interfaces.CargoCarrier;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Interfaces.PassengerCarrier;
import Utility.LoggerWriter;
import Utility.MessageWriter;

public class Bus extends LandVehicle
        implements FuelConsumable, Maintainable, PassengerCarrier, CargoCarrier
{
    private double fuelLevel = 0.0;
    private final int passengerCapacity = 50;
    private int currentPassengers = 0;
    private final double cargoCapacity = 500.0;
    private double currentCargo = 0;
    private boolean maintenanceNeeded;

    /**
     * ABC (Abstract Base Class) Vehicle to provide basic functionalities.
     *
     * @param id      : String used for identification of current Vehicle.
     * @param model   : String representing model of current Vehicle.
     * @param speed   : double value representing maximum speed of current Vehicle.
     * @param mileage : double value representing mileage of current Vehicle.
     * @param wheels  : int value representing number of wheels in the vehicle.
     */
    public Bus(String id, String model, double speed, double mileage, int wheels)
            throws InvalidIdentificationException
    {
        super(id, model, speed, mileage, wheels);
        maintenanceNeeded = mileage >= 10000;
    }

    // encapsulation for private properties, getters and setters.
    @Override
    public int getPassengerCapacity() { return passengerCapacity; }
    @Override
    public int getCurrentPassengers() { return currentPassengers; }
    @Override
    public double getCargoCapacity() { return cargoCapacity; }
    @Override
    public double getCurrentCargo() { return currentCargo; }

    // public void setPassengerCapacity(int count) { passengerCapacity = count; }
    public void setCurrentPassenger(int count) { currentPassengers = count; }
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
        LoggerWriter.write("Bus id:" + getId() + " is Transporting passengers and cargo...");
    }

    @Override
    public double calculateFuelEfficiency() { return 10.0; }

    @Override
    public void printInfo() {
        System.out.println("\t\t\t\t+-------------+");
        System.out.println("\t\t\t\t|             | id\t\t\t:" + getId());
        System.out.println("\t\t\t\t|             | model\t\t:" + getModel());
        System.out.println("\t\t\t\t|             | speed\t\t:" + getMaxSpeed());
        System.out.println("\t\t\t\t|     BUS     | mileage\t\t:" + getCurrentMileage());
        System.out.println("\t\t\t\t|     BUS     | maintained\t:" + !maintenanceNeeded);
        System.out.println("\t\t\t\t|     BUS     | fuel\t\t:" + getFuelLevel());
        System.out.println("\t\t\t\t|     BUS     | p capacity\t:" + passengerCapacity);
        System.out.println("\t\t\t\t|             | p contains\t:" + currentPassengers);
        System.out.println("\t\t\t\t|             | c capacity\t:" + cargoCapacity );
        System.out.println("\t\t\t\t|             | c contains\t:" + currentCargo);
        System.out.println("\t\t\t\t+-------------+");
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
                    "Not enough fuel for bus to travel " + distance +
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
            MessageWriter.write("Maintenance performed for bus id:" + getId());
        maintenanceNeeded = false;
        setCurrentMileage(0x0000);
    }

    // implementations for PassengerCarrier methods which works on passenger*.

    @Override
    public void boardPassengers(int count) throws OverloadException
    {
        if (currentPassengers + count > passengerCapacity)
            throw new OverloadException(
                    "Passengers overflow in bus, expected " + passengerCapacity +
                    " passengers given " + currentPassengers + count + " passengers"
            );
        currentPassengers += count;
    }

    @Override
    public void disembarkPassengers(int count) throws InvalidOperationException {
        if (count > currentPassengers)
            throw new InvalidOperationException(
                    "Cannot remove " + count + " passengers from passenger bus"
                    + "it contains only " + currentPassengers + " passengers"
            );
        currentPassengers -= count;
    }

    // implementation for CargoCarrier Interface method works with cargo*.

    @Override
    public void loadCargo(double weight) throws OverloadException
    {
        if (weight + currentCargo >= cargoCapacity)
            throw new OverloadException(
                    "bus load capacity exceeds the maximum cargo limit: "
                    + cargoCapacity + "kg, amount given: " + weight + "kg"
            );
        currentCargo += weight;
    }

    @Override
    public void unloadCargo(double weight) throws InvalidOperationException
    {
        if (weight > currentCargo)
            throw new InvalidOperationException(
                    "bus cannot unload more than what it contains. contains: "
                    + currentCargo + "kg,  given: " + weight + "kg."
            );
        currentCargo -= weight;
    }
}
