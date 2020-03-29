package com.autoauto.maintenancetracker.util;

public class Vehicle {
    private String make;
    public String getMake() { return make; }

    private String model;
    public String getModel() { return model; }

    private String year;
    public String getYear() { return year; }

    private int miles;
    public int getMiles() { return miles; }
    public void setMiles(int miles) {
        this.miles = miles;
        maintenanceScheduler.setCurrentMiles(miles);
    }

    private MaintenanceScheduler maintenanceScheduler;
    public MaintenanceScheduler getMaintenanceScheduler() { return maintenanceScheduler; }

    public Vehicle(String make, String model, String year, int miles) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.maintenanceScheduler = new MaintenanceScheduler(miles);
    }
}
