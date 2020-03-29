package com.autoauto.maintenancetracker;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.autoauto.maintenancetracker.util.DataLibrary;
import com.autoauto.maintenancetracker.util.Vehicle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AutoAutoApplication extends Application {
    // app-wide data can go here
    private DataLibrary dataLibrary = new DataLibrary();
    public DataLibrary getDataLibrary() { return dataLibrary; }

    private Vehicle vehicle;
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) {
        if(this.vehicle != null) {
            Log.w("Application", "Non-null vehicle was set");
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
            } catch (IOException e) {
                Log.e("IO Error", "Couldn't open car_data for writing");
            }
        }
        else {
            Log.w("Save", "Tried to save a null vehicle");
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
}
