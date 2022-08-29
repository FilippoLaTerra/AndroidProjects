package com.lock.secondtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tuya.smart.android.ble.api.LeScanSetting;
import com.tuya.smart.android.ble.api.ScanDeviceBean;
import com.tuya.smart.android.ble.api.ScanType;
import com.tuya.smart.android.ble.api.TyBleScanResponse;
import com.tuya.smart.android.user.api.ILoginCallback;
import com.tuya.smart.android.user.api.IRegisterCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.ConfigProductInfoBean;
import com.tuya.smart.sdk.api.IResultCallback;
import com.tuya.smart.sdk.api.ITuyaDataCallback;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    EditText edEmail, edPassword, edCountyCode;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startActivity(new Intent(getApplicationContext(), homeActivity.class));

        finish();

        btnLogin = findViewById(R.id.buttonGetCode);
        btnRegister = findViewById(R.id.buttonRegister);
        edPassword = findViewById(R.id.editTextPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TuyaHomeSdk.getUserInstance().sendVerifyCodeWithUserName("Filippo@laterramaggiore.it", "", "39", 1, new IResultCallback() {
                    @Override
                    public void onError(String code, String error) {
                        Toast.makeText(MainActivity.this, "SENDCODE FAILED", Toast.LENGTH_SHORT).show();
                        Log.e("SENDCODE", "ERROR: " + code + " " + error);
                    }

                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "SENDCODE succesful", Toast.LENGTH_SHORT).show();
                        Log.e("SENDCODE", "code sent");
                    }
                });

            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = edPassword.getText().toString();

                TuyaHomeSdk.getUserInstance().checkCodeWithUserName("testUserHardcoded", "String region", "39", code, 1, new IResultCallback() {
                    @Override
                    public void onError(String code, String error) {

                    }

                    @Override
                    public void onSuccess() {

                    }
                });


                TuyaHomeSdk.getUserInstance().registerAccountWithEmail("39", "Filippo@laterramaggiore.it", "test1234", code, new IRegisterCallback() {
                    @Override
                    public void onSuccess(User user) {

                    }

                    @Override
                    public void onError(String code, String error) {

                    }
                });

            }
        });


    }



}
