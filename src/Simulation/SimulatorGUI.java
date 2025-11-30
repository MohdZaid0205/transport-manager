package Simulation;

import Abstracts.LandVehicle;
import Core.Car;
import Extension.Highway;
import Exceptions.InvalidOperationException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SimulatorGUI extends JFrame {
    private final Highway highway;
    private final List<LandVehicle> vehicles;
    private final DefaultTableModel tableModel;
    private final JLabel totalDistanceLabel;
    private final JCheckBox syncCheckBox;
    private final Timer uiTimer;

    public SimulatorGUI(Highway highway, List<LandVehicle> vehicles) {
        this.highway = highway;
        this.vehicles = vehicles;

        setTitle("Fleet Highway Simulator");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        totalDistanceLabel = new JLabel("Total Highway Distance: 0 km");
        totalDistanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(totalDistanceLabel);

        syncCheckBox = new JCheckBox("Enable Synchronization");
        syncCheckBox.addActionListener(e -> updateSynchronization());
        topPanel.add(syncCheckBox);

        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Model", "Max Speed", "Mileage", "Fuel", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> startSimulation());
        bottomPanel.add(startButton);

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> pauseSimulation());
        bottomPanel.add(pauseButton);

        JButton resumeButton = new JButton("Resume");
        resumeButton.addActionListener(e -> resumeSimulation());
        bottomPanel.add(resumeButton);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> stopSimulation());
        bottomPanel.add(stopButton);

        JButton refuelButton = new JButton("Refuel Selected");
        refuelButton.addActionListener(e -> refuelSelectedVehicle(table));
        bottomPanel.add(refuelButton);

        add(bottomPanel, BorderLayout.SOUTH);
        updateTable();
        uiTimer = new Timer(100, e -> updateUI());
        uiTimer.start();
    }

    private void startSimulation() {
        for (LandVehicle v : vehicles) {
            v.start();
        }
    }

    private void pauseSimulation() {
        for (LandVehicle v : vehicles) {
            v.pause();
        }
    }

    private void resumeSimulation() {
        for (LandVehicle v : vehicles) {
            v.resume();
        }
    }

    private void stopSimulation() {
        highway.setMileage(0);
        for (LandVehicle v : vehicles) {
            v.setCurrentMileage(0);
        }
    }

    private void updateSynchronization() {
        boolean sync = syncCheckBox.isSelected();
        for (LandVehicle v : vehicles) {
            v.setUseSynchronization(sync);
        }
    }

    private void refuelSelectedVehicle(JTable table) {
        for (LandVehicle v : vehicles) {
            if (v instanceof Car) {
                try {
                    ((Car) v).refuel(50);
                    v.setOutOfFuel(false);
                    v.resume();
                } catch (InvalidOperationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateUI() {
        totalDistanceLabel.setText("Total Highway Distance: " + highway.getMileage() + " km");
        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (LandVehicle v : vehicles) {
            String status = "Running";
            if (v.isOutOfFuel()) {
                status = "Out of Fuel";
            }
            
            double fuel = 0;
            if (v instanceof Car) {
                fuel = ((Car) v).getFuelLevel();
            }

            Object[] row = {
                    v.getId(),
                    v.getModel(),
                    v.getMaxSpeed(),
                    String.format("%.2f", v.getCurrentMileage()),
                    String.format("%.2f", fuel),
                    status
            };
            tableModel.addRow(row);
        }
    }
}
