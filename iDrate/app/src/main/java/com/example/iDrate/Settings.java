package com.example.iDrate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {

    ImageView imageViewBackArrow;

    EditText editTextNomePiantina;

    TextView textViewNomeUtente;
    TextView textViewAcquaBevutaInTotale;

    Button buttonRinomina;

    String newName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        buttonRinomina = findViewById(R.id.buttonRinomina);
        imageViewBackArrow = findViewById(R.id.imageViewBackArrow);
        editTextNomePiantina = findViewById(R.id.editTextNomePiantina);
        textViewNomeUtente = findViewById(R.id.textViewNomeUtente);
        textViewAcquaBevutaInTotale = findViewById(R.id.textViewAcquaBevutaInTotale);


        textViewNomeUtente.setText(getIntent().getExtras().getString("email"));
        textViewAcquaBevutaInTotale.setText(getIntent().getExtras().getInt("waterDrank") + " Cl");



        buttonRinomina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextNomePiantina.getText().toString().equals("")){
                    newName = editTextNomePiantina.getText().toString();
                    finish();
                }
            }
        });

        imageViewBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void finish() {
        Intent goBack = new Intent();

        goBack.putExtra("newName", newName.toUpperCase());
        goBack.putExtra("waterDrank", 0);

        setResult(RESULT_OK, goBack);
        super.finish();

    }
}