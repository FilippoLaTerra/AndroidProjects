package com.lock.secondtest;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.tuya.smart.android.ble.api.LeScanSetting;
import com.tuya.smart.android.ble.api.ScanDeviceBean;
import com.tuya.smart.android.ble.api.ScanType;
import com.tuya.smart.android.ble.api.TyBleScanResponse;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.sdk.api.INeedLoginListener;

public class TuyaSmartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TuyaHomeSdk.setDebugMode(true);
        TuyaHomeSdk.init(this);

    }
}