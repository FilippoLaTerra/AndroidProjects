package com.example.e16_fotografia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_ID = 1;

    ImageView pictureViewerImageView;
    Button pictureButton;
    Bitmap image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pictureViewerImageView = findViewById(R.id.pictureViewerImageView);
        pictureButton = findViewById(R.id.pictureButton);

    }

    public void onClick(View v) {

        Intent openCamera = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(openCamera, CAMERA_ID);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        image = (Bitmap) data.getExtras().get("data");


        pictureViewerImageView.setImageBitmap(image);
        Log.i("Stauts", "picure updated");
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("image", image);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        image = savedInstanceState.getParcelable("image");
        pictureViewerImageView.setImageBitmap(image);
    }
}