package com.example.applicazioneesamezabot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String NOME_PIANTA;
    private static int ACTIVITY_ID = 1;

    ImageView imageViewPiantina;
    ImageView imageViewSettings;

    Button buttonBevi;

    ProgressBar progressBarAcquaBevuta;

    TextView textViewMessaggi;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            drainWaterLevel();
            getWaterLevel();
            handler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        imageViewPiantina = findViewById(R.id.imageViewPiantina);
        imageViewSettings = findViewById(R.id.imageViewSettings);
        buttonBevi = findViewById(R.id.buttonBevi);
        progressBarAcquaBevuta = findViewById(R.id.progressBarAcquaBevuta);
        textViewMessaggi = findViewById(R.id.textViewMessaggi);

        progressBarAcquaBevuta.setProgress(1000);

        handler.postDelayed(runnable, 1000);
        getWaterLevel();

        textViewMessaggi.setText("Ricordati di rimanere idratato ma senza esagerare!");

        buttonBevi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityToDrink = new Intent(getApplicationContext(), SelezionaAcquaDaBere.class);
                startActivityForResult(switchActivityToDrink, ACTIVITY_ID);
            }
        });
    }




    public void getWaterLevel(){

        if(progressBarAcquaBevuta.getProgress() >= 800){
            imageViewPiantina.setImageResource(R.drawable.pianta_hydrated);
        }else if (progressBarAcquaBevuta.getProgress() < 800 && progressBarAcquaBevuta.getProgress() > 500){
            imageViewPiantina.setImageResource(R.drawable.pianta_need_to_drink);

        }else if (progressBarAcquaBevuta.getProgress() <=500){
            imageViewPiantina.setImageResource(R.drawable.pianta_dehydrated);

        }

    }

    public void drainWaterLevel() {
        progressBarAcquaBevuta.setProgress(progressBarAcquaBevuta.getProgress()-1);
    }

    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_ID && resultCode == RESULT_OK) {

            int waterDrank = data.getExtras().getInt("waterDrank");
            int currentWater = progressBarAcquaBevuta.getProgress();

            progressBarAcquaBevuta.setProgress(currentWater + waterDrank);

        }
    }

}