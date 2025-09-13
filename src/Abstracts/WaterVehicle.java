package Abstracts;

import Exceptions.InvalidIdentificationException;
import Exceptions.InvalidOperationException;

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
    public WaterVehicle(String id, String model, double speed, double mileage, boolean sail)
            throws InvalidIdentificationException
    {
        super(id, model, speed, mileage);
        hasSail = sail;
    }

    @Override
    public double estimateJourneyTime(double distance) {
        return (distance / getMaxSpeed()) * 1.15;
    }

    public abstract void move(double distance) throws InvalidOperationException;
    public abstract double calculateFuelEfficiency();

    public boolean isHasSail() { return hasSail; }
    public void setHasSail(boolean sail) { hasSail = sail; }
}
