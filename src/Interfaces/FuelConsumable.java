package Interfaces;

import Exceptions.InsufficientFuelException;
import Exceptions.InvalidOperationException;

public interface FuelConsumable {
    /** Refuels the vehicle with the given amount of fuel.
     * @param amount amount of fuel to add (liters).
     * @throws InvalidOperationException if amount ≤ 0.
     */
    void refuel(double amount) throws InvalidOperationException;

    /** Returns the current fuel level of the vehicle.
     * @return current fuel level in liters.
     */
    double getFuelLevel();

    /** Consumes fuel based on distance traveled and the vehicle's fuel efficiency.
     * @param distance distance traveled (km).
     * @return amount of fuel consumed (liters).
     * @throws InsufficientFuelException if current fuel is not enough to cover the distance.
     */
    double consumeFuel(double distance) throws InsufficientFuelException;
}
