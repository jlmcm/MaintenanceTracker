package com.autoauto.maintenancetracker.util;

import android.util.Log;

import com.autoauto.maintenancetracker.AutoAutoApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

// handles scheduling of tasks for the Vehicle
public class MaintenanceScheduler implements Serializable {
    // currentMiles is a re-scoped copy from Vehicle
    private int currentMiles;
    // this is a little overloaded but w/e
    public void setCurrentMiles(int miles, AutoAutoApplication application) {
        currentMiles = miles;
        UpdateTasks(application);
    }

    private ArrayList<TaskTemplate> taskList;
    public ArrayList<TaskTemplate> getTaskList() { return taskList; }

    private ArrayList<Task> upcomingTasks;
    public ArrayList<Task> getUpcomingTasks() { return upcomingTasks; }

    private ArrayList<Task> alertedTasks;
    public ArrayList<Task> getAlertedTasks() { return alertedTasks; }

    private ArrayList<LoggedTask> expiredTasks;
    public ArrayList<LoggedTask> getExpiredTasks() { return expiredTasks; }

    public MaintenanceScheduler(int miles) {
        currentMiles = miles;

        taskList = new ArrayList<TaskTemplate>();

        // master list of tasks to be performed
        taskList.add(new TaskTemplate("Oil and Filter",
                "Change every 3000-5000 miles. Replace motor oil and filter type "
                + "following manufacturer specifications.", 3000));
        taskList.add(new TaskTemplate("Tire Rotation",
                "Recommended every 6000-8000 or every 6 months.", 6000));
        taskList.add(new TaskTemplate("Battery",
                "Inspect and test every 35000-50000 miles and replace every 3 years or "
                + "as needed.", 35000));
        taskList.add(new TaskTemplate("Brake Pads/Fluid",
                "Inspect with every oil change and replace every 50000 miles or as needed.",
                50000));
        taskList.add(new TaskTemplate("Coolant",
                "Change after the first 60000 miles, then every 30000 miles. "
                + "Check coolant type before replacing." ,30000));
        taskList.add(new TaskTemplate("Air Filter",
                "Replace every 15000-30000 miles." ,15000));
        taskList.add(new TaskTemplate("Fuel Filter",
                "Replace every 30000 miles or every 2 years." ,30000));
        taskList.add(new TaskTemplate("Spark Plugs",
                "Replace every 40000 miles." ,40000));
        taskList.add(new TaskTemplate("Ignition System",
                "Inspect every 45000 miles." ,45000));
        taskList.add(new TaskTemplate("Suspension",
                "Inspect every 45000 miles." ,45000));

        Collections.sort(taskList);

        upcomingTasks = new ArrayList<Task>();
        alertedTasks = new ArrayList<Task>();
        expiredTasks = new ArrayList<LoggedTask>();

        for (TaskTemplate t : taskList) {
            Task task = new Task(t, currentMiles);
            task.setActive();
            upcomingTasks.add(task);
        }
    }

    public void ResetTasks() {
        upcomingTasks.clear();
        alertedTasks.clear();
        expiredTasks.clear();

        for (TaskTemplate t : taskList) {
            Task task = new Task(t, currentMiles);
            task.setActive();
            upcomingTasks.add(task);
        }
    }

    // helper search funcitons
    public Task findTaskWithName(String name, ArrayList<Task> tasks) {
        for(Task t: tasks)
        {
            if(t.getName().equals(name)) return t;
        }
        return null;
    }

    public TaskTemplate findTaskTemplateWithName(String name, ArrayList<TaskTemplate> tasks) {
        for(TaskTemplate t: tasks)
        {
            if(t.getName().equals(name)) return t;
        }
        return null;
    }

    // move a Task from alerted to logged
    public void ExpireTask(int position, String action) {
        Task task = alertedTasks.get(position);

        if(task != null) {
            alertedTasks.remove(task);
            expiredTasks.add(0, new LoggedTask(task, action, currentMiles));

            // activate the corresponding current task
            Task upcoming = findTaskWithName(task.getName(), upcomingTasks);
            if(upcoming != null)
            {
                upcoming.setActive();
                upcoming.setCreatedMiles(currentMiles);
            }
            else Log.e("MaintenanceScheduler", "Tried to activate a task that isn't upcoming");
        }
        else {
            Log.e("MaintenanceScheduler", "Tried to expire a task that wasn't alerted") ;
        }
    }

    // update the task templates and upcoming tasks, then request an update for all Tasks
    public void SetMilePeriod(int position, int milePeriod, AutoAutoApplication application) {
        TaskTemplate taskTemplate = taskList.get(position);

        if(taskTemplate != null) {
            taskTemplate.setAlertPeriodMiles(milePeriod);

            Task upcoming = findTaskWithName(taskTemplate.getName(), upcomingTasks);
            if (upcoming != null) upcoming.setAlertPeriodMiles(milePeriod);
            else Log.e("MaintenanceScheduler", "Tried to change alert miles for a task that isn't upcoming");
        }
        else {
            Log.e("MaintenanceScheduler", "Tried to change alert miles for an invalid task");
        }

        Collections.sort(taskList);
        Collections.sort(upcomingTasks);
        UpdateTasks(application);
    }

    // move a Task from upcoming to alerted
    public void AlertTask(Task task, AutoAutoApplication application) {
        alertedTasks.add(task);
        upcomingTasks.remove(task);
        application.notifyAlert();

        // re-schedule alerted Task as an inactive upcoming from template
        TaskTemplate template = findTaskTemplateWithName(task.getName(), taskList);
        if(template != null) upcomingTasks.add(new Task(template, currentMiles));
        else Log.e("MaintenanceScheduler", "Tried to re-schedule an invalid task");
    }

    // checks all upcoming tasks and alerts them as necessary
    public void UpdateTasks(AutoAutoApplication application) {
        ArrayList<Task> temp = new ArrayList<Task>();

        for (Task t : upcomingTasks) {
            if(t.isActive()) {
                if (currentMiles >= t.getAlertMileMark()) temp.add(t);
            }
        }
        for (Task t : temp) AlertTask(t, application);
    }

    public void RemoveLoggedTask(int position) {
        LoggedTask task = expiredTasks.get(position);
        if (task != null)
        {
            expiredTasks.remove(task);
        }
        else Log.e("MaintenanceScheduler", "Tried to remove a non-expired task");
    }
}
