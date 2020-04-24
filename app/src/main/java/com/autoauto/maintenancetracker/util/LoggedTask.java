package com.autoauto.maintenancetracker.util;

// Task with different information
public class LoggedTask extends TaskTemplate {
    private String actionTaken;
    public String getActionTaken() { return actionTaken; }

    private int milesLoggedAt;
    public int getMilesLoggedAt() { return milesLoggedAt; }

    public LoggedTask(Task task, String actionTaken, int milesLoggedAt) {
        super(task.getName(), task.getDescription(), task.getAlertPeriodMiles());
        this.actionTaken = actionTaken;
        this.milesLoggedAt = milesLoggedAt;
    }
}
