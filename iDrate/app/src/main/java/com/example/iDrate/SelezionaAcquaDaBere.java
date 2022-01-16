package com.example.iDrate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelezionaAcquaDaBere extends AppCompatActivity {

    private static int ACTIVITY_ID = 2;

    Button buttonBicchierePiccolo;
    Button getButtonBicchiereMedio;
    Button buttonBicchiereGrande;
    Button buttonBottigliaMezzoLitro;
    Button buttonBottigliaLitro;

    int waterDrank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleziona_acqua_da_bere);
        getSupportActionBar().hide();

        buttonBicchierePiccolo = findViewById(R.id.buttonBicchierePiccolo);
        getButtonBicchiereMedio = findViewById(R.id.buttonBicchiereMedio);
        buttonBicchiereGrande = findViewById(R.id.buttonBicchiereGrande);
        buttonBottigliaMezzoLitro = findViewById(R.id.buttonBottigliaMezzoLitro);
        buttonBottigliaLitro = findViewById(R.id.buttonBottigliaLitro);
    }

    public void onClick(View v){

        int buttonID = v.getId();

        switch (buttonID){

            case R.id.buttonBicchierePiccolo:   waterDrank = 250;
                                                break;

            case R.id.buttonBicchiereMedio:     waterDrank = 350;
                                                break;

            case R.id.buttonBicchiereGrande:    waterDrank = 450;
                                                break;

            case R.id.buttonBottigliaMezzoLitro:    waterDrank = 500;
                                                    break;

            case R.id.buttonBottigliaLitro:     waterDrank = 1000;
                                                break;
        }

        finish();

    }

    @Override
    public void finish() {
        Intent goBack = new Intent();
        goBack.putExtra("waterDrank", waterDrank);
        goBack.putExtra("newName", "");
        setResult(RESULT_OK, goBack);
        super.finish();

    }
}
