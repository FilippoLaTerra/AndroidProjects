package com.lock.jsonmanagementtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.entity.StringEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {

    Button updateButton, buttonConvert, buttonSend;
    TextView textViewJson;
    ProgressDialog pd;
    String json;
    List<Telepresence> telepresences = new ArrayList<>();
    final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent intent = new Intent(getApplicationContext(), GetDataAlarm.class);

                intent.setAction("getData.alarm");

                //this may throw an error as the context may not be defined, but it's a problem for future me
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, getCalendarDate(), pendingIntent);


            }
        });

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    telepresences = objectMapper.readValue(json, new TypeReference<List<Telepresence>>(){});
                    pd = new ProgressDialog(MainActivity.this);
                    pd.setMessage("count: " + telepresences.size());
                    pd.show();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {


                        /*try {
                            URL url = new URL ("https://teletransport.visionqub.it/knobots/63170c1e71a51800136c19d9/destinations");
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            con.setRequestMethod("POST");
                            con.setRequestProperty("Content-Type", "application/json");
                            con.setRequestProperty("Accept", "application/json");
                            con.setDoOutput(true);
                            String jsonInputString = "{\"locations\": [\"testLocation1\", \"testLocation2\", \"testLocation3\"]}";



                            try(OutputStream os = con.getOutputStream()) {
                                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                                os.write(input, 0, input.length);
                                Log.i("datsent", "true");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                        try  {
                            Log.i("SENDDATA","sending json");
                            HttpClient httpClient = HttpClientBuilder.create().build();

                            try {

                                HttpPost request = new HttpPost("https://teletransport.visionqub.it/knobots/63170c1e71a51800136c19d9/destinations");
                                StringEntity params = new StringEntity("{\"locations\": [\"testLocation1\", \"testLocation2\", \"testLocation3\"]}");
                                request.addHeader("content-type", "application/x-www-form-urlencoded");
                                request.setEntity(params);
                                HttpResponse response = httpClient.execute(request);
                                Log.i("SENDDATA",response.toString());
                            } catch (Exception ex) {
                                Log.e("SENDDATA","failed");
                                Log.e("SENDDATA",ex.toString());
                            } finally {
                                // @Deprecated httpClient.getConnectionManager().shutdown();
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();

            }
        });

    }

    public long getCalendarDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.SECOND, 10);

        return calendar.getTimeInMillis();
    }


    public void initViews(){

        updateButton = findViewById(R.id.updateButton);
        buttonConvert  = findViewById(R.id.buttonConvert);
        buttonSend = findViewById(R.id.buttonSend);
        textViewJson = findViewById(R.id.textViewJson);

    }

    public class TrustAllHostNameVerifier implements HostnameVerifier {

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }

    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}