package com.autoauto.maintenancetracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoauto.maintenancetracker.util.Task;

import java.util.ArrayList;

// android dev must be some sort of sick practical joke or something wtf
// i   l o v e   t y p i n g   b o i l e r p l a t e   c o d e

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder> {
    private Context context;
    private Task[] alerts;
    private int currentMiles;

    public AlertAdapter(Context context, ArrayList<Task> alerts, int miles) {
        this.context = context;
        this.alerts = alerts.toArray(new Task[0]);
        this.currentMiles = miles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvName.setText(alerts[position].getName());
        holder.tvMilesAgo.setText(currentMiles - alerts[position].getAlertMileMark() + " Miles Ago");

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Alert", "Alert " + position + " was clicked");
            }
        });
    }

    @Override
    public int getItemCount() { return alerts.length; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMilesAgo;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvMilesAgo = itemView.findViewById(R.id.tvMilesAgo);
            parentLayout = itemView.findViewById(R.id.rowLayout);
        }
    }
}
