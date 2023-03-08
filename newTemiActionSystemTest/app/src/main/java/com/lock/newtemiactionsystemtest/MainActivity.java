package com.lock.newtemiactionsystemtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lock.newtemiactionsystemtest.actions.Action;
import com.lock.newtemiactionsystemtest.actions.MoveToLocationAction;
import com.lock.newtemiactionsystemtest.actions.SpeakMessageAction;
import com.lock.newtemiactionsystemtest.actions.testAction;
import com.lock.newtemiactionsystemtest.helpers.JsonHelper;
import com.lock.newtemiactionsystemtest.helpers.SettingsHelper;
import com.lock.newtemiactionsystemtest.tasks.Task;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button tabButtonTask, tabButtonJson, tabButtonNetwork, tabButtonOther;
    Button startTaskButton, planTaskButton;
    Button cancelPlanTaskButton, parseJSon;
    Button printSettings, changeSettings, resetSettings;

    ConstraintLayout taskButtons, networkButtons, otherButtons;

    Intent intent;
    TextView log;
    ArrayList<Action> actions = new ArrayList<>();
    Task testTask;
    ImageView imageviewTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initViews();
        intent = new Intent(this, APIService.class);
        /*actions.add(new MoveToLocationAction("testLocation", false, 3));
        actions.add(new SpeakMessageAction("test message"));*/
        actions.add(new testAction(imageviewTest, getApplicationContext()));
        testTask = new Task(0, actions);

        setUsability(taskButtons, true);
        setUsability(networkButtons, false);
        setUsability(otherButtons, false);

        setTabButtonListeners();
        setTaskButtonsListeners();
        setOtherButtonsListeners();
    }


    public void setUsability(ConstraintLayout layout, boolean isVisible){
        layout.setEnabled(isVisible);
        if(isVisible){
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.GONE);
        }
    }

    public void setTabButtonListeners(){

        tabButtonTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUsability(taskButtons, true);
                setUsability(networkButtons, false);
                setUsability(otherButtons, false);

                /*setUsability(taskButtons, true);
                setUsability(jsonButtons, false);
                setUsability(networkButtons, false);
                setUsability(otherButtons, false);*/
            }
        });

        tabButtonNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUsability(taskButtons, false);
                setUsability(networkButtons, true);
                setUsability(otherButtons, false);

                /*setUsability(taskButtons, true);
                setUsability(jsonButtons, false);
                setUsability(networkButtons, false);
                setUsability(otherButtons, false);*/
            }
        });

        tabButtonOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUsability(taskButtons, false);
                setUsability(networkButtons, false);
                setUsability(otherButtons, true);

            }
        });
    }

    public void setTaskButtonsListeners(){
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

    }

    public void setOtherButtonsListeners(){
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

        tabButtonTask = findViewById(R.id.tabButtonTask);
        tabButtonJson = findViewById(R.id.tabButtonJson);
        tabButtonNetwork = findViewById(R.id.tabButtonNetwork);
        tabButtonOther = findViewById(R.id.tabButtonOther);

        startTaskButton = findViewById(R.id.startTaskButton);
        planTaskButton = findViewById(R.id.planTaskButton);
        cancelPlanTaskButton = findViewById(R.id.cancelPlanTaskButton);
        parseJSon = findViewById(R.id.parseJSon);
        printSettings = findViewById(R.id.printSettings);
        changeSettings = findViewById(R.id.changeSettings);
        resetSettings = findViewById(R.id.resetSettings);

        imageviewTest = findViewById(R.id.imageviewTest);
        taskButtons = findViewById(R.id.taskButtons);
        networkButtons = findViewById(R.id.networkButtons);
        otherButtons = findViewById(R.id.otherButtons);

        /*taskButtons.add(startTaskButton);
        taskButtons.add(planTaskButton);
        taskButtons.add(cancelPlanTaskButton);
        //networkButtons.add();
        otherButtons.add(changeSettings);
        otherButtons.add(resetSettings);
        otherButtons.add(printSettings);
        otherButtons.add(parseJSon);*/

    }
}