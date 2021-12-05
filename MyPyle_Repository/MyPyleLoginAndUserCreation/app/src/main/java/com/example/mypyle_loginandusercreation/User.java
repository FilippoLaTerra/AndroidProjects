package com.example.mypyle_loginandusercreation;

public class User {

    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;

    private int USER_ID;

    public User() {
        this.USER_ID = -1;
    }

    public User(String firstName, String lastName,String email, String password, int USER_ID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.USER_ID = USER_ID;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }
}
