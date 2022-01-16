package com.example.iDrate;

public class User {

    private String email;
    private int age;
    private int weightinKilograms;
    protected double waterToDrinkInLiters;
    protected double waterDrankInCentiliters;


    public User(String email, int age, int weightinKilograms) {
        this.email = email;
        this.age = age;
        this.weightinKilograms = weightinKilograms;
        this.waterToDrinkInLiters = calculateWater();
    }

    private double calculateWater() {

        waterToDrinkInLiters = weightinKilograms / 22.892;

        if (age <=10) {
            waterToDrinkInLiters *= .8;
        } else if (age > 10 && age <= 15){
            waterToDrinkInLiters *= .9;
        } else if (age > 15 && age <= 19){
            waterToDrinkInLiters *= .95;
        }

        return waterToDrinkInLiters;
    }

    public String getEmail() {
        return email;
    }
}
