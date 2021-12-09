package com.example.mypyle_loginandusercreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartPage extends AppCompatActivity {

    Button logInButton;
    Button signInButton;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        getSupportActionBar().hide();

        database = database.getInstance();

        logInButton = findViewById(R.id.logInButton);
        signInButton = findViewById(R.id.signUpButton);
    }

    public void onClick(View v) {

        if (v.getId() == logInButton.getId()) {
            Log.i("Switch Activities", "starting Signup Page");
            Intent switchActivity = new Intent(this, LogInPage.class);
            startActivity(switchActivity);
        } else {
            Log.i("Switch Activities", "starting Login Page");
            Intent switchActivity = new Intent(this, SignUpPage.class);
            startActivity(switchActivity);
            finish();
        }

    }


}