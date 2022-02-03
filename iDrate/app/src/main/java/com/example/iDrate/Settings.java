package com.example.iDrate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {

    ImageView imageViewBackArrow;

    EditText editTextNomePiantina;

    TextView textViewNomeUtente, textViewAcquaBevutaInTotaleOggi, textViewAcquaBevutaInTotale;

    Button buttonRinomina;

    String newName = "";


    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    DatabaseReference reference = rootNode.getReference("Users");

    protected FirebaseAuth authenticator = FirebaseAuth.getInstance();
    DatabaseReference userReference = rootNode.getReference("Users").child(authenticator.getCurrentUser().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        buttonRinomina = findViewById(R.id.buttonRinomina);
        imageViewBackArrow = findViewById(R.id.imageViewBackArrow);
        editTextNomePiantina = findViewById(R.id.editTextNomePiantina);
        textViewNomeUtente = findViewById(R.id.textViewNomeUtente);
        textViewAcquaBevutaInTotaleOggi = findViewById(R.id.textViewAcquaBevutaInTotaleOggi);
        textViewAcquaBevutaInTotale = findViewById(R.id.textViewAcquaBevutaInTotale);

        reference.child( authenticator.getCurrentUser().getUid() ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                UserHelperClass currentUser = dataSnapshot.getValue(UserHelperClass.class);

                textViewAcquaBevutaInTotaleOggi.setText(currentUser.waterDrankTodayInCentiliters + " Cl");
                textViewAcquaBevutaInTotale.setText((float)(currentUser.lifetimeWaterDrankInCentiliters/100) + " L");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



        textViewNomeUtente.setText(authenticator.getCurrentUser().getEmail());


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