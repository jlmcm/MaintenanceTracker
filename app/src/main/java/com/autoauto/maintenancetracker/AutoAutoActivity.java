package com.autoauto.maintenancetracker;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

import com.openxc.VehicleManager;
import com.openxc.measurements.Measurement;
import com.openxc.measurements.Odometer;

/*
 * this will handle all of the service management, so any activity that inherits from this will have
 * access to mVehicle
 */

public class AutoAutoActivity extends AppCompatActivity {
    // !!! I had to import guava:24.1-android under Project Structure for openxc
    protected  AutoAutoApplication application;
    protected VehicleManager mVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (AutoAutoApplication) getApplication();
    }

    @Override
    // when the activity is resumed
    public void onResume() {
        super.onResume();
        if (mVehicle == null) {
            // getApplicationContext() is app-wide
            //Intent managerService = new Intent(getApplicationContext(), VehicleManager.class);
            Intent managerService = new Intent(this, VehicleManager.class);
            bindService(managerService, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    // when the activity is put in the background?
    public void onPause() {
        super.onPause();
        // to "prevent memory leaks"
        if (mVehicle != null) {
            // could log here
            // remove listeners here
            unbindService(mConnection);
            mVehicle = null;
        }
    }

    // defines how to interface with the service
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // could log here
            mVehicle = ((VehicleManager.VehicleBinder) iBinder).getService();
            // add listeners here
            mVehicle.addListener(Odometer.class, mOdometerListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // could log here
            mVehicle = null;
        }
    };

    Odometer.Listener mOdometerListener = new Odometer.Listener() {
        @Override
        public void receive(Measurement measurement) {
            Odometer miles = (Odometer) measurement;
            AutoAutoApplication app = (AutoAutoApplication) getApplication();
            app.getDataLibrary().UpdateMiles(miles.getValue().intValue());
        }
    };
}
