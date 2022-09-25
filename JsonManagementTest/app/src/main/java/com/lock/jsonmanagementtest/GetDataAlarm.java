package com.lock.jsonmanagementtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class GetDataAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("DATA_ALARM", "alarm executed");
        new JsonTask().execute("https://teletransport.visionqub.it/knobots/63170c1e71a51800136c19d9/telepresences");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        intent = new Intent(context.getApplicationContext(), GetDataAlarm.class);

        intent.setAction("getData.alarm");

        //this may throw an error as the context may not be defined, but it's a problem for future me
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, getCalendarDate(), pendingIntent);

    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();



            Log.i("PREEXECUTE", "preparing for fetch");
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                    //json = (String) line;
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("POSTEXECUTE", "");
        }
    }

    public long getCalendarDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 20);

        return calendar.getTimeInMillis();
    }

}

