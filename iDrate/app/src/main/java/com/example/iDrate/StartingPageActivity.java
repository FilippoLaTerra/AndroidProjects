package com.example.iDrate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartingPageActivity extends AppCompatActivity {

    Button buttonRegisterMain, buttonLoginMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);
        getSupportActionBar().hide();

        buttonRegisterMain = findViewById(R.id.buttonRegisterMain);
        buttonLoginMain = findViewById(R.id.buttonLoginMain);

        buttonRegisterMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(switchToRegister);

            }
        });

        buttonLoginMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(switchToLogin);
            }
        });


    }
}