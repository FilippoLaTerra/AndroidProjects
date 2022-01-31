package com.example.firebasetests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("TestNode");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserHelperClass testUser = new UserHelperClass(0, "NomeUtente", 60, 18);


        testUser.addToTodayWater(20);
        testUser.addToTodayWater(20);
        testUser.addToTodayWater(20);

        testUser.startNewDay();


        myRef.child(testUser.Username).setValue(testUser);






    }



}