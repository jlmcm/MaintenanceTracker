package com.autoauto.maintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoauto.maintenancetracker.util.Vehicle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

// displays vehicle information
public class AboutVehicleActivity extends AutoAutoActivity {
    TextView tvMake, tvModel, tvYear, tvMiles, tvLastUpdated;
    Button btAlerts, btMaintenance, btEditVehicle;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a 'on' EEE, MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vehicle);

        tvMake = findViewById(R.id.tvMake);
        tvModel = findViewById(R.id.tvModel);
        tvYear = findViewById(R.id.tvYear);
        tvMiles = findViewById(R.id.tvMiles);
        tvLastUpdated = findViewById(R.id.tvLastUpdated);

        btAlerts = findViewById(R.id.btAlerts);
        btAlerts.setOnClickListener(clickAlerts);

        btMaintenance = findViewById(R.id.btMaintenance);
        btMaintenance.setOnClickListener(clickMaintenance);

        btEditVehicle = findViewById(R.id.btEditVehicle);
        btEditVehicle.setOnClickListener(clickEditVehicle);
    }

    @Override
    public void onResume() {
        super.onResume();
        Vehicle vehicle = application.getVehicle();

        tvMake.setText(vehicle.getMake());
        tvModel.setText(vehicle.getModel());
        tvYear.setText(vehicle.getYear());
        tvMiles.setText(vehicle.getMiles() + " Miles");

        Date lastUpdate = vehicle.getLastUpdate();
        tvLastUpdated.setText("Last updated: " + dateFormatter.format(lastUpdate));

        int alertCount = vehicle.getMaintenanceScheduler().getAlertedTasks().size();
        btAlerts.setText(alertCount + " Alerts");
    }

    @Override
    protected void UpdateMiles(int miles) {
        super.UpdateMiles(miles);
        Vehicle vehicle = application.getVehicle();

        tvMiles.setText(vehicle.getMiles() + " Miles");

        Date lastUpdate = vehicle.getLastUpdate();
        tvLastUpdated.setText("Last updated: " + dateFormatter.format(lastUpdate));

        int alertCount = vehicle.getMaintenanceScheduler().getAlertedTasks().size();
        btAlerts.setText(alertCount + " Alerts");
    }

    View.OnClickListener clickAlerts = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent viewAlerts = new Intent(v.getContext(), ViewAlertsActivity.class);
            startActivity(viewAlerts);
        }
    };

    View.OnClickListener clickMaintenance = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent maintenance = new Intent(v.getContext(), MaintenanceActivity.class);
            startActivity(maintenance);
        }
    };

    View.OnClickListener clickEditVehicle = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent editVehicle = new Intent(v.getContext(), EditVehicleActivity.class);
            startActivity(editVehicle);
        }
    };
}
