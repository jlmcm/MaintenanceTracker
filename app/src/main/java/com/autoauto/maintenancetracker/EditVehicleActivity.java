package com.autoauto.maintenancetracker;

import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.autoauto.maintenancetracker.util.Vehicle;

public class EditVehicleActivity extends AutoAutoActivity {
    int pressCounter = 0;
    TextView tvMake;
    Button btSave, btCancel, btDelete, btDebug;
    EditText etMake, etModel, etYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        Vehicle vehicle = application.getVehicle();

        tvMake = findViewById(R.id.tvMake);
        tvMake.setOnClickListener(clickMake);

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

        btDelete = findViewById(R.id.btDelete);
        btDelete.setOnClickListener(clickDelete);

        btDebug = findViewById(R.id.btDebug);
        btDebug.setOnClickListener(clickDebug);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (application.isDebugMode()) btDebug.setVisibility(View.VISIBLE);
        else btDebug.setVisibility(View.GONE);
    }

    // nearly identical to the one in AddVehicle
    View.OnClickListener clickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String make = etMake.getText().toString();
            String model = etModel.getText().toString();
            String year = etYear.getText().toString();

            if (!make.equals("") && !model.equals("") && !year.equals("")) {
                application.getVehicle().setInfo(make, model, year);
                application.SaveVehicle();
                finish();
            }
            else {
                if(make.equals("")) etMake.setError("Please specify a make");
                if(model.equals("")) etModel.setError("Please specify a model");
                if(year.equals("")) etYear.setError("Please specify a year");
            }
        }
    };

    View.OnClickListener clickMake = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pressCounter++;
            if (pressCounter >= 10) {
                Toast.makeText(EditVehicleActivity.this, "Debug Mode Activated", Toast.LENGTH_SHORT).show();
                application.setDebugMode(true);
                btDebug.setVisibility(View.VISIBLE);
            }
        }
    };

    View.OnClickListener clickCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    View.OnClickListener clickDelete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditVehicleActivity context = EditVehicleActivity.this;

            // create dialog and set up view
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.delete_car_dialog, null);
            builder.setView(view);
            final AlertDialog deleteAlert = builder.create();

            // set up controls
            Button btDelete = view.findViewById(R.id.btDelete);
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteAlert.dismiss();
                    application.DeleteVehicle();
                    finishAffinity();
                }
            });

            Button btCancel = view.findViewById(R.id.btCancel);
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteAlert.dismiss();
                }
            });

            deleteAlert.show();
        }
    };

    View.OnClickListener clickDebug = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent debugMenu = new Intent(v.getContext(), DebugActivity.class);
            startActivity(debugMenu);
        }
    };
}
