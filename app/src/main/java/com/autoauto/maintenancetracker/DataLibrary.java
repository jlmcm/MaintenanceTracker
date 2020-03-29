package com.autoauto.maintenancetracker;

import com.openxc.NoValueException;
import com.openxc.VehicleManager;
import com.openxc.measurements.Odometer;
import com.openxc.measurements.UnrecognizedMeasurementTypeException;

public class DataLibrary {
    private int miles = 0;
    public void UpdateMiles(int miles) {
        if (miles > this.miles) {
            this.miles = miles;
        }
    }

    public DataLibrary()
    {
        // this function intentionally left blank
    }
}
