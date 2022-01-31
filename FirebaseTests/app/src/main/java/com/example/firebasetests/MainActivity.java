package com.example.firebasetests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserHelperClass testUser = new UserHelperClass(0, "NomeUtente", 60, 18);



        testUser.addNewDailyLog();

        SingleLog testLog = new SingleLog(14);

        testUser.updateDailyLog(testLog);

        Map<String, Object> users = new HashMap<>();
        users.put(testUser.getID(), testUser);



        db.collection("users")
                    .add(users);



    }



}