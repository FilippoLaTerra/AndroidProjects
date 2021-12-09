package com.example.e09_onclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView feedbackText;

    Button buttonMetodoA;
    Button buttonMetodoB;
    Button buttonMetodoC;
    Button buttonMetodoD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        feedbackText = findViewById(R.id.feedbackText);
        buttonMetodoA = findViewById(R.id.buttonMetodoA);
        buttonMetodoB = findViewById(R.id.buttonMetodoB);
        buttonMetodoC = findViewById(R.id.buttonMetodoC);
        buttonMetodoD = findViewById(R.id.buttonMetodoD);

        buttonMetodoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackText.setText("Premuto metodo B");
            }
        });

        buttonMetodoC.setOnClickListener(gestore);

        buttonMetodoD.setOnClickListener(this);

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonMetodoA:
                feedbackText.setText("Premuto metodo A");
                break;
            case R.id.buttonMetodoD:
                feedbackText.setText("Premuto metodo D");
                break;
        }


    }

    private View.OnClickListener gestore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonMetodoC:
                    feedbackText.setText("Premuto metodo C");
                    break;
            }
        }
    };

}