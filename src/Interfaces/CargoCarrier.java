package Interfaces;

import Exceptions.InvalidOperationException;
import Exceptions.OverloadException;

public interface CargoCarrier {

    /**
     * Loads cargo onto the vehicle.
     *
     * @param weight weight of cargo to load (kg).
     * @throws OverloadException if loading exceeds maximum capacity.
     */
    void loadCargo(double weight) throws OverloadException;

    /**
     * Unloads cargo from the vehicle.
     *
     * @param weight weight of cargo to unload (kg).
     * @throws InvalidOperationException if weight exceeds current cargo load.
     */
    void unloadCargo(double weight) throws InvalidOperationException;

    /**
     * Returns the maximum cargo capacity of the vehicle.
     *
     * @return maximum cargo capacity (kg).
     */
    double getCargoCapacity();

    /**
     * Returns the current cargo weight being carried by the vehicle.
     *
     * @return current cargo weight (kg).
     */
    double getCurrentCargo();
}
