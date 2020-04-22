package com.autoauto.maintenancetracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.autoauto.maintenancetracker.util.Vehicle;
import com.openxc.VehicleManager;
import com.openxc.measurements.Measurement;
import com.openxc.measurements.Odometer;

import java.util.Timer;
import java.util.TimerTask;

// base Activity for the app
// handles service management
public class AutoAutoActivity extends AppCompatActivity {
    // !!! I had to import guava:24.1-android under Project Structure for openxc to work
    protected AutoAutoApplication application;
    protected VehicleManager mVehicleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (AutoAutoApplication) getApplication();
    }

    @Override
    public void onResume() {
        super.onResume();
        application.LoadVehicle();
        if (mVehicleManager == null) {
            Intent managerService = new Intent(this, VehicleManager.class);
            bindService(managerService, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // to "prevent memory leaks"
        if (mVehicleManager != null) {
            // remove listeners here
            unbindService(mConnection);
            mVehicleManager = null;
        }
        application.SaveVehicle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // same as above
        if (mVehicleManager != null) {
            // remove listeners here
            unbindService(mConnection);
            mVehicleManager = null;
        }
        application.SaveVehicle();
    }

    // defines how to interface with the service
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mVehicleManager = ((VehicleManager.VehicleBinder) iBinder).getService();
            // add listeners here
            mVehicleManager.addListener(Odometer.class, mOdometerListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mVehicleManager = null;
        }
    };

    Odometer.Listener mOdometerListener = new Odometer.Listener() {
        @Override
        public void receive(Measurement measurement) {
            Odometer miles = (Odometer) measurement;
            UpdateMiles(miles.getValue().intValue());
        }
    };

    // this is overwritten by children (but still called)
    protected void UpdateMiles(int miles) {
        Vehicle vehicle = application.getVehicle();
        if(vehicle != null)
        {
            if(vehicle.getMiles() <= miles) application.UpdateMiles(miles);
            else Log.e("AutoAutoActivity", "Miles reported are less than expected");
        }
    }
}
