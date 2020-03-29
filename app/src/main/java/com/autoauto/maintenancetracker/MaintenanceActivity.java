package com.autoauto.maintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoauto.maintenancetracker.util.MaintenanceScheduler;
import com.autoauto.maintenancetracker.util.Task;
import com.autoauto.maintenancetracker.util.Vehicle;

public class MaintenanceActivity extends AutoAutoActivity {
    TextView tvUpcomingPlaceholder;
    Button btAlerts, btLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        btAlerts = findViewById(R.id.btAlerts);
        btAlerts.setOnClickListener(clickAlerts);

        tvUpcomingPlaceholder = findViewById(R.id.tvUpcomingPlaceholder);
        btLog = findViewById(R.id.btLog);
    }

    @Override
    public void onResume() {
        super.onResume();
        Vehicle vehicle = application.getVehicle();
        MaintenanceScheduler scheduler = vehicle.getMaintenanceScheduler();

        int alertCount = vehicle.getMaintenanceScheduler().getAlertedTasks().size();
        if(alertCount == 0) {
            // disabled for debugging
            // btAlerts.setEnabled(false);
            btAlerts.setText("0 Alerts");
        }
        else {
            btAlerts.setText(alertCount + " Alerts");
        }

        // for debugging, can probably look neater or something
        String upcomingTasks = "";
        for (Task t : scheduler.getUpcomingTasks()) {
            int miles = t.getAlertMileMark() - vehicle.getMiles();
            upcomingTasks += t.getName() + " in " + miles + " miles\n";
        }
        tvUpcomingPlaceholder.setText(upcomingTasks);
    }

    View.OnClickListener clickAlerts = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewAlerts = new Intent(v.getContext(), ViewAlertsActivity.class);
            startActivity(viewAlerts);
        }
    };
}
