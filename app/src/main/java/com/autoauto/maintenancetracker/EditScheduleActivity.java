package com.autoauto.maintenancetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.autoauto.maintenancetracker.util.TaskTemplate;
import com.autoauto.maintenancetracker.util.Vehicle;

import java.util.ArrayList;

// displays maintenence tasks and allows editing
public class EditScheduleActivity extends AutoAutoActivity {
    RecyclerView rvSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        rvSchedule = findViewById(R.id.rvSchedule);
        Vehicle vehicle = application.getVehicle();
        ArrayList<TaskTemplate> tasks = vehicle.getMaintenanceScheduler().getTaskList();

        rvSchedule.setAdapter(new ScheduleAdapter(this, tasks));
        rvSchedule.setLayoutManager(new LinearLayoutManager(this));
    }

    public void ModifyDialog(final int position) {
        TaskTemplate task = application.getVehicle().getMaintenanceScheduler().getTaskList().get(position);

        // create dialog and set up view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.edit_mile_dialog, null);
        builder.setView(view);
        final AlertDialog modifyDialog = builder.create();

        // set up controls
        final EditText etMiles = view.findViewById(R.id.etMiles);
        Button btApply = view.findViewById(R.id.btApply);
        etMiles.setText("" + task.getAlertPeriodMiles());

        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeSchedule(position, etMiles.getText().toString());
                modifyDialog.dismiss();
            }
        });

        modifyDialog.show();
    }

    public void ChangeSchedule(int position, String mileString) {
        try {
            int milePeriod = Integer.parseInt(mileString);
            if(milePeriod <= 0) throw new IllegalArgumentException();

            application.getVehicle().getMaintenanceScheduler().SetMilePeriod(position, milePeriod, application);
            rvSchedule.getAdapter().notifyDataSetChanged();
            Toast.makeText(this, "Updated settings", Toast.LENGTH_SHORT).show();
        }
        catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid entry", Toast.LENGTH_SHORT).show();
        }
        catch (IllegalArgumentException e) {
            Toast.makeText(this, "Miles must be positive", Toast.LENGTH_SHORT).show();
        }
    }
}
