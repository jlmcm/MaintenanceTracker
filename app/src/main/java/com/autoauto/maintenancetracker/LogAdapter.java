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

import java.util.ArrayList;

// android dev must be some sort of sick practical joke or something wtf
// i   l o v e   t y p i n g   b o i l e r p l a t e   c o d e
// android development makes me wanna lolli-pop myself

// basically a modified AlertAdapter

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Task> logs;
    private int currentMiles;

    public LogAdapter(Context context, ArrayList<Task> logs, int miles) {
        this.context = context;
        this.logs = logs;
        this.currentMiles = miles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.log_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvName.setText(logs.get(position).getName());
        holder.tvMilesAgo.setText(currentMiles - logs.get(position).getAlertMileMark() + " Miles Ago");
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
