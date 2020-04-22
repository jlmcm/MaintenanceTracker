package com.autoauto.maintenancetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoauto.maintenancetracker.util.LoggedTask;

import java.util.ArrayList;

// Adapter for logged tasks
// tied to ViewLogActivity
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
    private ViewLogActivity context;
    private ArrayList<LoggedTask> logs;
    private int currentMiles;

    public LogAdapter(ViewLogActivity context, ArrayList<LoggedTask> logs, int miles) {
        this.context = context;
        this.logs = logs;
        this.currentMiles = miles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.log_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DeleteDialog(viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        LoggedTask task = logs.get(position);
        holder.tvName.setText(task.getName());
        holder.tvMilesAgo.setText(task.getActionTaken() + " " + (currentMiles - task.getMilesLoggedAt()) + " Miles Ago");
    }

    @Override
    public int getItemCount() { return logs.size(); }

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
