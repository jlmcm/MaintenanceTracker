package com.autoauto.maintenancetracker;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
 * this class is not meant to be used, it's just for reference
 */

public class RecyclerViewActivity extends AutoAutoActivity {
    // declare controls here
    RecyclerView rvCars;
/*
    @Override
    // when the activity is first opened
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set references to controls here
        rvCars = findViewById(R.id.rvCars);
        // apparently passing String[0] in is common
        String[] carArray = (String[]) application.getMyGarage().getCars().toArray(new String[0]);
        CarListAdapter carListAdapter = new CarListAdapter(this, carArray);
        rvCars.setAdapter(carListAdapter);
        rvCars.setLayoutManager(new LinearLayoutManager(this));
    }
 */
}