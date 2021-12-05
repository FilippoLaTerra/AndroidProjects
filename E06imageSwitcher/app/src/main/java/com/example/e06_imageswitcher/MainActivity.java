package com.example.e06_imageswitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView nameFiletext;
    ImageView imageDisplay;
    Button nextButton;
    Button previousButton;
    int[] images;
    int currentImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameFiletext = findViewById(R.id.nameFileText);
        imageDisplay = findViewById(R.id.imageDisplay);
        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);

        currentImage = 0;
        images = new int[]{
                R.drawable.enrico_surprised,
                R.drawable.meroi_dread,
                R.drawable.meroi_fire,
                R.drawable.strazz_approves,
        };

        imageDisplay.setImageResource(images[currentImage]);

    }


    public void changeImage(View v) {

        int buttonID = v.getId();


        switch (buttonID) {

            case R.id.nextButton:
                checkIndex(currentImage++);
                imageDisplay.setImageResource(images[currentImage]);
                break;
            case R.id.previousButton:
                checkIndex(currentImage--);
                imageDisplay.setImageResource(images[currentImage]);
                break;
        }


    }

    public int checkIndex(int index) {

        if (index > images.length - 1) {
            return 0;
        } else if (index < 0) {
            return (images.length - 1);
        }
        return index;
    }

}