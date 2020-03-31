package com.autoauto.maintenancetracker;

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
    private ViewAlertsActivity context;
    private ArrayList<Task> alerts;
    private int currentMiles;

    public AlertAdapter(ViewAlertsActivity context, ArrayList<Task> alerts, int miles) {
        this.context = context;
        this.alerts = alerts;
        this.currentMiles = miles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DismissDialog(viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvName.setText(alerts.get(position).getName());
        holder.tvMilesAgo.setText(currentMiles - alerts.get(position).getAlertMileMark() + " Miles Ago");
    }

    @Override
    public int getItemCount() { return alerts.size(); }

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
