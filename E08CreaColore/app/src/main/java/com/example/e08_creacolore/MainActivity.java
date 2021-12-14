package com.example.e08_creacolore;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView value1Text;
    TextView value2Text;
    TextView value3Text;

    TextView value1;
    TextView value2;
    TextView value3;

    SeekBar seekBar1;
    SeekBar seekBar2;
    SeekBar seekBar3;

    Button toRgbButton;
    Button toVhsButton;

    ImageView colorView;

    boolean isVhs = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        value1Text = findViewById(R.id.value1Text);
        value2Text = findViewById(R.id.value2Text);
        value3Text = findViewById(R.id.value3Text);

        value1 = findViewById(R.id.value1);
        value2 = findViewById(R.id.value2);
        value3 = findViewById(R.id.value3);

        seekBar1 = findViewById(R.id.seekBar1);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);

        toRgbButton = findViewById(R.id.toRgbButton);
        toVhsButton = findViewById(R.id.toVhsButton);

        colorView = findViewById(R.id.colorView);


        toVhsButton.setBackgroundColor(getResources().getColor(R.color.teal_200));
        value1.setText("" + seekBar1.getProgress());
        value2.setText("" + seekBar2.getProgress());
        value3.setText("" + seekBar2.getProgress());


        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                createColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                createColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                createColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void createColor() {

        int getValue1 = seekBar1.getProgress();
        int getValue2 = seekBar2.getProgress();
        int getValue3 = seekBar3.getProgress();

        value1.setText("" + getValue1);
        value2.setText("" + getValue2);
        value3.setText("" + getValue3);

        if (isVhs) {
            colorView.setBackgroundColor(Color.HSVToColor(new float[]{getValue1, getValue2 / 100f, getValue3 / 100f}));
        } else {
            colorView.setBackgroundColor(Color.argb(255, getValue1, getValue2, getValue3));
        }


    }

    public void toRGB(View v) {

        isVhs = false;

        seekBar1.setMax(255);
        seekBar2.setMax(255);
        seekBar3.setMax(255);

        value1Text.setText("Red:");
        value2Text.setText("Green:");
        value3Text.setText("Blue:");

        toRgbButton.setBackgroundColor(getResources().getColor(R.color.teal_200));
        toVhsButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
    }

    public void toVHS(View v) {

        isVhs = true;

        seekBar1.setMax(360);
        seekBar2.setMax(100);
        seekBar3.setMax(100);

        value1Text.setText("Hue:");
        value2Text.setText("Saturation:");
        value3Text.setText("Value:");

        toVhsButton.setBackgroundColor(getResources().getColor(R.color.teal_200));
        toRgbButton.setBackgroundColor(getResources().getColor(R.color.purple_500));

    }


}



