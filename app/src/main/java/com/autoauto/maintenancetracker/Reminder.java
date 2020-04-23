package com.autoauto.maintenancetracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Reminder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        notifyReminder(context);
    }

    public static void notifyReminder(Context context) {
        // backwards-compatibility
        if (Build.VERSION.SDK_INT >= 26) {
            Notification.Builder builder = new Notification.Builder(context, "0");
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setContentTitle("Mileage Reminder");
            builder.setContentText("Mileage hasn't been updated in a while");
            if (Build.VERSION.SDK_INT >= 21)
                builder.setCategory(NotificationCompat.CATEGORY_REMINDER);
            Notification notification = builder.build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(0, notification);
        }
        else {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setContentTitle("Mileage Reminder");
            builder.setContentText("Mileage hasn't been updated in a while");
            builder.setPriority(Notification.PRIORITY_DEFAULT);
            Notification notification = builder.build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
        }
    }
}
