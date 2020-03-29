package com.autoauto.maintenancetracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// android dev must be some sort of sick practical joke or something wtf
// i   l o v e   t y p i n g   b o i l e r p l a t e   c o d e

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {
    private Context context;
    private String[] cars;

    public CarListAdapter(Context context, String[] cars) {
        this.context = context;
        this.cars = cars;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.car_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvCarName.setText(cars[position]);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAbout = new Intent(context, AboutVehicleActivity.class);
                openAbout.putExtra("car_index", position);
                context.startActivity(openAbout);
            }
        });
    }

    @Override
    public int getItemCount() { return cars.length; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCarName;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCarName = itemView.findViewById(R.id.tvMake);
            parentLayout = itemView.findViewById(R.id.rowLayout);
        }
    }
}
