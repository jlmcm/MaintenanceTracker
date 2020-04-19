package com.autoauto.maintenancetracker.util;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MaintenanceScheduler {
    // currentMiles is kind of redundant
    // but it's the easiest way to re-scope the variable from vehicle
    private int currentMiles;
    public void setCurrentMiles(int miles) { currentMiles = miles; }

    private ArrayList<TaskTemplate> taskList;
    public ArrayList<TaskTemplate> getTaskList() { return taskList; }

    private ArrayList<Task> upcomingTasks;
    public ArrayList<Task> getUpcomingTasks() { return upcomingTasks; }

    private ArrayList<Task> alertedTasks;
    public ArrayList<Task> getAlertedTasks() { return alertedTasks; }

    private ArrayList<Task> expiredTasks;
    public ArrayList<Task> getExpiredTasks() { return expiredTasks; }

    private int nextTaskID;

    public MaintenanceScheduler(int miles) {
        currentMiles = miles;

        taskList = new ArrayList<TaskTemplate>();
        // add new tasks here
        taskList.add(new TaskTemplate("oil", 3000));
        taskList.add(new TaskTemplate("filter", 10000));
        taskList.add(new TaskTemplate("test1", 10000));
        taskList.add(new TaskTemplate("test2", 10000));
        taskList.add(new TaskTemplate("test3", 10000));

        upcomingTasks = new ArrayList<Task>();
        alertedTasks = new ArrayList<Task>();
        expiredTasks = new ArrayList<Task>();

        nextTaskID = 0;
        for (TaskTemplate t : taskList) {
            // for debugging
            alertedTasks.add(new Task(t, currentMiles - 10000, nextTaskID));
            nextTaskID++;

            expiredTasks.add(new Task(t, currentMiles - 20000, nextTaskID));
            nextTaskID++;

            // not for debugging
            upcomingTasks.add(new Task(t, currentMiles, nextTaskID));
            nextTaskID++;
        }
    }

    public void ExpireTask(int position) {
        Task task = alertedTasks.get(position);

        if(task != null) {
            alertedTasks.remove(task);
            expiredTasks.add(task);
        }
        else {
            Log.w("MaintenanceScheduler", "Tried to expire a task that wasn't alerted");
        }
    }

    public void SetMilePeriod(int position, int milePeriod) {
        // update the task templates, as well as current tasks
        // also request an update of all tasks

        TaskTemplate taskTemplate = taskList.get(position);
        taskTemplate.setAlertPeriodMiles(milePeriod);

        for (Task t : upcomingTasks) {
            if(t.getName().equals(taskTemplate.getName())) {
                t.setAlertPeriodMiles(milePeriod);
            }
        }
        UpdateTasks();
    }

    public void AlertTask(Task task) {
        alertedTasks.add(task);
        upcomingTasks.remove(task);

        // imagine it's 2020 and Java still doesn't have an easy to way find an object by property
        TaskTemplate template = null;
        for (TaskTemplate t : taskList) {
            if(t.getName().equals(task.getName())) template = t;
        }

        if(template != null) {
            upcomingTasks.add(new Task(template, currentMiles, nextTaskID));
            nextTaskID++;
        }
    }

    public void UpdateTasks() {
        // this could be optimized
        ArrayList<Task> temp = new ArrayList<Task>();

        for (Task t : upcomingTasks) {
            if(currentMiles < t.getAlertMileMark()) temp.add(t);
        }
        for (Task t : temp) AlertTask(t);
    }
}
