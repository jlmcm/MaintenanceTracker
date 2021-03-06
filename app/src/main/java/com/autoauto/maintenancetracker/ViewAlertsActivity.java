package com.autoauto.maintenancetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.autoauto.maintenancetracker.util.MaintenanceScheduler;
import com.autoauto.maintenancetracker.util.Task;
import com.autoauto.maintenancetracker.util.Vehicle;

import java.util.ArrayList;

public class ViewAlertsActivity extends AutoAutoActivity {
    RecyclerView rvAlerts;
    private String[] dismissReasons = {"Performed", "Skipped"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alerts);

        rvAlerts = findViewById(R.id.rvAlerts);

        Vehicle vehicle = application.getVehicle();
        ArrayList<Task> alerts = vehicle.getMaintenanceScheduler().getAlertedTasks();

        rvAlerts.setAdapter(new AlertAdapter(this, alerts, vehicle.getMiles()));
        rvAlerts.setLayoutManager(new LinearLayoutManager(this));
    }

    public void DismissDialog(final int position) {
        final ViewAlertsActivity context = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dismiss Alert");
        builder.setItems(dismissReasons, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, dismissReasons[which], Toast.LENGTH_SHORT).show();
                context.DismissAlert(position);
            }
        });
        AlertDialog dismissAlert = builder.create();
        dismissAlert.show();
    }

    public void DismissAlert(int position) {
        MaintenanceScheduler maintenanceScheduler = application.getVehicle().getMaintenanceScheduler();
        int alertId = maintenanceScheduler.getAlertedTasks().get(position).getId();
        
        maintenanceScheduler.ExpireTask(alertId);
        rvAlerts.getAdapter().notifyItemRemoved(position);
    }
}
