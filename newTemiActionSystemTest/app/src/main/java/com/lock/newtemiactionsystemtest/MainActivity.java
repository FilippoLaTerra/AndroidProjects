package com.lock.newtemiactionsystemtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lock.newtemiactionsystemtest.actions.Action;
import com.lock.newtemiactionsystemtest.actions.MoveToLocationAction;
import com.lock.newtemiactionsystemtest.actions.SpeakMessageAction;
import com.lock.newtemiactionsystemtest.helpers.JsonHelper;
import com.lock.newtemiactionsystemtest.helpers.SettingsHelper;
import com.lock.newtemiactionsystemtest.tasks.Task;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button startTaskButton, planTaskButton, cancelPlanTaskButton, parseJSon, printSettings, changeSettings, resetSettings;
    TextView log;
    ArrayList<Action> actions = new ArrayList<>();
    Task testTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Intent intent = new Intent(this, APIService.class);

        actions.add(new MoveToLocationAction("testLocation", false, 3));
        actions.add(new SpeakMessageAction("test message"));
        testTask = new Task(0, actions);

        startTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TASK", "task started");
                testTask.startTask();

                startService(intent);
            }
        });

        planTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar currentTime = Calendar.getInstance();
                currentTime.setTimeInMillis(System.currentTimeMillis());

                currentTime.set(Calendar.SECOND, currentTime.get(Calendar.SECOND) + 10);

                testTask.programTask(currentTime.getTime());
            }
        });

        cancelPlanTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TASK", "task canceled");
                testTask.cancelTask();
            }
        });

        parseJSon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonHelper jsonHelper = new JsonHelper();
                try {
                    log.append(jsonHelper.jsonArraytest());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        printSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsHelper settingsHelper = new SettingsHelper();
                Log.i("SETTINGS_HELPER", settingsHelper.toString());
                log.append("" + settingsHelper);
            }
        });

        changeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsHelper settingsHelper = new SettingsHelper();

                settingsHelper.setDefaultFallbackLocation("mor'binda");
                settingsHelper.setDefaultSpeed(3);
                settingsHelper.setIsDefaultBackwards(true);
                settingsHelper.setDefaultTiltAngle(15);
                settingsHelper.setDefaultLanguage("EN");
                settingsHelper.setDefaultUnlockCode("1111");
                settingsHelper.setRobot_id("bobot");
                settingsHelper.setConnection_URL("magic_url.net");
                settingsHelper.applyChanges();
            }
        });

        resetSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsHelper settingsHelper = new SettingsHelper(getApplicationContext());
                settingsHelper.resetSettingsToDefault();
            }
        });

    }

    public void initViews(){
        log = findViewById(R.id.log);
        startTaskButton = findViewById(R.id.startTaskButton);
        planTaskButton = findViewById(R.id.planTaskButton);
        cancelPlanTaskButton = findViewById(R.id.cancelPlanTaskButton);
        parseJSon = findViewById(R.id.parseJSon);
        printSettings = findViewById(R.id.printSettings);
        changeSettings = findViewById(R.id.changeSettings);
        resetSettings = findViewById(R.id.resetSettings);
    }
}