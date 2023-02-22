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

/**
 * @author Filippo La Terra Maggiore
 * @version 1.0
 * The task class consists of a series of actions to be executed on a dedicated thread,
 * it allows for the programming of said task on a specified date and time
 * and the ability to start, stop, pause and resume the task
 */

public class Task implements Serializable {


    public int task_id;
    public String taskName;
    protected ArrayList<Action> actions;
    protected Date programmedDate;
    private Action currentAction;

    /**
     * The thread iterates through the available actions and starts them
     */
    private final Thread taskTread =  new Thread(){
        public void run(){
            for (Action action : actions) {
                    currentAction = action;
                    action.start();
                    if(interrupted())
                        return;
            }
            cancelTask();
        }
    };

    public Task(){}

    public Task(int task_id, ArrayList<Action> actions) {
        this.task_id = task_id;
        this.actions = actions;
    }

    public Task(int task_id, String taskName, ArrayList<Action> actions) {
        this.task_id = task_id;
        this.taskName = taskName;
        this.actions = actions;
    }

    public Task(int task_id, ArrayList<Action> actions, Date programmedDate) {
        this.task_id = task_id;
        this.actions = actions;
        this.programmedDate = programmedDate;
    }

    public Task(int task_id, String taskName, ArrayList<Action> actions, Date programmedDate) {
        this.task_id = task_id;
        this.taskName = taskName;
        this.actions = actions;
        this.programmedDate = programmedDate;
    }

    /**
     * This method sets a StartAlarm for the specified date
     * @param date the date and time at which the task must be started
     */
    public void programTask(Date date){
        Log.i("TASK","Programming task for " + date);

        Context context = ContextGetter.getContext();

        boolean alreadyProgrammed = PendingIntent.getBroadcast(context, this.task_id, new Intent("task.alarm"), PendingIntent.FLAG_NO_CREATE) != null;

        if(!alreadyProgrammed){
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, TaskAlarm.class);
            intent.setAction("task.alarm");

            //puts the task id and the task itself into the alarm intent
            byte[] taskByteArray = toByteArray();

            if(taskByteArray != null){
                intent.putExtra("task_id", this.task_id);
                intent.putExtra("task", toByteArray());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task_id, intent, PendingIntent.FLAG_ONE_SHOT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
            } else {
                Log.e("TASK_PROGRAMMING", "Task with id " + task_id + " generated a null byte array");
            }

        } else {
            Log.e("TASK", "Task with id " + task_id + " has already been programmed");
        }
    }

    /**
     * Starts the task
     */
    public void startTask(){
        if (taskTread.getState() == Thread.State.NEW)
        {
            Log.i("TASK_THREAD", "Task with id " + task_id + " thread started");
            taskTread.start();
        } else {
            Log.i("TASK_THREAD", "Task with id " + task_id + " thread already running");
        }
    }

    /**
     * Pauses the currently running task
     * @throws InterruptedException
     */
    public void pauseTask() throws InterruptedException {
        Log.i("TASK_THREAD", "Task " + task_id + " thread paused");
        taskTread.wait();
    }

    /**
     * Starts the previously paused task
     */
    public void resumeTask(){
        Log.i("TASK_THREAD", "Task " + task_id + " thread resumed");
        taskTread.notify();
    }

    /**
     *  Safely cancels the task, interrupting the current action and task thread
     *  if the task was programmed, it will cancel the corresponding alarm
     */
    public void cancelTask(){

        try {
            currentAction.stop();
            taskTread.interrupt();
        } catch (Exception e) {
            Log.e("TASK_CANCEL", "Task with id " + task_id + " failed to cancel, is there any task running? ");
        }

        try {
            Context context = ContextGetter.getContext();
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, TaskAlarm.class);
            intent.setAction("task.alarm");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task_id, intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.cancel(pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Customizable method to handle errors (WIP)
     */
    public void onError(){
        //personalized error handling
    }

    /**
     * converts the task object into a byte[]
     * @return
     */

    private byte[] toByteArray(){
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
