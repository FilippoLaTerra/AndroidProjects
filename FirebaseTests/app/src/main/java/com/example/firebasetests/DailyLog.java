package com.example.firebasetests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DailyLog{

    protected Date currentDate;
    protected String day;
    protected String month;
    protected String year;

    protected SingleLog[] dailyLogs ;

    public DailyLog() {

        this.currentDate = Calendar.getInstance().getTime();
        this.day = new SimpleDateFormat("dd").format(currentDate);
        this.month = new SimpleDateFormat("MM").format(currentDate);
        this.year = new SimpleDateFormat("yyyy").format(currentDate);

        this.dailyLogs = new SingleLog[0];

    }

    public void addLog(SingleLog log){

        SingleLog[] bufferArray = new SingleLog[dailyLogs.length+1];

        bufferArray[0] = log;

        for (int i = 1; i < dailyLogs.length + 1; i++){
            bufferArray[i] = dailyLogs[i];
        }

        this.dailyLogs = bufferArray;

    }

    public String getDateString(){
        return day +"-"+ month +"-"+ year;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
