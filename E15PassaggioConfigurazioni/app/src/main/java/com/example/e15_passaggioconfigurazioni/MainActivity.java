package com.example.e15_passaggioconfigurazioni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView settingsIconImageView;
    ConstraintLayout mainScreenLayout;
     int background = new Color().HSVToColor(new float[]{0,0,100});
     private static int ACTIVITY_ID  = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainScreenLayout = findViewById(R.id.mainScreenLayout);

        mainScreenLayout.setBackgroundColor(background);

        settingsIconImageView = findViewById(R.id.settingsIconImageView);

        settingsIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivity = new Intent(getApplicationContext(), Settings.class);
                startActivityForResult(switchActivity, ACTIVITY_ID);

            }
        });

    }


    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_ID && resultCode == RESULT_OK) {

            background = data.getExtras().getInt("color");
            mainScreenLayout.setBackgroundColor(background);
        }
    }

}