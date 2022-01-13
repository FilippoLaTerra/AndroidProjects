package com.example.applicazioneesamezabot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String NOME_PIANTA;
    private static int ACTIVITY_ID = 1;
    private int imgPiantina;

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

        createNotificationChannel();

        imageViewPiantina = findViewById(R.id.imageViewPiantina);
        imageViewSettings = findViewById(R.id.imageViewSettings);
        buttonBevi = findViewById(R.id.buttonBevi);
        progressBarAcquaBevuta = findViewById(R.id.progressBarAcquaBevuta);
        textViewMessaggi = findViewById(R.id.textViewMessaggi);

        progressBarAcquaBevuta.setProgress(1000);

        handler.postDelayed(runnable, 1000);
        getWaterLevel();

        textViewMessaggi.setText("Ricordati di rimanere idratato ma senza esagerare!");

        imageViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToSettings = new Intent(getApplicationContext(), Settings.class);
                startActivity(switchToSettings);
            }
        });

        buttonBevi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityToDrink = new Intent(getApplicationContext(), SelezionaAcquaDaBere.class);
                startActivityForResult(switchActivityToDrink, ACTIVITY_ID);
            }
        });
    }


    public void getWaterLevel() {

        if (progressBarAcquaBevuta.getProgress() >= 800) {
            imageViewPiantina.setImageResource(R.drawable.pianta_hydrated);



        } else if (progressBarAcquaBevuta.getProgress() < 800 && progressBarAcquaBevuta.getProgress() > 500) {
            imageViewPiantina.setImageResource(R.drawable.pianta_need_to_drink);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "reminder")
                    .setSmallIcon(R.drawable.ic_baseline_bubble_chart_24)
                    .setContentTitle("E' da tanto che non bevi")
                    .setContentText("Ricordati di bere abbastanza, ")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            notificationManager.notify(100, builder.build());


        } else if (progressBarAcquaBevuta.getProgress() <= 500) {
            imageViewPiantina.setImageResource(R.drawable.pianta_dehydrated);


            NotificationCompat.Builder notification = newNotification("Sei disidratato","sei uno stolto, un cotechino cotto e disidratato","reminder");

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            notificationManager.notify(100, notification.build());

        }

    }

    public void drainWaterLevel() {
        progressBarAcquaBevuta.setProgress(progressBarAcquaBevuta.getProgress() - 1);
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

    public void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "reminder";
            String description = "Channer for reminders of drinking water";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("reminder", name, importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }


    }

    protected NotificationCompat.Builder newNotification(String title, String text, String channelID){

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.ic_baseline_bubble_chart_24)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return notification;
    }

/*
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);




    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        counter = savedInstanceState.getInt("clicks");
        dateAndTime = (Date) savedInstanceState.getSerializable("dateAndTime");

        updateCounter(counter);
        updateTime(dateAndTime);
    }
*/
}