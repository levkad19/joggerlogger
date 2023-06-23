package com.example.joggerlogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AddStatistics extends AppCompatActivity {

    EditText title_input;
    EditText time_input;
    EditText distance_input;

    EditText kmh_input;

    Button addButton;

    ImageButton statisticsButton;
    ImageButton homeButton;
    ImageButton profileButton;

    ImageButton goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statistics);

        title_input = findViewById(R.id.statisticsTitle);
        time_input = findViewById(R.id.statisticsTime);
        distance_input = findViewById(R.id.statisticsDistance);
        addButton = findViewById(R.id.add_button);
        kmh_input = findViewById(R.id.statisticsKMH);


        statisticsButton = findViewById(R.id.profileButton2);
        homeButton = findViewById(R.id.homeButton2);
        profileButton = findViewById(R.id.statisticsButton2);

        goBack = (ImageButton) findViewById(R.id.goBack2);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatistics();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeActivity();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseStatistics db = new DatabaseStatistics(AddStatistics.this);
                db.addStatistics(title_input.getText().toString().trim(),
                        time_input.getText().toString().trim(),
                        Integer.valueOf(distance_input.getText().toString().trim()),
                        kmh_input.getText().toString().trim());

                goBack();

            }
        });
    }
    private void goBack(){
        Intent intent = new Intent(this, statisticsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void openStatistics(){
        goBack();
    }

    private void openHomeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void openProfileActivity(){
        Intent intent = new Intent(this, profileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

}