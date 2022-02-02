package com.example.iDrate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static int ACTIVITY_ID = 1;

    ImageView imageViewPiantina, imageViewSettings;
    Button buttonBevi;
    ProgressBar progressBarAcquaBevuta;
    TextView textViewMessaggi, textViewNomepianta;

    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    DatabaseReference reference = rootNode.getReference("Users");

    protected FirebaseAuth authenticator = FirebaseAuth.getInstance();
    DatabaseReference userReference = rootNode.getReference("Users").child(authenticator.getCurrentUser().getUid());

    protected UserHelperClass currentUser;
    protected String plantName;
    protected int dailyWaterQuoteInMilliliters;

    private Handler handler = new Handler();
    private Runnable waterDrainageFunction = new Runnable() {
        @Override
        public void run() {

            int currentWaterLevel = progressBarAcquaBevuta.getProgress();
            int onePercentOfMax = progressBarAcquaBevuta.getMax()/100;
            drainWaterLevel();
            

            //controlla che il livello sia sceso, e manda la notifica solo se è sceso rispetto al valore iniziale
            if (currentWaterLevel > progressBarAcquaBevuta.getProgress()) {
                if (progressBarAcquaBevuta.getProgress() == onePercentOfMax * 80) {

                    NotificationCompat.Builder notification = newNotification("E' da tanto che non bevi", "Ricordati di bere abbastanza", "reminder");

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                    notificationManager.notify(100, notification.build());
                } else if (progressBarAcquaBevuta.getProgress() == onePercentOfMax * 50) {

                    NotificationCompat.Builder notification = newNotification("Sei disidratato", "sei disidratato, dovresti bere il rpima possibile", "reminder");

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                    notificationManager.notify(101, notification.build());

                }
            }

            setPlantStatus();

            handler.postDelayed(this, 100);
        }
    };

    //funzione che aggiorna il testo ogni 10 secondi
    private Runnable textChangeFunction = new Runnable() {
        @Override
        public void run() {
            changeText();
            handler.postDelayed(this, 10000);
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
        textViewNomepianta = findViewById(R.id.textViewNomepianta);

        reference.child( authenticator.getCurrentUser().getUid() ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                currentUser = dataSnapshot.getValue(UserHelperClass.class);
                updateAllFields(currentUser);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        handler.postDelayed(waterDrainageFunction, 1);
        handler.postDelayed(textChangeFunction, 2);

        textViewMessaggi.setText(getNewFlavourText(-1));

        imageViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToSettings = new Intent(getApplicationContext(), Settings.class);
                switchToSettings.putExtra("email", authenticator.getCurrentUser().getEmail());
                switchToSettings.putExtra("waterDrank", currentUser.waterDrankTodayInCentiliters/100);
                startActivityForResult(switchToSettings, ACTIVITY_ID);
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

    public void setPlantStatus() {

        int onePercentOfMax = progressBarAcquaBevuta.getMax()/100;

        if (progressBarAcquaBevuta.getProgress() >= onePercentOfMax * 80) {
            imageViewPiantina.setImageResource(R.drawable.pianta_hydrated);
            imageViewPiantina.setTag(R.drawable.pianta_hydrated);

        } else if (progressBarAcquaBevuta.getProgress() < onePercentOfMax * 80 && progressBarAcquaBevuta.getProgress() > onePercentOfMax * 50) {
            imageViewPiantina.setImageResource(R.drawable.pianta_need_to_drink);
            imageViewPiantina.setTag(R.drawable.pianta_need_to_drink);

        } else if (progressBarAcquaBevuta.getProgress() <= onePercentOfMax * 50) {
            imageViewPiantina.setImageResource(R.drawable.pianta_dehydrated);
            imageViewPiantina.setTag(R.drawable.pianta_dehydrated);
        }

    }

    public void changeText(){

        int onePercentOfMax = progressBarAcquaBevuta.getMax()/100;

        if (progressBarAcquaBevuta.getProgress() >= onePercentOfMax * 80) {

            textViewMessaggi.setText(getNewFlavourText(-1));

        } else if (progressBarAcquaBevuta.getProgress() < onePercentOfMax * 80 && progressBarAcquaBevuta.getProgress() > onePercentOfMax * 50) {

            textViewMessaggi.setText(getNewFlavourText(0));

        } else if (progressBarAcquaBevuta.getProgress() <= onePercentOfMax * 50) {

            textViewMessaggi.setText(getNewFlavourText(1));

        }

    }

    public void drainWaterLevel() {
        progressBarAcquaBevuta.setProgress(progressBarAcquaBevuta.getProgress() - 1);
    }

    public String getNewFlavourText(int txtType){

        String[] randomMsg = new String[]{  "Tienimi in salute tenendoti in salute!",
                                            "Ricordati di bere!",
                                            "Dovresti bere almeno " + (double) dailyWaterQuoteInMilliliters/1000 + " Litri al giorno",
                                            //"Hai già bevuto " + currentUser.waterDrankTodayInCentiliters + " Centilitri d'acqua oggi!"
                                            };


        String[] needToDrinkMsg = new String[]{ "Non stai bevendo da un poco, dovresti reidratarti",
                                                "Non hai sete? Non bevi da un po'",
                                                "Dovresti bere, stare tanto senza bere non ti fa bene"
                                                };

        String[] dehydratedMsg = new String[]{  "Sei disidratato! Dovresti bere il prima possibile",
                                                "Lo sai che la disidatrazione può causare mal di testa? e morte?",
                                                "Sto morendo, ed è colpa tua",
                                                "Ti prego bevi qualcosa..."
                                                };
        int randomMsgindex;
        String returnMsg;

        switch (txtType){

            case 0: randomMsgindex = new Random().nextInt(needToDrinkMsg.length);
                    returnMsg = needToDrinkMsg[randomMsgindex];
                    break;
            case 1: randomMsgindex = new Random().nextInt(dehydratedMsg.length);
                    returnMsg = dehydratedMsg[randomMsgindex];
                    break;
            default: randomMsgindex = new Random().nextInt(randomMsg.length);
                     returnMsg = randomMsg[randomMsgindex];
                     break;

        }

        return returnMsg;
    }

    protected void updateAllFields(UserHelperClass user){

        this.currentUser = user;
        this.dailyWaterQuoteInMilliliters = currentUser.waterToDrinkInCentiliters * 10;
        this.progressBarAcquaBevuta.setMax(dailyWaterQuoteInMilliliters);
        this.plantName = currentUser.currentPlantName;
        this.textViewNomepianta.setText(plantName);

        try {
            int savedProgress = this.getIntent().getExtras().getInt("waterDrank");
            this.progressBarAcquaBevuta.setProgress(savedProgress);
        }catch (Exception e){
            this.progressBarAcquaBevuta.setProgress(dailyWaterQuoteInMilliliters);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_ID && resultCode == RESULT_OK) {

            int waterDrank = data.getExtras().getInt("waterDrank");
            int currentWater = progressBarAcquaBevuta.getProgress();

            String newName = data.getExtras().getString("newName");

            if (!newName.equals("")) {
                plantName = newName;
                textViewNomepianta.setText(plantName);
            }

            progressBarAcquaBevuta.setProgress(currentWater + waterDrank);
            currentUser.waterDrankTodayInCentiliters+= waterDrank/10;
            userReference.child("waterDrankTodayInCentiliters").setValue(currentUser.waterDrankTodayInCentiliters);

        }
    }

    public void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "reminder";
            String description = "Channel for reminders of drinking water";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("reminder", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }

    }

    protected NotificationCompat.Builder newNotification(String title, String text, String channelID) {

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.putExtra("waterDrank", progressBarAcquaBevuta.getProgress());
        PendingIntent returnToApp =     PendingIntent.getActivity(getApplicationContext(),
                                        0, notificationIntent,
                                        PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.ic_baseline_bubble_chart_24)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(returnToApp);

        return notification;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("ImageSrc", (int) imageViewPiantina.getTag());

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int imageSrc = savedInstanceState.getInt("ImageSrc");

        imageViewPiantina.setImageResource(imageSrc);
    }


}