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

    private ArrayList<SingleLog> dailyLogs;

    public DailyLog() {

        this.currentDate = Calendar.getInstance().getTime();
        this.day = new SimpleDateFormat("dd").format(currentDate);
        this.month = new SimpleDateFormat("mm").format(currentDate);
        this.year = new SimpleDateFormat("yyyy").format(currentDate);

        this.dailyLogs = new ArrayList<>();

    }

    public void addLog(SingleLog log){
        dailyLogs.add(log);
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
