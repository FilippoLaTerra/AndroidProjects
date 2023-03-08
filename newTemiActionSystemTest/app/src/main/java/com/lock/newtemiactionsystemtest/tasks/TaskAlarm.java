package com.lock.newtemiactionsystemtest.tasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class TaskAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int task_id = intent.getIntExtra("task_id", -1);
        byte[] taskBytes = intent.getByteArrayExtra("task");
        Task task = null;
        try {
            task = taskFromByteArray(taskBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("ALARM INFO", "Alarm for task " + task_id + " started");

        if (task != null)
            task.startTask();

    }

    private Task taskFromByteArray(byte[] byteArray) throws Exception{

        Task task = null;

        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            task = (Task) in.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return task;
    }

}
