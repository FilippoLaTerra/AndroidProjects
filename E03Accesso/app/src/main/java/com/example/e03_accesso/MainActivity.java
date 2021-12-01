package com.example.e03_accesso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    TextInputLayout usernameTextInput;
    TextInputEditText usernameTextInputEdit;
    TextInputLayout passwordTextInput;
    TextInputEditText passwordTextInputEdit;
    Button loginButton;
    String[][] userDatabase;
    TextView feedbackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameTextInput = findViewById(R.id.usernameTextInput);
        usernameTextInputEdit = findViewById(R.id.usernameTextInputEdit);
        passwordTextInput = findViewById(R.id.passwordTextInput);
        passwordTextInputEdit = findViewById(R.id.passwordTextInputEdit);
        loginButton = findViewById(R.id.loginButton);
        feedbackText = findViewById(R.id.feedbackText);
        userDatabase = new String[][]{
                {"gianluca", "paperella"},
                {"filippo", "password"},
                {"giuseppe", "gennaro"}
        };
    }

    public int checkLogin(View v) {

        feedbackText.setText("");
        boolean emptyFlag = false;
        if (isEmpty(usernameTextInputEdit)) {
            usernameTextInputEdit.setError("Il campo non può essere vuoto");
            emptyFlag = true;
        }
        if(isEmpty(passwordTextInputEdit)){
            passwordTextInputEdit.setError("Il campo non può essere vuoto");
            emptyFlag = true;
        }
        if(emptyFlag){
            return 1;
        }

        String[] userCredentials = new String[]{usernameTextInputEdit.getText().toString(),
                passwordTextInputEdit.getText().toString()};

        int userID = checkCredentials(userCredentials);

        if (userID < 0) {
            passwordTextInputEdit.setError("Username o Password errati");
            Log.e("Cretentials", "Username or Password incorrect");
            return 1;
        } else {
            feedbackText.setText("Ciao " + userDatabase[userID][0]);
        }

        return 0;
    }

    private boolean isEmpty(TextInputEditText t) {
        return t.getText().toString().equals("");
    }

    private int checkCredentials(String[] data) {

        for (int i = 0; i < userDatabase.length; i++) {

            if (data[0].equals(userDatabase[i][0]) && data[1].equals(userDatabase[i][1])) {
                return i;
            }

        }
        return -1;
    }
}
