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
    Button btMaintenance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vehicle);

        tvMake = findViewById(R.id.tvMake);
        tvModel = findViewById(R.id.tvModel);
        tvYear = findViewById(R.id.tvYear);

        btMaintenance = findViewById(R.id.btMaintenance);
        btMaintenance.setOnClickListener(clickMaintenance);
    }

    @Override
    public void onResume() {
        super.onResume();
        Vehicle vehicle = application.getVehicle();
        Log.i("onResume", String.format("%s, %s, %s", vehicle.getMake(), vehicle.getModel(), vehicle.getYear()));

        tvMake.setText(vehicle.getMake());
        tvModel.setText(vehicle.getModel());
        tvYear.setText(vehicle.getYear());
    }

    View.OnClickListener clickMaintenance = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent maintenanceSchedule = new Intent(v.getContext(), MaintenanceActivity.class);
            startActivity(maintenanceSchedule);
        }
    };
}
