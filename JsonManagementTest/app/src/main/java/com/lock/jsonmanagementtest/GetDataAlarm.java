package com.lock.jsonmanagementtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GetDataAlarm extends BroadcastReceiver {

    ProgressDialog pd;
    String json;
    List<Telepresence> telepresences = new ArrayList<>();
    final ObjectMapper objectMapper = new ObjectMapper();

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
                    json = (String) line;
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

            try {
json = "[{\"_id\":\"633ac13342c21e00149c9528\",\"robot\":\"63170c1e71a51800136c19d9\",\"user\":\"632c024ca1c1d10013b80372\",\"who\":\"filippo\",\"where\":\"testLocation\",\"whom\":\"Roberto\",\"email\":\"test@email.com\",\"when\":\"2022-10-03T13:38:00.000Z\",\"duration\":0,\"zoom\":\"75357393875\",\"start\":\"https://us04web.zoom.us/s/75357393875?zak=eyJ0eXAiOiJKV1QiLCJzdiI6IjAwMDAwMSIsInptX3NrbSI6InptX28ybSIsImFsZyI6IkhTMjU2In0.eyJhdWQiOiJjbGllbnRzbSIsInVpZCI6Ii1TaFNYX1hMUmgycEQ5dUlpRE5Pd2ciLCJpc3MiOiJ3ZWIiLCJzayI6IjM3NjQ3MzY4OTUyMzU2NzExOTkiLCJzdHkiOjEsIndjZCI6InVzMDQiLCJjbHQiOjAsIm1udW0iOiI3NTM1NzM5Mzg3NSIsImV4cCI6MTY2NDgwMTEwOSwiaWF0IjoxNjY0NzkzOTA5LCJhaWQiOiJlU2tQaDdwdlRPQ0ZrM2ZqYjNHU2VBIiwiY2lkIjoiIn0.mbUxSQT4ePWmutWIhc0dQVvdiBwj9ldmn5f_PTNKXEQ\",\"join\":\"https://us04web.zoom.us/j/75357393875?pwd=oY6Qd3uKaDeDw87fwFCkoySLSoagkn.1\",\"__v\":0},{\"_id\":\"633ae4a28079d200137994ff\",\"robot\":\"63170c1e71a51800136c19d9\",\"user\":\"632c024ca1c1d10013b80372\",\"who\":\"filippo\",\"where\":\"testroom\",\"whom\":\"roberto\",\"email\":\"test@email.it\",\"when\":\"2022-10-03T14:32:00.000Z\",\"duration\":0,\"zoom\":\"74398708692\",\"start\":\"https://us04web.zoom.us/s/74398708692?zak=eyJ0eXAiOiJKV1QiLCJzdiI6IjAwMDAwMSIsInptX3NrbSI6InptX28ybSIsImFsZyI6IkhTMjU2In0.eyJhdWQiOiJjbGllbnRzbSIsInVpZCI6Ii1TaFNYX1hMUmgycEQ5dUlpRE5Pd2ciLCJpc3MiOiJ3ZWIiLCJzayI6IjM3NjQ3MzY4OTUyMzU2NzExOTkiLCJzdHkiOjEsIndjZCI6InVzMDQiLCJjbHQiOjAsIm1udW0iOiI3NDM5ODcwODY5MiIsImV4cCI6MTY2NDgxMTIwMSwiaWF0IjoxNjY0ODA0MDAxLCJhaWQiOiJlU2tQaDdwdlRPQ0ZrM2ZqYjNHU2VBIiwiY2lkIjoiIn0.e5QwwXwVVSUSQ8R3f70eaVIt-gY0wqzdeG2uAXn8SKw\",\"join\":\"https://us04web.zoom.us/j/74398708692?pwd=gLr3uA8r0efE7iVB0uzTRrVTnjt7s5.1\",\"__v\":0}]";

                objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                telepresences = objectMapper.readValue(json, new TypeReference<List<Telepresence>>(){});
                telepresences.size();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public long getCalendarDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 20);

        return calendar.getTimeInMillis();
    }

}

