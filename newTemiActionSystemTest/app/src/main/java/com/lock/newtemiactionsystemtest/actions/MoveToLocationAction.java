package com.lock.newtemiactionsystemtest.actions;

import android.util.Log;

public class MoveToLocationAction extends Action {

    protected String location;
    protected boolean backwards;
    protected int speed;
    protected String custom_fallback;

    public MoveToLocationAction(String location, Boolean backwards, Integer speed, String custom_fallback) {
        super(101);

        if(location.equals("") || location == null){
            //throws error
        } else {
            this.location = location;
        }

        if(backwards == null){
            this.backwards = false; //should get default parameter from settings
        } else {
            this.backwards = backwards;
        }

        if(speed == null){
            this.speed = 2; //should get default parameter from settings
        } else {
            this.speed = speed;
        }

        if(custom_fallback.equals("") || custom_fallback == null){
            //throws error
        }
    }

    public MoveToLocationAction(String location, Boolean backwards, Integer speed) {
        super(101);

        if(location.equals("") || location == null){
            //throws error
        } else {
            this.location = location;
        }

        if(backwards == null){
            this.backwards = false; //should get default parameter from settings
        } else {
            this.backwards = backwards;
        }

        if(speed == null){
            this.speed = 2; //should get default parameter from settings
        } else {
            this.speed = speed;
        }

    }

    @Override
    public void start() {
        //move robot to location, speed, backwards
        Log.i("ACTION", "move to action started");
        System.out.println(this.toString());
        onComplete();

    }

    @Override
    public void stop() {
        System.out.println(this.toString());
    }

    @Override
    protected void onComplete() {
        Log.i("ACTION", "move to action completed");
    }

    @Override
    protected void doPost() {

    }

    public String getLocation() { return location; }

    public boolean isBackwards() { return backwards; }

    public int getSpeed() { return speed; }

    public String getCustom_fallback() { return custom_fallback; }

    @Override
    public String toString() {
        return "MoveToLocationAction{" +
                "action_id=" + action_id +
                ", location='" + location + '\'' +
                ", backwards=" + backwards +
                ", speed=" + speed +
                ", custom_fallback='" + custom_fallback + '\'' +
                '}';
    }
}
