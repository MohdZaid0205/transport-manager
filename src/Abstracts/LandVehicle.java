package Abstracts;

import Exceptions.InvalidIdentificationException;
import Exceptions.InvalidOperationException;
import Extension.Highway;
import Utility.ExceptionWriter;

public abstract class LandVehicle 
    extends Vehicle implements Runnable
{
    private int numWheels;
    public volatile Highway highway;

    /**
     * ABC (Abstract Base Class) Vehicle to provide basic functionalities.
     *
     * @param id : String used for identification of current Vehicle.
     * @param model : String representing model of current Vehicle.
     * @param speed : double value representing maximum speed of current Vehicle.
     * @param mileage : double value representing mileage of current Vehicle.
     * @param wheels : int value representing number of wheels in the vehicle.
     */
    public LandVehicle(String id, String model, double speed, double mileage, int wheels, Highway highway)
            throws InvalidIdentificationException
    {
        super(id, model, speed, mileage);
        this.numWheels = wheels;
        this.highway = highway;
    }

    @Override
    public double estimateJourneyTime(double distance) {
        return (distance / getMaxSpeed()) * 1.1;
    }

    public abstract void move(double distance) throws InvalidOperationException;
    public abstract double calculateFuelEfficiency();

    public int getNumWheels() { return numWheels; }
    public void setNumWheels(int wheels) { numWheels = wheels; }

    @Override
    public void run(){
        try {
            for (int i = 0; i < 100000; i++) {
                this.move(1);
                this.highway.unsynchronizedIncrementMileage(1);
                Thread.sleep(0);
            }
        } catch (InterruptedException e){
            ExceptionWriter.write("inturrupted land vehicle id:" + this.getId()
                + " while running on highway id:" + this.highway.getId()
            );
        } finally {

        }
    }
}
