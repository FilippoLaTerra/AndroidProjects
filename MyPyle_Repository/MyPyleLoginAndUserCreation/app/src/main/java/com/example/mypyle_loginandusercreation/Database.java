package com.example.mypyle_loginandusercreation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {

    private List<User> userDatabase;
    protected int lastIdUsed;

    public Database() {
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
