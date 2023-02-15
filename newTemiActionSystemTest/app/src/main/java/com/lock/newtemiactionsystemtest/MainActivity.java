package com.lock.newtemiactionsystemtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lock.newtemiactionsystemtest.actions.Action;
import com.lock.newtemiactionsystemtest.actions.MoveToLocationAction;
import com.lock.newtemiactionsystemtest.actions.SpeakMessageAction;
import com.lock.newtemiactionsystemtest.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button startTaskButton, planTaskButton, cancelPlanTaskButton;
    TextView log;
    ArrayList<Action> actions = new ArrayList<>();
    Task testTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        actions.add(new MoveToLocationAction("testLocation", false, 3));
        actions.add(new SpeakMessageAction("test message"));
        testTask = new Task(0, actions);

        startTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TASK", "task started");
                testTask.startTask();

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

    public void initViews(){
        log = findViewById(R.id.log);
        startTaskButton = findViewById(R.id.startTaskButton);
        planTaskButton = findViewById(R.id.planTaskButton);
        cancelPlanTaskButton = findViewById(R.id.cancelPlanTaskButton);
    }
}