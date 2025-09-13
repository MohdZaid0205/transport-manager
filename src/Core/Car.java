package Core;

import Abstracts.LandVehicle;
import Abstracts.Vehicle;
import Exceptions.InsufficientFuelException;
import Exceptions.InvalidIdentificationException;
import Exceptions.InvalidOperationException;
import Exceptions.OverloadException;
import Interfaces.CargoCarrier;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Interfaces.PassengerCarrier;
import Utility.MessageWriter;

public class Car extends LandVehicle
        implements FuelConsumable, Maintainable, PassengerCarrier
{
    private double fuelLevel = 0.0;
    private final int passengerCapacity = 5;
    private int currentPassengers = 0;
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
    public Car(String id, String model, double speed, double mileage, int wheels)
            throws InvalidIdentificationException
    {
        super(id, model, speed, mileage, wheels);
    }

    // encapsulation for private properties, getters and setters.
    @Override
    public int getPassengerCapacity() { return passengerCapacity; }
    @Override
    public int getCurrentPassengers() { return currentPassengers; }

    // public void setPassengerCapacity(int count) { passengerCapacity = count; }
    public void setCurrentPassenger(int count) { currentPassengers = count; }


    // implementations for abstract class LandVehicle.

    @Override
    public void move(double distance) throws InvalidOperationException
    {
        // TODO: implement this function.
    }

    @Override
    public double calculateFuelEfficiency() { return 15.0; }

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
                    "Not enough fuel for car to travel " + distance +
                    "km needed:" + requiredFuel + ", has:" + fuelLevel
            );
        fuelLevel -= requiredFuel;
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

    // implementations for PassengerCarrier methods which works on passenger*.

    @Override
    public void boardPassengers(int count) throws OverloadException
    {
        if (currentPassengers + count > passengerCapacity)
            throw new OverloadException(
                    "Passengers overflow in car, expected " + passengerCapacity +
                    " passengers given " + currentPassengers + count + " passengers"
            );
        currentPassengers += count;
    }

    @Override
    public void disembarkPassengers(int count) throws InvalidOperationException
    {
        if (count > currentPassengers)
            throw new InvalidOperationException(
                    "Cannot remove " + count + " passengers from passenger car"
                    + "it contains only " + currentPassengers + " passengers"
            );
        currentPassengers -= count;
    }
}
