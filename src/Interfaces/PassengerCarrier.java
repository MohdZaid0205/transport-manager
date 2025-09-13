package Interfaces;

import Exceptions.InvalidOperationException;
import Exceptions.OverloadException;

public interface PassengerCarrier {

    /**
     * Boards passengers onto the vehicle.
     *
     * @param count number of passengers to board.
     * @throws OverloadException if boarding exceeds maximum capacity.
     */
    void boardPassengers(int count) throws OverloadException;

    /**
     * Disembarks passengers from the vehicle.
     *
     * @param count number of passengers to disembark.
     * @throws InvalidOperationException if count exceeds current passengers onboard.
     */
    void disembarkPassengers(int count) throws InvalidOperationException;

    /**
     * Returns the maximum passenger capacity of the vehicle.
     *
     * @return maximum passenger capacity.
     */
    int getPassengerCapacity();

    /**
     * Returns the current number of passengers onboard.
     *
     * @return current passenger count.
     */
    int getCurrentPassengers();
}
