package com.autoauto.maintenancetracker;

import android.app.Application;

public class AutoAutoApplication extends Application {
    // app-wide data can go here
    private DataLibrary dataLibrary = new DataLibrary();
    public DataLibrary getDataLibrary() {
        return dataLibrary;
    }

    private MyGarage myGarage = new MyGarage();
    public MyGarage getMyGarage() { return myGarage; }
}
