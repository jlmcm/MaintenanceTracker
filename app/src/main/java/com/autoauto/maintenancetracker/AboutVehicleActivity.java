package com.autoauto.maintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoauto.maintenancetracker.util.MaintenanceScheduler;
import com.autoauto.maintenancetracker.util.Vehicle;

public class AboutVehicleActivity extends AutoAutoActivity {
    TextView tvMake, tvModel, tvYear;
    Button btAlerts, btMaintenance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vehicle);

        tvMake = findViewById(R.id.tvMake);
        tvModel = findViewById(R.id.tvModel);
        tvYear = findViewById(R.id.tvYear);

        btAlerts = findViewById(R.id.btAlerts);

        btMaintenance = findViewById(R.id.btMaintenance);
        btMaintenance.setOnClickListener(clickMaintenance);
    }

    @Override
    public void onResume() {
        super.onResume();
        Vehicle vehicle = application.getVehicle();
        Log.i("AboutVehicle", "onResume was called");
        Log.i("onResume", String.format("%s, %s, %s", vehicle.getMake(), vehicle.getModel(), vehicle.getYear()));

        tvMake.setText(vehicle.getMake());
        tvModel.setText(vehicle.getModel());
        tvYear.setText(vehicle.getYear());

        int alertCount = vehicle.getMaintenanceScheduler().getAlertedTasks().size();
        if(alertCount == 0) {
            btAlerts.setEnabled(false);
            btAlerts.setText("0 Alerts");
        }
        else {
            btAlerts.setText(alertCount + " Alerts");
        }
    }

    View.OnClickListener clickMaintenance = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent maintenanceSchedule = new Intent(v.getContext(), MaintenanceActivity.class);
            startActivity(maintenanceSchedule);
        }
    };
}
