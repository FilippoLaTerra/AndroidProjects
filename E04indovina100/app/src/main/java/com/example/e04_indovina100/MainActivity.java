package com.example.e04_indovina100;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.IllegalFormatCodePointException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    EditText guessEditText;
    TextView feedbackTest;
    TextView numberOfGuessesText;
    Button guessInputButton;
    int numberToGuess;
    int numberOfGuesses;
    boolean winFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guessEditText = findViewById(R.id.guessEditText);
        feedbackTest = findViewById(R.id.feedbackText);
        guessInputButton = findViewById(R.id.guessInputButton);
        numberOfGuessesText = findViewById(R.id.numberOfGuessesText);

        numberToGuess = new Random().nextInt(99) + 1;
        numberOfGuesses = 0;
        winFlag = false;

        feedbackTest.setText("Sto pensando a un numero da 1 a 100; indovina!");
    }

    public void onClick(View v) {

        if (winFlag){
            recreate();
        } else {
            makeAGuess();
        }

    }

    public void makeAGuess() {

        if (guessEditText.getText().toString().equals("")) {
            guessEditText.setError("Field cannot be empty");
            feedbackTest.setText("Devi Inserire un numero!");

        } else {

            int guess = Integer.parseInt(guessEditText.getText().toString());

            if (guess > numberToGuess) {
                feedbackTest.setText("Troppo grande...");
                numberOfGuesses++;
                updateGuesses(numberOfGuesses);
            } else if (guess < numberToGuess) {
                feedbackTest.setText("Troppo piccolo...");
                numberOfGuesses++;
                updateGuesses(numberOfGuesses);
            } else {
                feedbackTest.setText("Hai INDOVINATO!!");
                numberOfGuesses++;
                updateGuesses(numberOfGuesses);
                winFlag = true;
                guessInputButton.setText("Rigioca");
            }

        }

    }

    public void updateGuesses(int nOfGuesses) {
        numberOfGuessesText.setText("Tentativi: " + nOfGuesses);
    }

}
