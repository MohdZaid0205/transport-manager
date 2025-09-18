# Transportation Fleet Management System `Assignment`.

This repository contains java source file implemented by [Mohd Zaid]() `2024353` 
as part of assignment 1 of Advanced programming at **IIIT Delhi**.

## Overview 

Tihis program is a Java-based simulation of a transportation fleet management
system for a logistics company. The system is designed to manage a diverse
fleet of vehicles operating across land, air, and water, including cars, trucks,
buses, airplanes, and cargo ships.

## Project `Structure`.

The project is organized into packages to ensure a clean separation of concerns

```text
src/
├── Abstracts/      # Base abstract classes (Vehicle, LandVehicle, etc.)
├── Core/           # Concrete vehicle implementations (Car, Truck, etc.)
├── Exceptions/     # Custom exception classes for error handling
├── Interfaces/     # Interfaces for modular behaviors
├── Utility/        # Helper classes (Writers, VehicleFactory, etc.)
└── Validators/     # Validation logic and other core classes
```

## Compile and Run `[USAGE]`

following code works on current working directory, out of source file.
```bash
# ⚠️ do not cd into the src directory, stay in root of this project.
# ⚠️ you may need to make build folder using mkdir or touch "bin" folder.
javac -d bin -sourcepath src src/Main.java  # for class file compilation.
#in order to use the compiled java file, use java to invoke Main and use bin.
java -cp bin Main # also sets bin as directory that contains all other .class.
```

## `UML` Diagrams

following diagram explains relations between `ABCs` and their implementations.
![ABC DIAGRAM](res/AbstractInheritance.png)

following diagram explains relations between `Interfaces` and their implementations.
![INT DIAGRAM](res/InterfacesImplementation.png)
