package com.lock.newtemiactionsystemtest;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.ServerSocket;

public class APIService extends Service {

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    private ServerSocket serversocket;

    private final class ServiceHandler extends Handler{
        public ServiceHandler(Looper looper){
            super(looper);
        }

        public void testService(){
            try {
                Thread.sleep(1000);
                Log.i("SERVICE", "Yep, i'm working");
                serversocket.accept();
            } catch(Exception e) {
                Thread.currentThread().interrupt();
            }
            //stopSelf();
        }


    }

    @Override
    public void onCreate(){
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    public int onStartCommand(Intent intent, int flags, int startId){

        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        try {
            serversocket = new ServerSocket(17, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serviceHandler.testService();

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "service brutally murdered", Toast.LENGTH_SHORT).show();
        try {
            serversocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
