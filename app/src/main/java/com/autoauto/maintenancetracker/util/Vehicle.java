package com.autoauto.maintenancetracker.util;

import com.autoauto.maintenancetracker.AutoAutoApplication;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

// holds vehicle information and has a maintenance scheduler
public class Vehicle implements Serializable {
    private String make;
    public String getMake() { return make; }

    private String model;
    public String getModel() { return model; }

    private String year;
    public String getYear() { return year; }

    private int miles;
    public int getMiles() { return miles; }
    public void setMiles(int miles, AutoAutoApplication application) {
        this.miles = miles;
        setLastUpdate();
        maintenanceScheduler.setCurrentMiles(miles, application);
    }

    private MaintenanceScheduler maintenanceScheduler;
    public MaintenanceScheduler getMaintenanceScheduler() { return maintenanceScheduler; }

    private Date lastUpdate;
    public Date getLastUpdate() { return lastUpdate; }
    public void setLastUpdate() { lastUpdate = Calendar.getInstance().getTime(); }

    public Vehicle(String make, String model, String year, int miles) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.miles = miles;
        this.maintenanceScheduler = new MaintenanceScheduler(miles);
        setLastUpdate();
    }

    public void setInfo(String make, String model, String year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }
}
