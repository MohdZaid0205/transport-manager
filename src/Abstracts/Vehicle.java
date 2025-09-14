package Abstracts;

import Exceptions.InvalidOperationException;
import Utility.LoggerWriter;
import Validators.IdentityValidator;
import Exceptions.InvalidIdentificationException;


public abstract class Vehicle
{
    private String id;
    private String model;
    private double maxSpeed;
    private double currentMileage;

    /**
     * ABC (Abstract Base Class) Vehicle to provide basic functionalities.
     *
     * @param id : String used for identification of current Vehicle.
     * @param model : String representing model of current Vehicle.
     * @param speed : double value representing maximum speed of current Vehicle.
     * @param mileage : double value representing mileage of current Vehicle.
     *
     */
    public Vehicle(String id, String model, double speed, double mileage)
            throws InvalidIdentificationException
    {
        IdentityValidator.validate(id);
        IdentityValidator.insert(id);
        this.id = id;
        this.model = model;
        this.maxSpeed = speed;
        this.currentMileage = mileage;
    }

    public abstract void move(double distance) throws InvalidOperationException;
    public abstract double calculateFuelEfficiency();
    public abstract double estimateJourneyTime(double distance);

    public void displayInfo() {
        LoggerWriter.write(
                "Vehicle ID: " + id + ", Max Speed: " + maxSpeed
                + " km/h, Mileage: " + currentMileage + " kms"
        );
    }

    public abstract void printInfo();

    public String getId() { return id; }
    public String getModel() { return model; }
    public double getMaxSpeed() { return maxSpeed; }
    public double getCurrentMileage() { return currentMileage; }

    public void setId(String id) { this.id = id; }
    public void setModel(String model) { this.model = model; }
    public void setMaxSpeed(double speed) { maxSpeed = speed; }
    public void setCurrentMileage(double mileage) { currentMileage = mileage; }

}