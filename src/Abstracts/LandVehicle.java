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

    private volatile boolean running = false;
    private volatile boolean paused = false;
    private volatile boolean useSynchronization = false;
    private volatile boolean outOfFuel = false;

    public void setUseSynchronization(boolean useSynchronization) {
        this.useSynchronization = useSynchronization;
    }

    public void start() {
        if (!running) {
            running = true;
            paused = false;
            new Thread(this).start();
        }
    }

    public void stop() {
        running = false;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public boolean isOutOfFuel() {
        return outOfFuel;
    }
    
    public void setOutOfFuel(boolean outOfFuel) {
        this.outOfFuel = outOfFuel;
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (paused || outOfFuel) {
                    Thread.sleep(100);
                    continue;
                }

                double distance = 1.0;

                try {
                    this.move(distance);

                    if (useSynchronization) {
                        highway.synchronizedIncrementMileage((int) distance);
                    } else {
                        highway.unsynchronizedIncrementMileage((int) distance);
                    }

                } catch (Exceptions.InsufficientFuelException e) {
                    outOfFuel = true;
                } catch (InvalidOperationException e) {
                    e.printStackTrace();
                }

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
