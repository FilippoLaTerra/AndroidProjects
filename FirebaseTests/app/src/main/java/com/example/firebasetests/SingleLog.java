package com.example.firebasetests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SingleLog {

    final Date LOG_TIME = Calendar.getInstance().getTime();
    private int waterDrankInCentiliters;
    protected String day;
    protected String month;
    protected String year;

    public SingleLog(int waterDrankInCentiliters) {

        this.day = new SimpleDateFormat("dd").format(LOG_TIME);
        this.month = new SimpleDateFormat("mm").format(LOG_TIME);
        this.year = new SimpleDateFormat("yyyy").format(LOG_TIME);

        this.waterDrankInCentiliters = waterDrankInCentiliters;
    }

    @Override
    public String toString() {
        return "DailyLog{" +
                "logTime=" + LOG_TIME +
                ", waterDrankInCentiliters=" + waterDrankInCentiliters +
                '}';
    }
}
