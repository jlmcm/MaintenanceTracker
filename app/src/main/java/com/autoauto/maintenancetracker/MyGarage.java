package com.autoauto.maintenancetracker;

import java.util.ArrayList;

public class MyGarage {
    private ArrayList<String> cars;
    public ArrayList<String> getCars() { return cars; }
    public String[] getCarsArray() { return cars.toArray(new String[0]); }

    public MyGarage() {
        cars = new ArrayList<String>();
        cars.add("H");
        cars.add("I");
        cars.add("J");
    }
}
