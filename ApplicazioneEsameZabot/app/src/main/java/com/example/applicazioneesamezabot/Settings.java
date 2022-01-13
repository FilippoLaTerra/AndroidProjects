package com.example.applicazioneesamezabot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Settings extends AppCompatActivity {

    ImageView imageViewBackArrow;

    Button buttonRinomina;

    String newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        buttonRinomina = findViewById(R.id.buttonRinomina);
        imageViewBackArrow = findViewById(R.id.imageViewBackArrow);


        imageViewBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToMainScreen = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(switchToMainScreen);
            }
        });

    }

    @Override
    public void finish() {
        Intent goBack = new Intent();

        if(!newName.equals("")){
            goBack.putExtra("newName", newName);
        }

        setResult(RESULT_OK, goBack);
        super.finish();

    }
}