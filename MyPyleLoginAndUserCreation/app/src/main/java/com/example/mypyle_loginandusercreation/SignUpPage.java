package com.example.mypyle_loginandusercreation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SignUpPage extends AppCompatActivity {

    protected EditText[] fields = new EditText[5];
    protected int userID;
    protected int uniquePassCode;
    protected List userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        getSupportActionBar().hide();
        fields[0] = findViewById(R.id.firstNameField);
        fields[1] = findViewById(R.id.lastNameField);
        fields[2] = findViewById(R.id.emailField);
        fields[3] = findViewById(R.id.passwordField);
        fields[4] = findViewById(R.id.confirmPasswordField);

    }

    public int convalidateUser(View v) {

        Boolean anyFieldEmpty = false;
        for (EditText f : fields) {
            if (isEmpty(f)) {
                anyFieldEmpty = true;
                f.setError("Cannot Be empty");
                Log.e("Error", f.getId() + " cannot be empty");
            }
        }
        if(anyFieldEmpty){
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
            fields[3].setError("Passwords do not match");
            fields[4].setError("Passwords do not match");
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