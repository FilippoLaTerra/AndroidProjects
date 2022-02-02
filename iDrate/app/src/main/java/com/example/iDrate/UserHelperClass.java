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
    public int lifetimeWaterDrankInLiters;
    public int waterDrankTodayInCentiliters;

    public HashMap<String, String> waterInaDayLogs;

    public UserHelperClass(){

    }

    public UserHelperClass(String UID, String username, int weight, int age) {
        this.username = username;
        this.UID = UID;
        this.weightInKilograms = weight;
        this.age = age;
        this.currentPlantName = "ermenegilda";

        this.waterToDrinkInCentiliters = calculateWater();
        startNewDay();
        this.lifetimeWaterDrankInLiters = 0;

        this.waterInaDayLogs = new HashMap<>();
    }

    public void startNewDay(){

        try{
            this.waterInaDayLogs.put(getDateToString(), Integer.toString(waterDrankTodayInCentiliters));
        } catch(Exception e) {

        }

        this.waterDrankTodayInCentiliters = 0;

    }

    public void addToTodayWater(int centilitersDrank){
        this.waterDrankTodayInCentiliters += centilitersDrank;
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


