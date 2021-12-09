package com.example.mypyle_loginandusercreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SignUpPage extends AppCompatActivity {

    protected EditText[] fields = new EditText[5];
    protected TextInputLayout[] layouts = new TextInputLayout[5];
    private Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        getSupportActionBar().hide();

        fields[0] = findViewById(R.id.firstNameEditText);
        fields[1] = findViewById(R.id.lastNameEditText);
        fields[2] = findViewById(R.id.emailEditText);
        fields[3] = findViewById(R.id.passwordEditText);
        fields[4] = findViewById(R.id.confirmPasswordEditText);

        layouts[0] = findViewById(R.id.firstNameTextLayout);
        layouts[1] = findViewById(R.id.lastNameTextLayout);
        layouts[2] = findViewById(R.id.emailTextLayout);
        layouts[3] = findViewById(R.id.passwordTextLayout);
        layouts[4] = findViewById(R.id.confirmPasswordTextLayout);

        database = database.getInstance();

    }

    public void convalidateUser(View v) {

        Boolean anyFieldEmpty = false;

        for (TextInputLayout l : layouts) {
            l.setError(null);
        }

        for (int i = 0; i < fields.length; i++) {
            if (isEmpty(fields[i])) {
                anyFieldEmpty = true;
                layouts[i].setError("Cannot Be empty");
                Log.e("Error", fields[i].getId() + " cannot be empty");
            }
        }

        if (!anyFieldEmpty) {
            String password = fields[3].getText().toString();
            String passwordConfirmation = fields[4].getText().toString();

            if (password.equals(passwordConfirmation)) {

                createUser();

                Log.i("Switch Activities", "starting statPage");
                Intent switchActivity = new Intent(this, StartPage.class);
                startActivity(switchActivity);
                finish();


            } else {
                //error ms of passwords not matching

                fields[3].getText().clear();
                fields[4].getText().clear();
                layouts[3].setError("Passwords do not match");
                layouts[4].setError("Passwords do not match");
                Log.e("Error", "Passwords do not match");
            }
        }

    }

    private boolean isEmpty(EditText t) {
        return (t.getText().toString().equals(""));
    }

    private void createUser() {



        String firstName = fields[0].getText().toString();
        String lastName = fields[1].getText().toString();
        String email = fields[2].getText().toString();
        String password = fields[3].getText().toString();

        int userID = database.createID();

        database.addUserToDatabase(new User(firstName, lastName, email, password, userID));



        Log.i("User", "created new user with ID " + userID);

    }

}