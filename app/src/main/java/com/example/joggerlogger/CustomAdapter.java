package com.example.joggerlogger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;

    Activity activity;

    private ArrayList statistics_id, statistics_title, statistics_time, statistics_distance, statistics_kmh;

    public CustomAdapter(Activity activity, Context context, ArrayList statistics_id, ArrayList statistics_title, ArrayList statistics_time, ArrayList statistics_distance, ArrayList statistics_kmh) {
        this.activity = activity;
        this.context = context;
        this.statistics_id = statistics_id;
        this.statistics_title = statistics_title;
        this.statistics_time = statistics_time;
        this.statistics_distance = statistics_distance;
        this.statistics_kmh = statistics_kmh;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvId.setText(String.valueOf(statistics_id.get(position)));
        holder.tvTitle.setText(String.valueOf(statistics_title.get(position)));
        holder.tvTime.setText(String.valueOf(statistics_time.get(position)));
        holder.tvDistance.setText(String.valueOf(statistics_distance.get(position)) + "m");
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(statistics_id.get(position)));
                intent.putExtra("title", String.valueOf(statistics_title.get(position)));
                intent.putExtra("time", String.valueOf(statistics_time.get(position)));
                intent.putExtra("distance", String.valueOf(statistics_distance.get(position)));
                intent.putExtra("kmh", String.valueOf(statistics_kmh.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return statistics_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvTitle, tvTime, tvDistance, tvKMH;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            mainLayout = itemView.findViewById(R.id.constrMyRow);
            tvKMH = itemView.findViewById(R.id.statisticsKMH);
        }
    }
}
