# Fleet Highway Simulator

## How to Run
1. Open the project in your IDE (IntelliJ IDEA recommended).
2. Navigate to `src/Main.java`.
3. Run the `main` method.
4. The GUI will launch, allowing you to control the simulation.

## Design Overview
The system uses a multi-threaded approach where each vehicle runs in its own thread.
- **Model**: Reuses `Vehicle`, `Car`, `Bus`, `Truck` from `src/Abstracts` and `src/Core`.
- **Simulation**: `Highway` class manages the shared distance counter. `VehicleThread` handles the execution loop for each vehicle.
- **UI**: `MainFrame` and `VehiclePanel` provide the graphical interface.

## Race Condition
The "Enable Synchronization" toggle in the GUI controls whether the shared highway distance is updated safely or unsafely.
- **Unsafe**: Multiple threads increment the counter simultaneously without locks, leading to lost updates.
- **Safe**: Updates are synchronized, ensuring the total distance is accurate.

## GUI Thread Safety
Swing components are updated on the Event Dispatch Thread (EDT) to ensure thread safety. `SwingUtilities.invokeLater` is used for updates from vehicle threads.

## UML Diagrams
*(Place UML diagrams in `res/` and link them here)*

## Screenshots
*(Place screenshots in `res/` and link them here)*