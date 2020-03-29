package com.autoauto.maintenancetracker.util;

public class Task extends TaskTemplate {
    private int currentMiles;

    // purely for identification from outside methods without relying on address comparisons
    private int id;
    public int getId() { return id; }

    public int getAlertMileMark() { return currentMiles + super.getAlertPeriodMiles(); }

    public Task(TaskTemplate taskTemplate, int currentMiles, int id) {
        super(taskTemplate.getName(), taskTemplate.getAlertPeriodMiles());
        this.currentMiles = currentMiles;
        this.id = id;
    }
}
