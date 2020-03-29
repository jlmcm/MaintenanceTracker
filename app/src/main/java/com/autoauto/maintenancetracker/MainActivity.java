package com.autoauto.maintenancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openxc.VehicleManager;

public class MainActivity extends AutoAutoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        application.LoadVehicle();

        if(application.getVehicle() == null) {
            Intent addVehicle = new Intent(this, AddVehicleActivity.class);
            startActivity(addVehicle);
        }
        else {
            Intent aboutVehicle = new Intent(this, AboutVehicleActivity.class);
            startActivity(aboutVehicle);
        }
    }
}