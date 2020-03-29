package com.autoauto.maintenancetracker.util;

public class Vehicle {
    private String make;
    public String getMake() { return make; }

    private String model;
    public String getModel() { return model; }

    private String year;
    public String getYear() { return year; }

    private MaintenanceScheduler maintenanceScheduler;
    public MaintenanceScheduler getMaintenanceScheduler() { return maintenanceScheduler; }

    public Vehicle(String make, String model, String year) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.maintenanceScheduler = new MaintenanceScheduler();
    }
}
