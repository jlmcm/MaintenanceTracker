package com.autoauto.maintenancetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.autoauto.maintenancetracker.util.Task;
import com.autoauto.maintenancetracker.util.Vehicle;

import java.util.ArrayList;

public class ViewAlertsActivity extends AutoAutoActivity {
    RecyclerView rvAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alerts);

        rvAlerts = findViewById(R.id.rvAlerts);

        Vehicle vehicle = application.getVehicle();
        ArrayList<Task> alerts = vehicle.getMaintenanceScheduler().getAlertedTasks();

        rvAlerts.setAdapter(new AlertAdapter(this, alerts, vehicle.getMiles()));
        rvAlerts.setLayoutManager(new LinearLayoutManager(this));
    }
}
