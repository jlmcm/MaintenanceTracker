package com.autoauto.maintenancetracker;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.autoauto.maintenancetracker.util.LoggedTask;
import com.autoauto.maintenancetracker.util.MaintenanceScheduler;
import com.autoauto.maintenancetracker.util.Vehicle;

import java.util.ArrayList;

// shows logged Tasks


// add dialog to remove past alerts
public class ViewLogActivity extends AutoAutoActivity {
    RecyclerView rvLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log);

        rvLogs = findViewById(R.id.rvLogs);

        Vehicle vehicle = application.getVehicle();
        ArrayList<LoggedTask> logs = vehicle.getMaintenanceScheduler().getExpiredTasks();

        rvLogs.setAdapter(new LogAdapter(this, logs, vehicle.getMiles()));
        rvLogs.setLayoutManager(new LinearLayoutManager(this));
    }

    public void DeleteDialog(final int position) {
        final Context context = this;

        // create dialog and set up view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.delete_log_dialog, null);
        builder.setView(view);
        final AlertDialog deleteDialog = builder.create();

        // set up controls
        Button btDelete = view.findViewById(R.id.btDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                DeleteLoggedTask(position);
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();
    }

    public void DeleteLoggedTask(int position) {
        MaintenanceScheduler maintenanceScheduler = application.getVehicle().getMaintenanceScheduler();
        maintenanceScheduler.RemoveLoggedTask(position);
        rvLogs.getAdapter().notifyItemRemoved(position);
    }
}
