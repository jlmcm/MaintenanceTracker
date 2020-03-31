package com.autoauto.maintenancetracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoauto.maintenancetracker.util.Task;
import com.autoauto.maintenancetracker.util.TaskTemplate;

import java.util.ArrayList;

// android dev must be some sort of sick practical joke or something wtf
// i   l o v e   t y p i n g   b o i l e r p l a t e   c o d e

// basically a modified AlertAdapter

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private EditScheduleActivity context;
    private ArrayList<TaskTemplate> tasks;

    public ScheduleAdapter(EditScheduleActivity context, ArrayList<TaskTemplate> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.schedule_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.ModifyDialog(viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvName.setText(tasks.get(position).getName());
        holder.tvMilePeriod.setText("Every " + tasks.get(position).getAlertPeriodMiles() + " Miles");
    }

    @Override
    public int getItemCount() { return tasks.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMilePeriod;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvMilePeriod = itemView.findViewById(R.id.tvMilePeriod);
            parentLayout = itemView.findViewById(R.id.rowLayout);
        }
    }
}
