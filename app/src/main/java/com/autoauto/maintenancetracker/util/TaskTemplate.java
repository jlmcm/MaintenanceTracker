package com.autoauto.maintenancetracker.util;

public class TaskTemplate {
    private String name;
    public String getName() { return name; }

    private int alertPeriodMiles;
    public int getAlertPeriodMiles() { return alertPeriodMiles; }
    public void setAlertPeriodMiles(int miles) { alertPeriodMiles = miles; }

    public TaskTemplate(String name, int milesUntilAlert) {
        this.name = name;
        this.alertPeriodMiles = milesUntilAlert;
    }
}
