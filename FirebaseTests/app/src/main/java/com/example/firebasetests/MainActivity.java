package com.example.firebasetests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("TestNode");
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String email = "testEmail@email.com";
        String password = "password123";

        auth.createUserWithEmailAndPassword(email, password);

        auth.getCurrentUser().getUid();

        /*UserHelperClass testUser = new UserHelperClass(0, "NomeUtente", 60, 18);


        testUser.addToTodayWater(20);
        testUser.addToTodayWater(20);
        testUser.addToTodayWater(20);

        testUser.startNewDay();


        myRef.child(testUser.Username).setValue(testUser);*/






    }



}