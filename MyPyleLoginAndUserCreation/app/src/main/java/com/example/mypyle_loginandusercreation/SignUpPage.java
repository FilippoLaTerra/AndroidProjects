package com.example.mypyle_loginandusercreation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SignUpPage extends AppCompatActivity {

    protected EditText[] fields = new EditText[5];
    protected TextInputLayout[] layouts = new TextInputLayout[5];
    protected int userID;
    protected int uniquePassCode;
    protected List userData;

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

    }

    public int convalidateUser(View v) {

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
        if (anyFieldEmpty) {
            return 1;
        }
        String password = fields[3].getText().toString();
        String passwordConfirmation = fields[4].getText().toString();

        if (doPasswordsMatch(password, passwordConfirmation)) {

            Log.i("User created", "Created new user with ID " + userID);
        } else {
            //error ms of passwords not matching

            fields[3].getText().clear();
            fields[4].getText().clear();
            layouts[3].setError("Passwords do not match");
            layouts[4].setError("Passwords do not match");
            Log.e("Error", "Passwords do not match");
        }

        return 0;
    }

    private boolean isEmpty(EditText t) {
        return (t.getText().toString().equals(""));
    }

    private boolean doPasswordsMatch(String p1, String p2) {
        return p1.equals(p2);
    }

}