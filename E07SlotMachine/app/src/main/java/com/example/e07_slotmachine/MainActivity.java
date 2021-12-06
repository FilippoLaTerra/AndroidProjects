package com.example.e07_slotmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView numberOfCoinsText;
    TextView roundsCounterText;
    TextView titleText;

    int[] spriteSlot = new int[]{
            R.drawable.slot_7,
            R.drawable.slot_apple,
            R.drawable.slot_bell,
            R.drawable.slot_cherries,
            R.drawable.slot_crown,
            R.drawable.slot_diamond,
            R.drawable.slot_star,
            R.drawable.slot_strawberry,
            R.drawable.slot_watermelon,
    };

    ImageView[] imageSlots;

    Button playButton;

    int monete;
    int round;

    int[] valoriSlot = new int[spriteSlot.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        imageSlots = new ImageView[spriteSlot.length];

        numberOfCoinsText = findViewById(R.id.numberOfCoinsText);
        roundsCounterText = findViewById(R.id.roundsCounterText);
        titleText = findViewById(R.id.titleText);

        imageSlots = new ImageView[]{
                findViewById(R.id.imageDado1),
                findViewById(R.id.imageDado2),
                findViewById(R.id.imageDado3),
                findViewById(R.id.imageDado4),
                findViewById(R.id.imageDado5),
        };

        playButton = findViewById(R.id.playButton);


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

        valoriSlot = new int[spriteSlot.length];
        aggiornaStats(monete, round);

        valueRolls(getRolls());
        aggiornaStats(monete, round);
    }

    public void valueRolls(int[] rolls) {

        int highestStreak = 0;

        for (int value : rolls) {

            if (value > highestStreak) {
                highestStreak = value;
            }

        }

        switch (highestStreak) {
            case 5:
                titleText.setText("HAI FATTO JACKPOT!!");
                monete += 100;
                break;
            case 4:
                titleText.setText("Quattro di un tipo!!");
                monete += 50;
                break;
            case 3:
                titleText.setText("TRIS");
                monete += 10;
                break;
            case 2:
                titleText.setText("coppia");
                monete += 5;
                break;
            default:
                titleText.setText("non hai fatto nulla...");
                break;
        }

    }

    public int[] getRolls() {

        for (int i = 0; i < imageSlots.length; i++) {

            int result = getSimbolo(imageSlots[i]);
            valoriSlot[result]++;
        }

        return valoriSlot;

    }

    public int getSimbolo(ImageView image) {

        int result = new Random().nextInt(spriteSlot.length);
        image.setImageResource(spriteSlot[result]);
        return result;
    }

    public void aggiornaStats(int monete, int round) {

        numberOfCoinsText.setText("Monete:" + monete);
        roundsCounterText.setText(round + "° round");
    }

}