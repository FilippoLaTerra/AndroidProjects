package com.example.firebasetests;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class UserHelperClass {

    public int ID;
    public String Username;
    public int weightInKilograms;
    public int age;
    public String currentPlantName;

    public int waterToDrinkInCentiliters;
    public int lifetimeWaterDrankInLiters;
    public int waterDrankTodayInCentiliters;

    public HashMap<String, String> waterInaDayLogs;

    public UserHelperClass(int ID, String username, int weight, int age) {
        this.Username = username;
        this.ID = ID;
        this.weightInKilograms = weight;
        this.age = age;
        this.currentPlantName = "ermenegilda";

        this.waterToDrinkInCentiliters = 500;
        this.lifetimeWaterDrankInLiters = 0;

        this.waterInaDayLogs = new HashMap<>();
    }

    public void startNewDay(){

        this.waterInaDayLogs.put(getDateToString(), Integer.toString(waterDrankTodayInCentiliters));
        this.waterDrankTodayInCentiliters = 0;

    }

    public void addToTodayWater(int centilitersDrank){
        this.waterDrankTodayInCentiliters += centilitersDrank;
    }


    public String getDateToString(){

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(today);
        return date;

    }


}


