package com.example.firebasetests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("TestNode");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserHelperClass testUser = new UserHelperClass(0, "NomeUtente", 60, 18);

        myRef.child(testUser.getUsername()).setValue(testUser);

        SingleLog testLog = new SingleLog(20);
        DailyLog tesDailyLog = new DailyLog();

        testUser.userLogs.add(tesDailyLog);
        testUser.userLogs.get(0).addLog(testLog);

        myRef.child(testUser.getUsername()).setValue(testUser);


    }



}