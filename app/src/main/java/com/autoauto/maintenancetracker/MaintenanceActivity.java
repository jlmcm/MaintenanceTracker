package com.autoauto.maintenancetracker;

import android.os.Bundle;
import android.widget.TextView;

import com.autoauto.maintenancetracker.util.MaintenanceScheduler;

public class MaintenanceActivity extends AutoAutoActivity {
    TextView tvPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        tvPlaceholder = findViewById(R.id.tvPlaceholder);
    }

    @Override
    public void onResume() {
        super.onResume();
        MaintenanceScheduler scheduler = application.getVehicle().getMaintenanceScheduler();
        tvPlaceholder.setText("Oil every " + scheduler.getOilMiles() + " miles");
    }
}
