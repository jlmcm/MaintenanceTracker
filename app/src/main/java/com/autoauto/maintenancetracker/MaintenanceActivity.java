package com.autoauto.maintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoauto.maintenancetracker.util.MaintenanceScheduler;
import com.autoauto.maintenancetracker.util.Task;
import com.autoauto.maintenancetracker.util.Vehicle;

import java.util.ArrayList;

// displays task information
public class MaintenanceActivity extends AutoAutoActivity {
    TextView tvUpcomingPlaceholder;
    Button btAlerts, btLog, btEditSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        btAlerts = findViewById(R.id.btAlerts);
        btAlerts.setOnClickListener(clickAlerts);

        tvUpcomingPlaceholder = findViewById(R.id.tvUpcomingPlaceholder);

        btLog = findViewById(R.id.btLog);
        btLog.setOnClickListener(clickLog);

        btEditSchedule = findViewById(R.id.btEditSchedule);
        btEditSchedule.setOnClickListener(clickEditSchedule);
    }

    @Override
    public void onResume() {
        super.onResume();

        int alertCount = application.getVehicle().getMaintenanceScheduler().getAlertedTasks().size();
        btAlerts.setText(alertCount + " Alerts");

        tvUpcomingPlaceholder.setText(listUpcomingTasks());
    }

    @Override
    protected void UpdateMiles(int miles) {
        super.UpdateMiles(miles);

        int alertCount = application.getVehicle().getMaintenanceScheduler().getAlertedTasks().size();
        btAlerts.setText(alertCount + " Alerts");

        tvUpcomingPlaceholder.setText(listUpcomingTasks());
    }

    private String listUpcomingTasks() {
        Vehicle vehicle = application.getVehicle();
        MaintenanceScheduler scheduler = vehicle.getMaintenanceScheduler();
        String upcomingTasks = "";

        // technically inefficient but w/e
        for (Task t : scheduler.getUpcomingTasks()) {
            if(!t.isActive()) {
                int miles = vehicle.getMiles() - t.getCreatedMiles();
                upcomingTasks += t.getName() + "  " + miles + " miles ago\n";
            }
        }
        for (Task t : scheduler.getUpcomingTasks()) {
            if(t.isActive()) {
                int miles = t.getAlertMileMark() - vehicle.getMiles();
                upcomingTasks += t.getName() + " in " + miles + " miles\n";
            }
        }
        return upcomingTasks;
    }

    View.OnClickListener clickAlerts = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewAlerts = new Intent(v.getContext(), ViewAlertsActivity.class);
            startActivity(viewAlerts);
        }
    };

    View.OnClickListener clickLog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewLog = new Intent(v.getContext(), ViewLogActivity.class);
            startActivity(viewLog);
        }
    };

    View.OnClickListener clickEditSchedule = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent editSchedule = new Intent(v.getContext(), EditScheduleActivity.class);
            startActivity(editSchedule);
        }
    };
}
