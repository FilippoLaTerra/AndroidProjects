package com.example.e05_dadi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.contentcapture.DataShareWriteAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView numberOfCoinsText;
    TextView roundsCounterText;
    TextView titleText;

    ImageView imageDado1;
    ImageView imageDado2;
    ImageView imageDado3;

    Button playButton;

    int spriteDadi[];

    int monete;
    int round;

    int valoreDado1;
    int valoreDado2;
    int valoreDado3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        numberOfCoinsText = findViewById(R.id.numberOfCoinsText);
        roundsCounterText = findViewById(R.id.roundsCounterText);
        titleText = findViewById(R.id.titleText);
        imageDado1 = findViewById(R.id.imageDado1);
        imageDado2 = findViewById(R.id.imageDado2);
        imageDado3 = findViewById(R.id.imageDado3);
        playButton = findViewById(R.id.playButton);

        spriteDadi = new int[]{
                R.drawable.red_dice_1,
                R.drawable.red_dice_2,
                R.drawable.red_dice_3,
                R.drawable.red_dice_4,
                R.drawable.red_dice_5,
                R.drawable.red_dice_6,
        };

        monete = 100;
        round = 1;

        aggiornaStats(monete, round);
    }

    public void rigiocaClick(View v) {
        if (monete >= 5) {
            giocaTurno();
        } else {
            titleText.setText("Non hai più soldi..");
            playButton.setEnabled(false);
        }

    }

    public void ricomincia(View v) {
        recreate();
    }

    public void giocaTurno() {

        monete -= 5;
        round += 1;

        aggiornaStats(monete, round);

        valoreDado1 = tiraDado(imageDado1);
        valoreDado2 = tiraDado(imageDado2);
        valoreDado3 = tiraDado(imageDado3);


        if (valoreDado1 == valoreDado2 && valoreDado2 == valoreDado3) {
            monete += 50;
            titleText.setText("TRIS!!!");
        } else if (valoreDado1 == valoreDado2 || valoreDado2 == valoreDado3 || valoreDado1 == valoreDado3) {
            monete += 10;
            titleText.setText("COPPIA!!!");
        } else {
            titleText.setText("Non hai fatto nulla...");
        }

        aggiornaStats(monete, round);
    }

    public int tiraDado(ImageView image) {

        int result = new Random().nextInt(5);

        int newImageSrc = spriteDadi[result];

        giraDado(image, newImageSrc);

        return result + 1;
    }

    public void aggiornaStats(int monete, int round) {

        numberOfCoinsText.setText("Monete:" + monete);
        roundsCounterText.setText(round + "° round");
    }

    public void giraDado(ImageView image, int newImage) {


        new CountDownTimer(225, 5) {

            int rotazione = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                rotazione += 6;
                image.setRotationY(rotazione);
            }

            @Override
            public void onFinish() {
                image.setRotationY(-90);
                image.setImageResource(newImage);

                new CountDownTimer(225, 5) {

                    int rotazione = -90;

                    @Override
                    public void onTick(long millisUntilFinished) {
                        rotazione += 6;
                        image.setRotationY(rotazione);
                    }

                    @Override
                    public void onFinish() {
                        image.setRotationY(0);
                    }
                }.start();
            }
        }.start();




    }


}