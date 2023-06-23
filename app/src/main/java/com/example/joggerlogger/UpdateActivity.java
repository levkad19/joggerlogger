package com.example.joggerlogger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

public class UpdateActivity extends AppCompatActivity {

    String id, title, time, distance, kmh;

    TextView tvAvgSpeed, tvMaxSpeed;

    XYPlot plot;

    ImageButton statisticsButton;
    ImageButton homeButton;
    ImageButton profileButton;

    EditText title_input, time_input, distance_input, kmh_input;

    Button updateButton, deleteButton;

    ImageButton goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        statisticsButton = findViewById(R.id.profileButton3);
        homeButton = findViewById(R.id.homeButton3);
        profileButton = findViewById(R.id.statisticsButton3);

        title_input = findViewById(R.id.statisticsTitle2);
        time_input = findViewById(R.id.statisticsTime2);
        distance_input = findViewById(R.id.statisticsDistance2);
        kmh_input = findViewById(R.id.statisticsKMH2);
        plot = findViewById(R.id.plot);
        tvAvgSpeed = findViewById(R.id.tvAvgSpeed);
        tvMaxSpeed = findViewById(R.id.tvMaxSpeed);

        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);

        goBack = (ImageButton) findViewById(R.id.goBack);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        getAndSetIntentData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseStatistics myDB = new DatabaseStatistics(UpdateActivity.this);

                title = String.valueOf(title_input.getText());
                time = String.valueOf(time_input.getText());
                distance = String.valueOf(distance_input.getText());
                kmh = String.valueOf(kmh_input.getText());

                myDB.updateData(id, title, time, distance, kmh);

                goBack();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
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

        String kmhListString = String.valueOf(kmh_input.getText());

        if(!kmhListString.isEmpty()) {

            String trimmedInput = kmhListString.replace("[", "");
            trimmedInput = trimmedInput.replace("]", "");

            System.out.println("THIS ONE: " + kmhListString);

            String[] stringArray = trimmedInput.split(",");

            Double[] kmhListDouble = new Double[stringArray.length];

            for (int i = 0; i < stringArray.length; i++) {
                kmhListDouble[i] = Double.parseDouble(stringArray[i]);
            }

            for (int i = 0; i < kmhListDouble.length; i++) {
                kmhListDouble[i] = Math.round(kmhListDouble[i] * 10.0) / 10.0;
            }

            final Number[] domainLabels = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250};
            Number[] seriesNumbers = kmhListDouble;

            XYSeries series1 = new SimpleXYSeries(Arrays.asList(seriesNumbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");

            LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.rgb(160, 32, 240), Color.GREEN, null, null);

            series1Format.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

            plot.addSeries(series1, series1Format);

            plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
                @Override
                public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                    int i = Math.round(((Number) obj).floatValue());
                    return toAppendTo.append(domainLabels[i]);
                }

                @Override
                public Object parseObject(String s, ParsePosition parsePosition) {
                    return null;
                }
            });


            PanZoom.attach(plot);

            Double avgSpeed = 0.0;
            Double sumValues = 0.0;
            int numValues = kmhListDouble.length;
            Double maxSpeed = 0.0;

            for(int i = 0; i < kmhListDouble.length; i++){
                sumValues += kmhListDouble[i];

                if(kmhListDouble[i] > maxSpeed){
                    maxSpeed = kmhListDouble[i];
                }
            }

            avgSpeed = sumValues / numValues;

            avgSpeed = Math.round(avgSpeed * 10.0) / 10.0;

            tvAvgSpeed.setText("Average Speed: " + avgSpeed + " km/h");
            tvMaxSpeed.setText("Maximum Speed: " + maxSpeed + " km/h");

        }

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

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("time") && getIntent().hasExtra("distance") && getIntent().hasExtra("kmh")){

            //Get the Data from the Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            time = getIntent().getStringExtra("time");
            distance = getIntent().getStringExtra("distance");
            kmh = getIntent().getStringExtra("kmh");

            //Setting Intent Data

            title_input.setText(title);
            time_input.setText(time);
            distance_input.setText(distance);
            kmh_input.setText(kmh);

        }
        else{
            //Toast.makeText(this, "No data!", Toast.LENGTH_LONG).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");

        // Set positive button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseStatistics dbs = new DatabaseStatistics(UpdateActivity.this);
                dbs.deleteOneRow(id);
                goBack();
            }
        });

        // Set negative button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Create the dialog
        AlertDialog dialog = builder.create();

        // Set the background color of the dialog window to purple
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#460083")));

        // Show the dialog
        dialog.show();
    }

}