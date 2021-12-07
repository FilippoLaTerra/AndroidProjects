package com.example.mypyle_loginandusercreation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Database implements Serializable {

    private static Database database = null;

    private List<User> userDatabase;
    protected int lastIdUsed;

    public static Database getInstance(){
        if(database == null){
            database = new Database();
        }
        return database;
    }

    private Database() {
        this.userDatabase = new ArrayList<>();
        lastIdUsed = 0;
    }

    public void addUserToDatabase(User user){

        userDatabase.add(user);
    }

    public int getLastIdUsed() {
        return lastIdUsed;
    }

    public List<User> getUserDatabase(){
        return userDatabase;
    }

    public int createID(){
        this.lastIdUsed++;
        return lastIdUsed;
    }
}
