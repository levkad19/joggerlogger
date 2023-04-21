package com.example.joggerlogger;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText timer1,timer2,timer3, output;
    int currentTimer =1;
    int value=1;
    CountDownTimer countDownTimer;
    Button startButton,stopButton;
    SeekBar bar;
    int countDownProgress;
    int i=0;
    public int timer;
    int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer1=findViewById(R.id.timer1);
        timer2=findViewById(R.id.timer2);
        timer3=findViewById(R.id.timer3);
        output=findViewById(R.id.outputfield);
        bar=findViewById(R.id.seekBar);
        textView=findViewById(R.id.textView);
        startButton=findViewById(R.id.button);
        stopButton=findViewById(R.id.stop);
        value=bar.getProgress();


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(value);
                countDownProgress=Integer.parseInt(timer1.getText().toString());
                countdown(Integer.parseInt(timer1.getText().toString()));
            }
        });

    }
    private void countdown(int timers){
        timer=timers;
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.peep);
    countDownTimer=new CountDownTimer(timer*1000, 1000) {
        @Override
        public void onTick(long l) {
            timer--;
            output.setText(String.valueOf(timer));
            stopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    countDownTimer.cancel();
                }
            });

        }

        @Override
        public void onFinish() {
            timer=Integer.parseInt(timer2.getText().toString());
            //Countdown.setVisibility(View.INVISIBLE);

                if (currentTimer < 3 && timer != 0) {
                    if(currentTimer==0){
                        timer = Integer.parseInt(timer1.getText().toString());
                        mp.start();
                    }
                    if (currentTimer == 1) {
                        timer = Integer.parseInt(timer2.getText().toString());
                        mp.start();
                    }
                    if (currentTimer == 2) {
                        timer = Integer.parseInt(timer3.getText().toString());
                        mp.start();
                    }
                    countdown(timer+1);
                    i++;
                    currentTimer++;
                }
                else if(counter<value){
                    timer = Integer.parseInt(timer1.getText().toString());
                    currentTimer=1;
                    counter++;
                    countdown(timer+1);
                }
                else {
                    currentTimer = 0;

                    textView.setVisibility(View.INVISIBLE);
                }

        }
    }.start();
    }
}