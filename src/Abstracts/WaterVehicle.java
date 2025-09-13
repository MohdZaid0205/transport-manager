package Abstracts;

import Exceptions.InvalidIdentificationException;

public abstract class WaterVehicle extends Vehicle{
    private boolean hasSail;

    /**
     * ABC (Abstract Base Class) Vehicle to provide basic functionalities.
     *
     * @param id : String used for identification of current Vehicle.
     * @param model : String representing model of current Vehicle.
     * @param speed : double value representing maximum speed of current Vehicle.
     * @param mileage : double value representing mileage of current Vehicle.
     * @param sail : boolean value representing that water vehicle has sails.
     */
    public WaterVehicle(String id, String model, long speed, long mileage, boolean sail)
            throws InvalidIdentificationException
    {
        super(id, model, speed, mileage);
        hasSail = sail;
    }

    @Override
    public double estimateJourneyTime(double distance) {
        return (distance / maxSpeed) * 1.15;
    }


    public abstract void move(double distance) throws InvalidIdentificationException;
    public abstract double calculateFuelEfficiency();
}
