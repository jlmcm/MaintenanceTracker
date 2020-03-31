package com.autoauto.maintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoauto.maintenancetracker.util.Vehicle;

public class AboutVehicleActivity extends AutoAutoActivity {
    TextView tvMake, tvModel, tvYear, tvMiles;
    Button btAlerts, btMaintenance, btEditVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vehicle);

        tvMake = findViewById(R.id.tvMake);
        tvModel = findViewById(R.id.tvModel);
        tvYear = findViewById(R.id.tvYear);
        tvMiles = findViewById(R.id.tvMiles);

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
        Log.i("AboutVehicle", "onResume was called");
        Log.i("onResume", String.format("%s, %s, %s", vehicle.getMake(), vehicle.getModel(), vehicle.getYear()));

        tvMake.setText(vehicle.getMake());
        tvModel.setText(vehicle.getModel());
        tvYear.setText(vehicle.getYear());
        tvMiles.setText(vehicle.getMiles() + " Miles");

        int alertCount = vehicle.getMaintenanceScheduler().getAlertedTasks().size();
        if(alertCount == 0) {
            btAlerts.setEnabled(false);
            btAlerts.setText("0 Alerts");
        }
        else {
            btAlerts.setText(alertCount + " Alerts");
        }
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
