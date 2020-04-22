package com.autoauto.maintenancetracker;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.autoauto.maintenancetracker.util.MaintenanceScheduler;
import com.autoauto.maintenancetracker.util.Task;
import com.autoauto.maintenancetracker.util.Vehicle;

import java.util.ArrayList;

// shows current alerts and allows dismissal
public class ViewAlertsActivity extends AutoAutoActivity {
    RecyclerView rvAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alerts);

        rvAlerts = findViewById(R.id.rvAlerts);

        Vehicle vehicle = application.getVehicle();
        ArrayList<Task> alerts = vehicle.getMaintenanceScheduler().getAlertedTasks();

        // fix the negative mile thing
        rvAlerts.setAdapter(new AlertAdapter(this, alerts));
        rvAlerts.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void UpdateMiles(int miles) {
        super.UpdateMiles(miles);
        // fix this
        rvAlerts.getAdapter().notifyDataSetChanged();
    }

    public void DismissDialog(final int position) {
        final ViewAlertsActivity context = this;

        // create dialog and set up view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dismiss_alert_dialog, null);
        builder.setView(view);
        final AlertDialog dismissDialog = builder.create();

        // set up controls
        Button btPerformed = view.findViewById(R.id.btPerformed);
        btPerformed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Performed", Toast.LENGTH_SHORT).show();
                DismissAlert(position, "Performed");
                dismissDialog.dismiss();
            }
        });

        Button btSkipped = view.findViewById(R.id.btSkipped);
        btSkipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Skipped", Toast.LENGTH_SHORT).show();
                DismissAlert(position, "Skipped");
                dismissDialog.dismiss();
            }
        });

        dismissDialog.show();
    }

    public void DismissAlert(int position, String action) {
        MaintenanceScheduler maintenanceScheduler = application.getVehicle().getMaintenanceScheduler();
        
        maintenanceScheduler.ExpireTask(position, action);
        rvAlerts.getAdapter().notifyItemRemoved(position);
    }
}
