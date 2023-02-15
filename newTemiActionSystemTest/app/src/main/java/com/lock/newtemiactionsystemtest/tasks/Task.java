package com.lock.newtemiactionsystemtest.tasks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lock.newtemiactionsystemtest.ContextGetter;
import com.lock.newtemiactionsystemtest.actions.Action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task implements Serializable {

    //TODO -> align attributes with json format: missing taskName and programmed
        // consequently add the needed contructors
    public int task_id;
    protected ArrayList<Action> actions;
    private Action currentAction;

    public Task(int task_id, ArrayList<Action> actions) {
        this.task_id = task_id;
        this.actions = actions;
    }

    public void programTask(Date date){
        Log.i("TASK","Programming task for " + date);

        //remember to put a check if the alarm is already up
        Context context = ContextGetter.getContext();

        boolean alreadyProgrammed = PendingIntent.getBroadcast(context, this.task_id, new Intent("task.alarm"), PendingIntent.FLAG_NO_CREATE) != null;

        if(!alreadyProgrammed){
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, TaskAlarm.class);
            intent.setAction("task.alarm");

            //puts the task id and the task itself into the alarm intent
            intent.putExtra("task_id", this.task_id);
            intent.putExtra("task", toByteArray());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task_id, intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
        } else {
            Log.e("TASK", "Task " + task_id + " has already been programmed");
        }


    }

    public void startTask(){
        for (Action action : actions) {
            currentAction = action;
            action.start();
        }
    }

    public void pauseTask(){
        currentAction.stop();
    }

    public void resumeTask(){

    }

    public void cancelTask(){

        try{
            Context context = ContextGetter.getContext();
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, TaskAlarm.class);
            intent.setAction("task.alarm");

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task_id, intent, PendingIntent.FLAG_ONE_SHOT);

            alarmManager.cancel(pendingIntent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onError(){

    }

    //converts the task object into a byte[]
    public byte[] toByteArray(){
        byte[] byteArray = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(new Task(this.task_id, this.actions));
            out.flush();
            byteArray = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return byteArray;
    }

}
