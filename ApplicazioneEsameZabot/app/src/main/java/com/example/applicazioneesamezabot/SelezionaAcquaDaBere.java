package com.example.applicazioneesamezabot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelezionaAcquaDaBere extends AppCompatActivity {

    private static int ACTIVITY_ID = 1;

    Button buttonBicchierePiccolo;
    Button getButtonBicchiereMedio;
    Button buttonBicchiereGrande;

    int waterDrank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleziona_acqua_da_bere);
        getSupportActionBar().hide();

        buttonBicchierePiccolo = findViewById(R.id.buttonBicchierePiccolo);
        getButtonBicchiereMedio = findViewById(R.id.buttonBicchiereMedio);
        buttonBicchiereGrande = findViewById(R.id.buttonBicchiereGrande);
    }

    public void onClick(View v){

        int buttonID = v.getId();

        switch (buttonID){

            case R.id.buttonBicchierePiccolo:   waterDrank = 100;
                                                break;

            case R.id.buttonBicchiereMedio:     waterDrank = 150;
                                                break;

            case R.id.buttonBicchiereGrande:    waterDrank = 200;
                                                break;
        }

        finish();

    }

    @Override
    public void finish() {
        Intent goBack = new Intent();
        goBack.putExtra("waterDrank", waterDrank);
        setResult(RESULT_OK, goBack);
        super.finish();

    }
}
