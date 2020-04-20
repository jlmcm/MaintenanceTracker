package com.autoauto.maintenancetracker.util;

import java.io.Serializable;

// template for Tasks that holds basic data for re-scheduling
public class TaskTemplate implements Serializable, Comparable {
    private String name;
    public String getName() { return name; }

    private int alertPeriodMiles;
    public int getAlertPeriodMiles() { return alertPeriodMiles; }
    public void setAlertPeriodMiles(int miles) { alertPeriodMiles = miles; }

    public TaskTemplate(String name, int milesUntilAlert) {
        this.name = name;
        this.alertPeriodMiles = milesUntilAlert;
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof TaskTemplate)) throw new IllegalArgumentException();
        if(alertPeriodMiles == ((TaskTemplate) o).alertPeriodMiles) return 0;
        return alertPeriodMiles - ((TaskTemplate) o).alertPeriodMiles;
    }
}
