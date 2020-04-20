package com.autoauto.maintenancetracker.util;

// Task that is either upcoming or currently alerted
public class Task extends TaskTemplate {
    // createdMiles = -1 is an inactive task
    // ie. re-scheduled, but the same type of task is currently alerted
    private int createdMiles;
    public int getCreatedMiles() { return createdMiles; }
    public void setCreatedMiles(int miles) { createdMiles = miles; }

    private boolean active = false;
    public boolean isActive(){ return active; }
    public void setActive() { active = true; }

    public int getAlertMileMark() { return createdMiles + super.getAlertPeriodMiles(); }

    public Task(TaskTemplate taskTemplate, int currentMiles) {
        super(taskTemplate.getName(), taskTemplate.getAlertPeriodMiles());
        this.createdMiles = currentMiles;
    }
}
