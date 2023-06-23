package com.example.joggerlogger;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnStart, btnPause, btnStop;
    static boolean status;
    LocationManager locationManager;
    static TextView distance,time;
    static long startTime2, endTime2;
    static ProgressDialog progressDialog;
    static int p=0;
    LocationService myService;
    public Long startTime;
    public Long endTime;

    private int stopVariable = 0;

    private ProgressBar progressBar;
    private TextView progressText;
    private TextView roundsView;
    private int progr = 0;

    private ImageButton imgProfileButton;
    private ImageButton imgStatisticsButton;

    static TextView textView,outputms;
    EditText timer1,timer2,timer3;
    int currentTimer =1;
    int value=1;
    CountDownTimer countDownTimer;
    CountDownTimer countDownTimerProgress;
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
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },1000);
        }

        imgProfileButton = (ImageButton) findViewById(R.id.profileButton);
        imgStatisticsButton = (ImageButton) findViewById(R.id.statisticsButtonSelected);
        progressBar = findViewById(R.id.progress_circle);
        progressText = findViewById(R.id.progress_text);
        roundsView = findViewById(R.id.rounds);
        progressBar.setProgress(100);

        imgProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        imgStatisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatisticsActivity();
            }
        });
        outputms= (TextView) findViewById(R.id.outputms);
        timer1=findViewById(R.id.timer1);
        timer2=findViewById(R.id.timer2);
        timer3=findViewById(R.id.timer3);
        bar=findViewById(R.id.seekBar);
        startButton=findViewById(R.id.button);
        stopButton=findViewById(R.id.stop);
        value=bar.getProgress();


        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = bar.getProgress() + 1;
                roundsView.setText("Rounds: " + value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Called when the user starts interacting with the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Called when the user stops interacting with the SeekBar
            }
        });


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGPS();
                locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    return;
                if(status == false){
                    bindService();

                    progressDialog= new ProgressDialog(MainActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Getting location...");
                    progressDialog.show();
                }
                if(stopVariable == 0){
                    startTime = System.currentTimeMillis();
                }
                System.out.println(value);
                countDownProgress=Integer.parseInt(timer1.getText().toString());
                countdown(Integer.parseInt(timer1.getText().toString()));
            }
        });
    }
    private void checkGPS() {
        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            showGPSDisableAlert();
    }

    private void showGPSDisableAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("ENABLE GPS PLEASE")
                .setCancelable(false)
                .setPositiveButton("EnableGPS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                        startActivity(intent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void bindService() {
        if(status==true){
            return;
        }
        Intent i = new Intent(getApplicationContext(), LocationService.class);
        bindService(i, sc, BIND_AUTO_CREATE);
        status = true;
        startTime=System.currentTimeMillis();
    }


    private void updateLocationUI() {

    }
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LocationService.LocalBinder binder = (LocationService.LocalBinder) iBinder;
            myService=binder.getService();
            status=true;
        }


        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            status =false;
        }
    };
    @Override
    protected void onDestroy(){
        if(status==true)
            unbindService();
        super.onDestroy();
    }

    private void unbindService() {
        if(status==false)
            return;
        Intent i = new Intent(getApplicationContext(), LocationService.class);
        unbindService(sc);
        status=false;
    }
    @Override
    public void onBackPressed(){
        if(status==false){
            super.onBackPressed();
        }else
            moveTaskToBack(true);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(myService, "GRANTED", Toast.LENGTH_SHORT).show();
                else System.out.println("");
                    //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }
    private void countdown(int timers){
        timer = timers;

        System.out.println("THIS IS THE TIMER: " + timer);

        Double multiplier = Double.valueOf(100 / timer);

        System.out.println("THIS IS THE MULTIPLIER: " + multiplier);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.peep);

        countDownTimerProgress = new CountDownTimer(timer*1000, 100) {
            Double timerZeit = Double.valueOf(Integer.parseInt(String.valueOf(timer)));
            @Override
            public void onTick(long millisUntilFinished) {
                timerZeit = timerZeit - 0.1;
                Double progreee = timerZeit * multiplier;
                int progressInt = progreee.intValue();
                progressBar.setProgress(progressInt);
            }

            @Override
            public void onFinish() {
            }

        }.start();

            countDownTimer=new CountDownTimer(timer*1000, 1000) {
            int timerZeit = Integer.parseInt(String.valueOf(timer));

            @Override
            public void onTick(long l) {

                progressText.setText(String.valueOf(timer));

                timer--;
                timerZeit--;

                stopButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        countDownTimer.cancel();
                        stopVariable = 10;
                    }
                });

            }

            @Override
            public void onFinish() {
                timer=Integer.parseInt(timer2.getText().toString());
                progressBar.setProgress(100);

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
                    countdown(timer);
                    i++;
                    currentTimer++;
                }
                else if(counter < value){
                    mp.start();
                    timer = Integer.parseInt(timer1.getText().toString());
                    currentTimer=1;
                    counter++;
                    countdown(timer);
                }
                else {
                    currentTimer = 0;
                    progressText.setText("");

                    endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;
                    long elapsedSeconds = elapsedTime / 1000;

                    saveData(elapsedSeconds);
                }

            }
        }.start();
        System.out.println("FINISHED");
    }

    private void openProfileActivity(){
        Intent intent = new Intent(this, statisticsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void openStatisticsActivity(){
        Intent intent = new Intent(this, profileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void saveData(long elapsedSeconds) {

        LocationService ls = new LocationService();
        List<Double> kmhListe = new ArrayList<>();
        kmhListe = ls.getKmhList();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Statistics?");
        builder.setMessage("Do you want to save your statistics?");

        List<Double> finalKmhListe = kmhListe;

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder titlePromptBuilder = new AlertDialog.Builder(MainActivity.this);
                titlePromptBuilder.setTitle("Enter Title");

                final EditText titleEditText = new EditText(MainActivity.this);
                titleEditText.setHint("Title");
                titlePromptBuilder.setView(titleEditText);

                titlePromptBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = titleEditText.getText().toString();

                        if (!TextUtils.isEmpty(title)) {
                            DatabaseStatistics dbs = new DatabaseStatistics(MainActivity.this);

                            LocationService ls = new LocationService();
                            String time = timeToString(elapsedSeconds);
                            Double distance = ls.getDistance();

                            dbs.addStatistics(title, time, distance.intValue(), finalKmhListe.toString());
                        }
                    }
                });

                titlePromptBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog titlePromptDialog = titlePromptBuilder.create();
                titlePromptDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#460083")));
                titlePromptDialog.show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#460083")));
        dialog.show();
    }


    private String timeToString(long seconds){
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;

        String formattedMinutes = String.format("%02d", minutes);
        String formattedSeconds = String.format("%02d", remainingSeconds);

        return formattedMinutes + ":" + formattedSeconds;
    }

}