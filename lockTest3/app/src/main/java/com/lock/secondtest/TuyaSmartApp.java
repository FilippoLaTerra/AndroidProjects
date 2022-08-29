package com.lock.secondtest;

import android.app.Application;

import com.tuya.smart.home.sdk.TuyaHomeSdk;

public class TuyaSmartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TuyaHomeSdk.setDebugMode(true);
        TuyaHomeSdk.init(this);

    }
}