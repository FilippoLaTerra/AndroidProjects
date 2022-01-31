package com.example.firebasetests;

import java.util.ArrayList;

public class UserHelperClass {

    private int ID;
    private String Username;
    private int weightInKilograms;
    private int age;
    private String currentPlantName;

    private int waterToDrinkInCentiliters;
    private int lifetimeWaterDrankInLiters;

    public ArrayList<DailyLog> userLogs;

    public UserHelperClass(int ID, String username, int weight, int age)  {
        Username = username;
        this.ID = ID;
        this.weightInKilograms = weight;
        this.age = age;

        waterToDrinkInCentiliters = 500;
        lifetimeWaterDrankInLiters = 0;

        userLogs = new ArrayList<DailyLog>();
    }

    public String getUsername() {
        return Username;
    }

    public int getID() {
        return ID;
    }



}
