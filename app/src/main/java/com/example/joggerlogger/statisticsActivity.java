package com.example.joggerlogger;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class statisticsActivity extends AppCompatActivity {

    private ImageButton imgButtonHome;
    private ImageButton imgButtonStatistics;

    RecyclerView recyclerView;

    DatabaseStatistics databaseStatistics;

    ArrayList<String> stats_id, stats_title, stats_time, stats_distance, stats_kmh;

    CustomAdapter customAdapter;

    private ImageButton add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        imgButtonHome = (ImageButton) findViewById(R.id.homeButton);
        imgButtonStatistics = (ImageButton) findViewById(R.id.statisticsButton);

        recyclerView = findViewById(R.id.recyclerView);

        add_button = (ImageButton) findViewById(R.id.add_statistics);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatistics();
            }
        });

        databaseStatistics = new DatabaseStatistics(statisticsActivity.this);
        stats_id = new ArrayList<>();
        stats_title = new ArrayList<>();
        stats_time = new ArrayList<>();
        stats_distance = new ArrayList<>();
        stats_kmh = new ArrayList<>();

        imgButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeActivity();
            }
        });

        imgButtonStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatisticsActivity();
            }
        });

        storeDataInArrays();

        customAdapter = new CustomAdapter(statisticsActivity.this, this, stats_id, stats_title, stats_time, stats_distance, stats_kmh);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(statisticsActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            recreate();
        }

    }

    private void openStatistics(){
        Intent intent = new Intent(this, AddStatistics.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void openHomeActivity(){
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(0, 0);
    }

    private void openStatisticsActivity(){
        Intent intent = new Intent(this, profileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    void storeDataInArrays(){
        Cursor cursor = databaseStatistics.readAllData();
        if(cursor.getCount() == 0){
            //Toast.makeText(this, "No data.", Toast.LENGTH_LONG).show();
        }
        else{
            while(cursor.moveToNext()){
                stats_id.add(cursor.getString(0));
                stats_title.add(cursor.getString(1));
                stats_time.add(cursor.getString(2));
                stats_distance.add(cursor.getString(3));
                stats_kmh.add(cursor.getString(4));
            }
        }
    }

}