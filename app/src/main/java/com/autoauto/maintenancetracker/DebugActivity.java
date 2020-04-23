package com.autoauto.maintenancetracker;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DebugActivity extends AutoAutoActivity {
    Button btChangeMileage, btResetTasks, btNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        btChangeMileage = findViewById(R.id.btChangeMileage);
        btChangeMileage.setOnClickListener(clickChangeMileage);

        btResetTasks = findViewById(R.id.btResetTasks);
        btResetTasks.setOnClickListener(clickResetTasks);

        btNotification = findViewById(R.id.btNotification);
        btNotification.setOnClickListener(clickNotification);
    }

    View.OnClickListener clickChangeMileage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Context context = DebugActivity.this;

            // create dialog and set up view
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.edit_mile_dialog, null);
            builder.setView(view);
            final AlertDialog mileDialog = builder.create();

            // set up controls
            final EditText etMiles = view.findViewById(R.id.etMiles);
            Button btApply = view.findViewById(R.id.btApply);
            etMiles.setText("" + application.getVehicle().getMiles());

            btApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChangeMileage(etMiles.getText().toString());
                    mileDialog.dismiss();
                }
            });
            mileDialog.show();
        }
    };

    View.OnClickListener clickResetTasks = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            application.getVehicle().getMaintenanceScheduler().ResetTasks();
        }
    };

    public void ChangeMileage(String mileString) {
        try {
            int miles = Integer.parseInt(mileString);
            if(miles <= 0) throw new IllegalArgumentException();

            application.UpdateMiles(miles);
            Toast.makeText(this, "Updated Mileage", Toast.LENGTH_SHORT).show();
        }
        catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid entry", Toast.LENGTH_SHORT).show();
        }
        catch (IllegalArgumentException e) {
            Toast.makeText(this, "Miles must be positive", Toast.LENGTH_SHORT).show();
        }
    }

    View.OnClickListener clickNotification = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = DebugActivity.this;
            Reminder.notifyReminder(context);
        }
    };
}
