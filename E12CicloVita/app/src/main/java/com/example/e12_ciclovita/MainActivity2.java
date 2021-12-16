package com.example.e12_ciclovita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    Button changeActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.e("App status", "application created");

        changeActivityButton = findViewById(R.id.changeActivityButton);

        changeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(switchActivity);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("App status", "application started");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("App status", "application resumed");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e("App status", "application paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("App status", "application stopped");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("App status", "application restarted");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("App status", "application destroyed");
    }
}