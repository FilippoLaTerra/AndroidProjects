package com.example.e05_dadi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.contentcapture.DataShareWriteAdapter;

public class MainActivity extends AppCompatActivity {

    Drawable spiteDadi[] = new Drawable[]{  };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
}