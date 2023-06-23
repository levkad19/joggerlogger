package com.example.joggerlogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class profileActivity extends AppCompatActivity {

    private ImageButton imgButtonHome;
    private ImageButton imgButtonProfile;

    private RecyclerView recyclerView;
    private Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgButtonHome = (ImageButton) findViewById(R.id.homeButton);
        imgButtonProfile = (ImageButton) findViewById(R.id.profileButton);


        imgButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeActivity();
            }
        });

        imgButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

    }

    private void openHomeActivity(){
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(0, 0);
    }

    private void openProfileActivity(){
        Intent intent = new Intent(this, statisticsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

}