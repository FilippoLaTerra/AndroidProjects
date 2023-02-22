package com.lock.newtemiactionsystemtest.actions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lock.newtemiactionsystemtest.R;


import android.util.Log;

/**
 * @author Filippo La Terra Maggiore
 * @version 1.0
 * This action class handles the Robot Temi movement
 */
@JsonIgnoreProperties(value = { "actionName" })
public class MoveToLocationAction extends Action {

    protected String location;
    protected boolean backwards;
    protected int speed;
    protected String custom_fallback;

    public MoveToLocationAction(){
        super(R.integer.MoveToLocationAction);
    }

    public MoveToLocationAction(String location, Boolean backwards, Integer speed, String custom_fallback) {
        super(R.integer.MoveToLocationAction);

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
        super(R.integer.MoveToLocationAction);

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
        
        //temi SDK has stopMovement() as a function to stop all movement
        System.out.println(this.toString());
    }

    @Override
    protected void onComplete() {
        Log.i("ACTION", "move to action completed");
    }

    @Override
    protected void doPost() {

    }

    @Override
    protected void onError() {

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
