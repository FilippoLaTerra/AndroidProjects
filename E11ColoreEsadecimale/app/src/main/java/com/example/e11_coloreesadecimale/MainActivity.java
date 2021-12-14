package com.example.e11_coloreesadecimale;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextInputLayout hexValueTextInputLayout;
    TextInputEditText hexValueEditText;

    TextView hexValueTextView;

    ImageView hexColorImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hexValueTextInputLayout = findViewById(R.id.hexValueTextInputLayout);
        hexValueEditText = findViewById(R.id.hexValueEditText);
        hexValueTextView = findViewById(R.id.hexValueTextView);
        hexColorImageView = findViewById(R.id.hexColorImageView);

        hexValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeColor();
            }

            @Override
            public void afterTextChanged(Editable s) {
                changeColor();
            }
        });

    }


    public void changeColor() {

        String hexValue;

        if (hexValueEditText.getText().toString().equals("")) {

        } else {

            hexValue = hexValueEditText.getText().toString();

            try {
                hexColorImageView.setBackgroundColor(Color.parseColor(hexValue));
                hexValueTextView.setText(hexValue.toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                Log.e("Parse error", "cannot parse color");
            }

        }

    }

}