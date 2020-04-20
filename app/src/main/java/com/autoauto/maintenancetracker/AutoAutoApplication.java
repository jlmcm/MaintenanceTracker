package com.autoauto.maintenancetracker;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.autoauto.maintenancetracker.util.Vehicle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// base application
public class AutoAutoApplication extends Application {
    private Vehicle vehicle;
    public Vehicle getVehicle() { return vehicle; }

    public void UpdateMiles(int miles) {
        if(vehicle != null) {
            vehicle.setMiles(miles);
            vehicle.setLastUpdate();
        }
    }

    public void setVehicle(Vehicle vehicle) {
        if(this.vehicle != null) {
            Log.e("AutoAutoApplication", "Non-null vehicle was set (overwritten)");
        }
        this.vehicle = vehicle;
    }

    public void SaveVehicle() {
        if(vehicle != null) {
            try {
                FileOutputStream fOutputStream = openFileOutput("car_data", Context.MODE_PRIVATE);
                ObjectOutputStream oOutputStream = new ObjectOutputStream(fOutputStream);
                oOutputStream.writeObject(vehicle);
                oOutputStream.close();
                fOutputStream.close();
            }
            catch (IOException e) {
                Log.e("IO Error", "Couldn't open car_data for writing");
                Log.e("IO Error", e.toString());
            }
        }
        else {
            Log.e("Save", "Tried to save a null vehicle");
        }
    }

    public void LoadVehicle() {
        try {
            FileInputStream fInputStream = openFileInput("car_data");
            ObjectInputStream oInputStream = new ObjectInputStream(fInputStream);
            vehicle = (Vehicle) oInputStream.readObject();
            fInputStream.close();
            oInputStream.close();
        } catch (IOException e) {
            Log.e("IO Error", "Couldn't open car_data for reading");
        } catch (ClassNotFoundException e) {
            Log.e("IO Error", "Couldn't parse vehicle class from file");
        }
    }

    public void DeleteVehicle() {
        vehicle = null;
        deleteFile("car_data");
    }
}
