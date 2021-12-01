package com.example.e02_calcolatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    EditText operator1;
    EditText operator2;
    Button sumButton;
    Button subButton;
    TextView messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operator1 = findViewById(R.id.operator1);
        operator2 = findViewById(R.id.operator2);
        sumButton = findViewById(R.id.sumButton);
        subButton = findViewById(R.id.sumButton);
        messageText = findViewById(R.id.messageText);

    }

    public int mostraRisultato(View v) {

        int viewId = v.getId();
        int result = 0;
        boolean emptyFlag = false;

        messageText.setText("");

        if (operator1.getText().toString().equals("")) {
            operator1.setError("Il campo non può essere vuoto");
            Log.i("nullException", "input 1 is null");
            emptyFlag = true;
        }
        if (operator2.getText().toString().equals("")) {
            operator2.setError("Il campo non può essere vuoto");
            Log.i("nullException", "input 2 is null");
            emptyFlag = true;
        }
        if (emptyFlag){
            return 1;
        }


        int n1 = Integer.parseInt(operator1.getText().toString());
        int n2 = Integer.parseInt(operator2.getText().toString());

        Log.i("button pressed", "pressed" + findViewById(v.getId()));

        switch (viewId) {

            case R.id.sumButton:
                result = n1 + n2;
                break;
            case R.id.subButton:
                result = n1 - n2;
                break;
        }

        messageText.setText(String.format("Il risultato é : %d", result));
        return 0;

    }

}