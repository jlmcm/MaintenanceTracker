package com.autoauto.maintenancetracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autoauto.maintenancetracker.util.Vehicle;

// shown when a vehicle can't be loaded
// handles vehicle creation
public class AddVehicleActivity extends AutoAutoActivity {
    Button btCreate;
    EditText etMake, etModel, etYear, etMiles;
    int miles = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        etMake = findViewById(R.id.etMake);
        etModel = findViewById(R.id.etModel);
        etYear = findViewById(R.id.etYear);
        etMiles = findViewById(R.id.etMiles);

        btCreate = findViewById(R.id.btCreate);
        btCreate.setOnClickListener(clickCreate);
    }

    @Override
    protected void UpdateMiles(int miles) {
        this.miles = miles;
    }

    View.OnClickListener clickCreate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String make = etMake.getText().toString();
            String model = etModel.getText().toString();
            String year = etYear.getText().toString();
            int miles;
            try {
                miles = Integer.parseInt(etMiles.getText().toString());
            }
            catch (Exception e)
            {
                miles = 0;
            }

            if (!make.equals("") && !model.equals("") && !year.equals("") && miles >= 0) {
                application.setVehicle(new Vehicle(make, model, year, miles));
                application.SaveVehicle();
                finish();
            }
            else {
                if(make.equals("")) etMake.setError("Please specify a make");
                if(model.equals("")) etModel.setError("Please specify a model");
                if(year.equals("")) etYear.setError("Please specify a year");
                if(miles < 0) etMiles.setError("Miles must be positive");
            }
        }
    };
}
