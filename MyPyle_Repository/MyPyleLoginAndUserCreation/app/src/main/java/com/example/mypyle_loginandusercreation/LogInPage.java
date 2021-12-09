package com.example.mypyle_loginandusercreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LogInPage extends AppCompatActivity {

    private TextInputLayout usernameTextInput;
    private TextInputEditText usernameTextInputEdit;
    private TextInputLayout passwordTextInput;
    private TextInputEditText passwordTextInputEdit;
    private Button loginButton;
    private TextView feedbackText;
    public Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        getSupportActionBar().hide();

        usernameTextInput = findViewById(R.id.usernameTextInput);
        usernameTextInputEdit = findViewById(R.id.usernameTextInputEdit);
        passwordTextInput = findViewById(R.id.passwordTextInput);
        passwordTextInputEdit = findViewById(R.id.passwordTextInputEdit);
        loginButton = findViewById(R.id.loginButton);
        feedbackText = findViewById(R.id.feedbackText);

        database = database.getInstance();

    }

    public void checkLogin(View v) {

        feedbackText.setText("");
        usernameTextInput.setError("");
        passwordTextInput.setError("");


        if (areFieldEmpty() == false) {

            String inputUsername = usernameTextInputEdit.getText().toString();
            String inputPassword = passwordTextInputEdit.getText().toString();


            User foundUser = checkCredentials(database, inputUsername, inputPassword);

            if (foundUser.getUSER_ID() < 0) {
                passwordTextInput.setError("Username o Password errati");
                Log.e("Cretentials", "Username or Password incorrect");

            } else {
                feedbackText.setText("Ciao " + foundUser.firstName);

                Log.i("Switch Activities", "starting mainMenu Page");
                Intent switchActivity = new Intent(this, MainMenu.class);
                startActivity(switchActivity);
                finish();
            }
        }

    }

    private boolean isEmpty(TextInputEditText t) {
        return t.getText().toString().equals("");
    }

    private User checkCredentials(Database database, String username, String password) {

        List<User> users = database.getUserDatabase();

        for (User user : users) {

            if ((user.firstName).equals(username) && (user.password).equals(password)) {
                return user;
            }
        }

        Log.e("User", "User not found, returning null User");
        return new User();
    }

    private boolean areFieldEmpty(){
        boolean emptyFlag = false;

        if (isEmpty(usernameTextInputEdit)) {
            usernameTextInput.setError("Il campo non può essere vuoto");
            emptyFlag = true;
        }

        if (isEmpty(passwordTextInputEdit)) {
            passwordTextInput.setError("Il campo non può essere vuoto");
            emptyFlag = true;
        }

        return emptyFlag;
    }
}
