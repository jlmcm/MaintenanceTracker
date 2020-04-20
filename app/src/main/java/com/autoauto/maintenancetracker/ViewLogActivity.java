package com.autoauto.maintenancetracker;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.autoauto.maintenancetracker.util.LoggedTask;
import com.autoauto.maintenancetracker.util.Vehicle;

import java.util.ArrayList;

// shows logged Tasks
public class ViewLogActivity extends AutoAutoActivity {
    RecyclerView rvLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log);

        rvLogs = findViewById(R.id.rvLogs);

        Vehicle vehicle = application.getVehicle();
        ArrayList<LoggedTask> logs = vehicle.getMaintenanceScheduler().getExpiredTasks();

        rvLogs.setAdapter(new LogAdapter(this, logs, vehicle.getMiles()));
        rvLogs.setLayoutManager(new LinearLayoutManager(this));
    }
}
