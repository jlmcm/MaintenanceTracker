package com.autoauto.maintenancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autoauto.maintenancetracker.util.Vehicle;

public class EditVehicleActivity extends AutoAutoActivity {
    Button btSave, btCancel;
    EditText etMake, etModel, etYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        Vehicle vehicle = application.getVehicle();

        etMake = findViewById(R.id.etMake);
        etMake.setText(vehicle.getMake());

        etModel = findViewById(R.id.etModel);
        etModel.setText(vehicle.getModel());

        etYear = findViewById(R.id.etYear);
        etYear.setText(vehicle.getYear());

        btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(clickSave);

        btCancel = findViewById(R.id.btCancel);
        btCancel.setOnClickListener(clickCancel);
    }

    // yes this is nearly identical to the one in AddVehicle
    // no I don't wanna figure out a way to reuse it
    View.OnClickListener clickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String make = etMake.getText().toString();
            String model = etModel.getText().toString();
            String year = etYear.getText().toString();
            Log.i("onClick", String.format("%s, %s, %s", make, model, year));

            if (!make.equals("") && !model.equals("") && !year.equals("")) {
                application.getVehicle().setInfo(make, model, year);
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

    View.OnClickListener clickCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
