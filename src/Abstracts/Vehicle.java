package Abstracts;

import Exceptions.InvalidOperationException;
import Utility.LoggerWriter;
import Validators.IdentityValidator;
import Exceptions.InvalidIdentificationException;


public abstract class Vehicle
{
    protected String id;
    protected String model;
    protected double maxSpeed;
    protected double currentMileage;

    /**
     * ABC (Abstract Base Class) Vehicle to provide basic functionalities.
     *
     * @param id : String used for identification of current Vehicle.
     * @param model : String representing model of current Vehicle.
     * @param speed : double value representing maximum speed of current Vehicle.
     * @param mileage : double value representing mileage of current Vehicle.
     *
     */
    public Vehicle(String id, String model, long speed, long mileage)
            throws InvalidIdentificationException
    {
        IdentityValidator.validate(id);
        IdentityValidator.insert(id);
        this.id = id;
        this.model = model;
        this.maxSpeed = speed;
        this.currentMileage = mileage;
    }

    abstract void move(double distance) throws InvalidOperationException;
    abstract double calculateFuelEfficiency();
    abstract double estimateJourneyTime(double distance);

    public void displayInfo() {
        LoggerWriter.write(
                "Vehicle ID: " + id + ", Max Speed: " + maxSpeed
                + " km/h, Mileage: " + currentMileage + " kms"
        );
    }

    public String getId() { return id; }
    public String getModel() { return model; }
    public double getCurrentMileage() { return currentMileage; }

}