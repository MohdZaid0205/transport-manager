package Abstracts;

import Exceptions.InvalidIdentificationException;

public abstract class LandVehicle extends Vehicle
{
    private int numWheels;

    /**
     * ABC (Abstract Base Class) Vehicle to provide basic functionalities.
     *
     * @param id : String used for identification of current Vehicle.
     * @param model : String representing model of current Vehicle.
     * @param speed : double value representing maximum speed of current Vehicle.
     * @param mileage : double value representing mileage of current Vehicle.
     * @param wheels : int value representing number of wheels in the vehicle.
     */
    public LandVehicle(String id, String model, long speed, long mileage, int wheels)
            throws InvalidIdentificationException
    {
        super(id, model, speed, mileage);
        numWheels = wheels;
    }

    @Override
    public double estimateJourneyTime(double distance) {
        return (distance / maxSpeed) * 1.1;
    }


    public abstract void move(double distance);
    public abstract double calculateFuelEfficiency();
}
