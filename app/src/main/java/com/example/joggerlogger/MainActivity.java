package com.example.joggerlogger;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView Countdown, textView;

    CountDownTimer countDownTimer;
    int countDownProgress = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Countdown=findViewById(R.id.textCount);
        textView=findViewById(R.id.textView);
        countdown();

    }
    private void countdown(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.peep);
    countDownTimer=new CountDownTimer(5000, 1000) {
        @SuppressLint("ResourceType")
        @Override
        public void onTick(long l) {
            countDownProgress--;
            Countdown.setText(String.valueOf(countDownProgress));
        }

        @Override
        public void onFinish() {
            mp.start();
            textView.setVisibility(View.INVISIBLE);
            //Countdown.setVisibility(View.INVISIBLE);

        }
    }.start();
    }
}