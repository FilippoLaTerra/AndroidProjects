package com.example.firebasetests;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class UserHelperClass {

    private int ID;
    private String Username;
    private int weightInKilograms;
    private int age;
    private String currentPlantName;

    private int waterToDrinkInCentiliters;
    private int lifetimeWaterDrankInLiters;

    public HashMap<String, DailyLog> userLogs;

    public UserHelperClass(int ID, String username, int weight, int age)  {
        Username = username;
        this.ID = ID;
        this.weightInKilograms = weight;
        this.age = age;

        waterToDrinkInCentiliters = 500;
        lifetimeWaterDrankInLiters = 0;

        userLogs = new HashMap<>();
    }

    public void addNewDailyLog(){

        DailyLog todayLog = new DailyLog();

        userLogs.put(todayLog.getDateString(), todayLog);

    }

    public void updateDailyLog(SingleLog log){

        userLogs.get("31-01-2022").addLog(log);

    }





    public String getID() {
        return "" + ID;
    }



}
