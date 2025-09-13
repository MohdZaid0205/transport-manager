package Abstracts;

import Exceptions.InvalidIdentificationException;
import Exceptions.InvalidOperationException;

public abstract class AirVehicle extends Vehicle
{
    private double maxAltitude;

    /**
     * ABC (Abstract Base Class) Vehicle to provide basic functionalities.
     *
     * @param id : String used for identification of current Vehicle.
     * @param model : String representing model of current Vehicle.
     * @param speed : double value representing maximum speed of current Vehicle.
     * @param mileage : double value representing mileage of current Vehicle.
     * @param altitude : double representing maximum achievable altitude by the vehicle.
     */
    public AirVehicle(String id, String model, long speed, long mileage, double altitude)
            throws InvalidIdentificationException
    {
        super(id, model, speed, mileage);
        maxAltitude = altitude;
    }

    @Override
    public double estimateJourneyTime(double distance) {
        return (distance / maxSpeed) * 0.95;
    }


    public abstract void move(double distance) throws InvalidOperationException;
    public abstract double calculateFuelEfficiency();

}
