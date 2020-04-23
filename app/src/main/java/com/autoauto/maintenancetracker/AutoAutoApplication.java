package com.autoauto.maintenancetracker;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.autoauto.maintenancetracker.util.Vehicle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;

// base application
public class AutoAutoApplication extends Application {
    private boolean debugMode = false;
    public boolean isDebugMode() { return debugMode; }
    public void setDebugMode(boolean debugMode) { this.debugMode = debugMode; }

    private Vehicle vehicle;
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) {
        if(this.vehicle != null) {
            Log.e("AutoAutoApplication", "Non-null vehicle was set (overwritten)");
        }
        this.vehicle = vehicle;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            NotificationChannel reminderChannel = new NotificationChannel("reminder", "Reminders", NotificationManager.IMPORTANCE_DEFAULT);
            reminderChannel.setDescription("Mileage update reminders");
            notificationManager.createNotificationChannel(reminderChannel);

            NotificationChannel alertChannel = new NotificationChannel("alert", "Alerts", NotificationManager.IMPORTANCE_DEFAULT);
            alertChannel.setDescription("Maintenance Alerts");
            notificationManager.createNotificationChannel(alertChannel);
        }
    }

    // includes context to send notifications if necessary
    public void UpdateMiles(int miles) {
        if(vehicle != null) {
            vehicle.setMiles(miles, this);
        }
    }

    public void SaveVehicle() {
        if(vehicle != null) {
            try {
                FileOutputStream fOutputStream = openFileOutput("car_data", Context.MODE_PRIVATE);
                ObjectOutputStream oOutputStream = new ObjectOutputStream(fOutputStream);

                oOutputStream.writeObject(vehicle);

                oOutputStream.close();
                fOutputStream.close();
            }
            catch (IOException e) {
                Log.e("IO Error", "Couldn't open car_data for writing");
                Log.e("IO Error", e.toString());
            }
        }
        else {
            Log.e("Save", "Tried to save a null vehicle");
        }
    }

    public void LoadVehicle() {
        try {
            FileInputStream fInputStream = openFileInput("car_data");
            ObjectInputStream oInputStream = new ObjectInputStream(fInputStream);

            vehicle = (Vehicle) oInputStream.readObject();

            fInputStream.close();
            oInputStream.close();
        } catch (IOException e) {
            Log.e("IO Error", "Couldn't open car_data for reading");
        } catch (ClassNotFoundException e) {
            Log.e("IO Error", "Couldn't parse vehicle class from file");
        }
    }

    public void DeleteVehicle() {
        vehicle = null;
        deleteFile("car_data");
    }

    public void notifyAlert() {
        Context context = getApplicationContext();
        Intent viewAlerts = new Intent(this, ViewAlertsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, viewAlerts, 0);

        // backwards-compatibility
        if (Build.VERSION.SDK_INT >= 26) {
            Notification.Builder builder = new Notification.Builder(context, "0");
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setContentTitle("Maintenance Alert");
            builder.setContentText("You have new maintenance alerts");
            builder.setContentIntent(pendingIntent);
            if (Build.VERSION.SDK_INT >= 21)
                builder.setCategory(NotificationCompat.CATEGORY_ALARM);
            Notification notification = builder.build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(0, notification);
        }
        else {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setContentTitle("Maintenance Alert");
            builder.setContentText("You have new maintenance alerts");
            builder.setContentIntent(pendingIntent);
            builder.setPriority(Notification.PRIORITY_DEFAULT);
            Notification notification = builder.build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
        }
    }
}
