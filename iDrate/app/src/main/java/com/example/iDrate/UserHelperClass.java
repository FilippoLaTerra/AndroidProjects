package com.example.iDrate;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class UserHelperClass {

    public String UID;
    public String username;
    public int weightInKilograms;
    public int age;
    public String currentPlantName;

    public int waterToDrinkInCentiliters;
    public int lifetimeWaterDrankInCentiliters;
    public int waterDrankTodayInCentiliters;

    public HashMap<String, String> waterInaDayLogs;
    public String latestLogDate;

    public UserHelperClass(){

    }


    public UserHelperClass(String UID, String username, int weight, int age) {
        this.username = username;
        this.UID = UID;
        this.weightInKilograms = weight;
        this.age = age;
        this.currentPlantName = "ermenegilda";

        this.waterToDrinkInCentiliters = calculateWater();
        this.lifetimeWaterDrankInCentiliters = 0;

        this.latestLogDate = getDateToString();
        this.waterInaDayLogs = new HashMap<>();
    }



    private int calculateWater() {

        waterToDrinkInCentiliters = (int) (weightInKilograms / 22.892)*100;

        if (age <=10) {
            waterToDrinkInCentiliters *= .8;
        } else if (age > 10 && age <= 15){
            waterToDrinkInCentiliters *= .9;
        } else if (age > 15 && age <= 19){
            waterToDrinkInCentiliters *= .95;
        }

        return waterToDrinkInCentiliters;
    }

    public String getDateToString(){

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(today);
        return date;

    }

}


