package com.example.e14_statoactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView timeAndDateTextView;
    TextView counterTextView;
    Button incrementerButton;
    int counter = 0;
    Date dateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeAndDateTextView = findViewById(R.id.timeAndDateTextView);
        counterTextView = findViewById(R.id.counterTextView);
        incrementerButton = findViewById(R.id.incrementorButton);

        dateAndTime = Calendar.getInstance().getTime();

        updateCounter(counter);
        updateTime(dateAndTime);

        incrementerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                updateCounter(counter);
            }
        });

    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("clicks", counter);
        outState.putSerializable("dateAndTime", dateAndTime);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        counter = savedInstanceState.getInt("clicks");
        dateAndTime = (Date) savedInstanceState.getSerializable("dateAndTime");

        updateCounter(counter);
        updateTime(dateAndTime);
    }

    private void updateCounter(int n) {
        counterTextView.setText("Clicks: " + n);
    }

    private void updateTime(Date dateAndTime) {

        timeAndDateTextView.setText("" + dateAndTime);
    }
}