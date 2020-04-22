package com.autoauto.maintenancetracker.util;

import android.content.Context;
import android.util.Log;

import com.autoauto.maintenancetracker.AutoAutoApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
        taskList.add(new TaskTemplate("oil", 30));
        taskList.add(new TaskTemplate("filter", 40));
        taskList.add(new TaskTemplate("test3", 70));
        taskList.add(new TaskTemplate("test1", 50));
        taskList.add(new TaskTemplate("test2", 60));

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
