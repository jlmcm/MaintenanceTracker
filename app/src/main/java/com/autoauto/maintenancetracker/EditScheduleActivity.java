package com.autoauto.maintenancetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.autoauto.maintenancetracker.util.MaintenanceScheduler;
import com.autoauto.maintenancetracker.util.TaskTemplate;
import com.autoauto.maintenancetracker.util.Vehicle;

import java.util.ArrayList;
import java.util.zip.Inflater;

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

    // adapted from from ViewAlertsActivity
    public void ModifyDialog(final int position) {
        final EditScheduleActivity context = this;

        TaskTemplate task = application.getVehicle().getMaintenanceScheduler().getTaskList().get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Mile Period");

        // all this for a view
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.edit_mile_dialog, null);
        final EditText etMiles = view.findViewById(R.id.etMiles);
        etMiles.setText("" + task.getAlertPeriodMiles());

        builder.setView(view);

        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.ChangeSchedule(position, etMiles.getText().toString());
            }
        });

        AlertDialog modifyDialog = builder.create();
        modifyDialog.show();
    }

    public void ChangeSchedule(int position, String mileString) {
        try {
            int milePeriod = Integer.parseInt(mileString);
            application.getVehicle().getMaintenanceScheduler().SetMilePeriod(position, milePeriod);
            Toast.makeText(this, "Updated Settings", Toast.LENGTH_SHORT).show();
            rvSchedule.getAdapter().notifyItemChanged(position);
        }
        catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid entry", Toast.LENGTH_SHORT).show();
        }
    }
}
