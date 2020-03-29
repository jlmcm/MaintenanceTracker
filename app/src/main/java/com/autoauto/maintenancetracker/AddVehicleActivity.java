package com.autoauto.maintenancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autoauto.maintenancetracker.util.Vehicle;

public class AddVehicleActivity extends AutoAutoActivity {
    Button btCreate;
    EditText etMake, etModel, etYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        etMake = findViewById(R.id.etMake);
        etModel = findViewById(R.id.etModel);
        etYear = findViewById(R.id.etYear);

        btCreate = findViewById(R.id.btCreate);
        btCreate.setOnClickListener(clickCreate);
    }

    View.OnClickListener clickCreate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String make = etMake.getText().toString();
            String model = etModel.getText().toString();
            String year = etYear.getText().toString();
            Log.i("onClick", String.format("%s, %s, %s", make, model, year));

            if (!make.equals("") && !model.equals("") && !year.equals("")) {
                application.setVehicle(new Vehicle(make, model, year, application.getMilesTemp()));
                // commented out for debugging
                // application.SaveVehicle();
                finish();
            }
            else {
                if(make.equals("")) etMake.setError("Please specify a make");
                if(model.equals("")) etModel.setError("Please specify a model");
                if(year.equals("")) etYear.setError("Please specify a year");
            }
        }
    };
}
