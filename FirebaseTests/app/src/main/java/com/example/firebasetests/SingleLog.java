package com.example.firebasetests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SingleLog extends MonthlyLog {

    private Date logTime;
    private int waterDrankInCentiliters;
    protected Date currentDate;
    protected String day;
    protected String month;
    protected String year;

    public SingleLog(int waterDrankInCentiliters) {
        this.logTime = Calendar.getInstance().getTime();
        this.currentDate = Calendar.getInstance().getTime();
        this.day = new SimpleDateFormat("dd").format(currentDate);
        this.month = new SimpleDateFormat("mm").format(currentDate);
        this.year = new SimpleDateFormat("yyyy").format(currentDate);
        this.waterDrankInCentiliters = waterDrankInCentiliters;
    }

    @Override
    public String toString() {
        return "DailyLog{" +
                "logTime=" + logTime +
                ", waterDrankInCentiliters=" + waterDrankInCentiliters +
                '}';
    }
}
