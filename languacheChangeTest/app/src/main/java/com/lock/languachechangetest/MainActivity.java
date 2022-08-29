package com.lock.languachechangetest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView LanguageIndicator;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadLocale();
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();
            }
        });

    }

    private void showChangeLanguageDialog() {

        final String[] listItems = {"italiano", "english"};
        AlertDialog.Builder  mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("choose language");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(i == 0){

                    setLocale("it");
                    recreate();
                }

                if(i == 1){

                    setLocale("en");
                    recreate();
                }

                dialogInterface.dismiss();

            }
        });


        AlertDialog mDialog = mBuilder.create();

        mDialog.show();

    }

    private void setLocale(String lang) {

       Locale locale = new Locale(lang);
       Locale.setDefault(locale);

       Configuration configuration = new Configuration();
       configuration.locale = locale;

       getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
       SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
       editor.putString("My_Lang", lang);
       editor.apply();
        
    }

    public void loadLocale(){

        SharedPreferences preferences =  getSharedPreferences("Settings", MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);

    }
}