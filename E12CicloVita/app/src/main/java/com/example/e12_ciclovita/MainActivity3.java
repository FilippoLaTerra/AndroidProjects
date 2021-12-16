package com.example.e12_ciclovita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sitoprova.ovh/"));
        startActivity(i);
        finish();
    }
}