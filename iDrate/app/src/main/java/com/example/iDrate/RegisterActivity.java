package com.example.iDrate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextEta, editTextPeso, editTextUsername;
    private Button buttonRegisterConfirm;

    private FirebaseAuth authenticator = FirebaseAuth.getInstance();

    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEta = findViewById(R.id.editTextEta);
        editTextPeso = findViewById(R.id.editTextPeso);
        editTextUsername = findViewById(R.id.editTextUsername);

        buttonRegisterConfirm = findViewById(R.id.buttonRegisterConfirm);

        buttonRegisterConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_email = editTextEmail.getText().toString();
                String txt_password = editTextPassword.getText().toString();
                String txt_eta = editTextEta.getText().toString();
                String txt_peso = editTextPeso.getText().toString();
                String txt_username = editTextUsername.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegisterActivity.this, "Credenziali vuote", Toast.LENGTH_SHORT).show();

                } else if (txt_password.length()<6) {
                    Toast.makeText(RegisterActivity.this, "La password deve avere almeno 6 caratteri", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(txt_eta)<0) {
                    Toast.makeText(RegisterActivity.this, "Età non valida", Toast.LENGTH_SHORT);
                } else if (Integer.parseInt(txt_peso)<0) {
                    Toast.makeText(RegisterActivity.this, "Peso non valido", Toast.LENGTH_SHORT);
                }
                else {
                    registerUser(txt_email, txt_password);
                    String UID = authenticator.getCurrentUser().getUid();
                    createUserDatabase(UID, txt_username, Integer.parseInt(txt_peso), Integer.parseInt(txt_eta));

                    Intent switchToStartingPage = new Intent(getApplicationContext(), StartingPageActivity.class);
                    startActivity(switchToStartingPage);
                    finish();

                }

            }
        });

    }

    private void registerUser(String email, String password){
        authenticator.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registrazione completata con successo", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegisterActivity.this, "Registrazione fallita", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUserDatabase(String UID, String username, int peso, int eta){

        UserHelperClass databaseUser = new UserHelperClass(UID, username, peso, eta);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");
        reference.child(UID).setValue(databaseUser);

    }

}