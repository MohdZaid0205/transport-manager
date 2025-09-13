package Interfaces;

public interface Maintainable {

    /** Schedules maintenance for the vehicle by setting the maintenance flag.*/
    void scheduleMaintenance();

    /** Checks if the vehicle needs maintenance.
     * @return true if mileage exceeds 10,000 km or maintenance is flagged, false otherwise.
     */
    boolean needsMaintenance();

    /** Performs maintenance on the vehicle, resetting the maintenance flag
     * and printing a confirmation message.
     */
    void performMaintenance();
}
