package com.autoauto.maintenancetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

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
        builder.setView(R.layout.edit_mile_dialog);
        // add the listener for this
        builder.setPositiveButton("Apply", null);

        AlertDialog modifyDialog = builder.create();
        modifyDialog.show();

        EditText etMiles = modifyDialog.findViewById(R.id.etMiles);
        etMiles.setText("" + task.getAlertPeriodMiles());
    }
}
