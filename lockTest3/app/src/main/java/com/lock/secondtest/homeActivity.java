package com.lock.secondtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.tuya.smart.android.ble.api.LeScanSetting;
import com.tuya.smart.android.ble.api.ScanDeviceBean;
import com.tuya.smart.android.ble.api.ScanType;
import com.tuya.smart.android.ble.api.TyBleScanResponse;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.ConfigProductInfoBean;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback;
import com.tuya.smart.home.sdk.callback.ITuyaResultCallback;
import com.tuya.smart.optimus.lock.api.ITuyaBleLock;
import com.tuya.smart.optimus.lock.api.ITuyaLockManager;
import com.tuya.smart.optimus.lock.api.callback.ConnectListener;
import com.tuya.smart.optimus.sdk.TuyaOptimusSdk;
import com.tuya.smart.sdk.api.IBleActivatorListener;
import com.tuya.smart.sdk.api.ITuyaDataCallback;
import com.tuya.smart.sdk.bean.BleActivatorBean;
import com.tuya.smart.sdk.bean.DeviceBean;
import com.tuya.smart.sdk.optimus.lock.bean.ble.BLELockUser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class homeActivity extends AppCompatActivity {

    CardView cardViewDevices;

    Button buttonScanDevices, buttonUnlock, buttonListDevices;
    TextView textViewProductID, textViewDeviceID, textViewDeviceName;

    ITuyaBleLock tuyaLockDevice;
    BLELockUser testUser;

    ImageView imageViewDeviceImage;

    List<String> rooms = new ArrayList<>();
    List<ScanDeviceBean> beans = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonScanDevices = findViewById(R.id.buttonScanDevices);
        buttonUnlock = findViewById(R.id.buttonUnlock);

        textViewProductID = findViewById(R.id.textViewProductID);
        textViewDeviceID = findViewById(R.id.textViewDeviceID);
        textViewDeviceName = findViewById(R.id.textViewDeviceName);

        imageViewDeviceImage = findViewById(R.id.imageViewDeviceImage);

        cardViewDevices = findViewById(R.id.CardViewDevices);

        cardViewDevices.setClickable(false);
        cardViewDevices.setBackgroundColor(Color.LTGRAY);

        TuyaOptimusSdk.init(getApplicationContext());
            // Get the `TuyaLockManager` class.
        ITuyaLockManager tuyaLockManager = TuyaOptimusSdk.getManager(ITuyaLockManager.class);
            // Create the `ITuyaBleLock` class.
        tuyaLockDevice = tuyaLockManager.getBleLock("d44f560be7df21d7");

        LeScanSetting scanSetting = new LeScanSetting.Builder()
                .setTimeout(60000) // The duration of the scanning. Unit: milliseconds.
                .addScanType(ScanType.SINGLE) // `ScanType.SINGLE`: scans for Bluetooth LE devices.
                // `.addScanType(ScanType.SIG_MESH)`: scans for other types of devices.
                .build();

        rooms.add("dualLocker");

        createHome();

        tuyaLockDevice.getLockUsers(new ITuyaResultCallback<List<BLELockUser>>() {
            @Override
            public void onError(String code, String message) {
                Log.e("LOCKUSER", "get lock users failed: code = " + code + "  message = " + message);
            }

            @Override
            public void onSuccess(List<BLELockUser> user) {
                Log.i("LOCKUSER", "get lock users success: lockUserBean = " + user);
                testUser = user.get(0);
            }
        });

        cardViewDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BleActivatorBean bleActivatorBean = new BleActivatorBean();

// `mScanDeviceBean`: returned by `ScanDeviceBean` in the scanning result callback.
                bleActivatorBean.homeId = 123456; // homeId
                bleActivatorBean.address = beans.get(0).getAddress(); // The device IP address.
                bleActivatorBean.deviceType = beans.get(0).getDeviceType(); // The type of device.
                bleActivatorBean.uuid = beans.get(0).getUuid(); // The UUID.
                bleActivatorBean.productId = beans.get(0).getProductId(); // The product ID.
                bleActivatorBean.isShare = (beans.get(0).getFlag() > 2) & 0x01 == 1;// The flag that indicates whether the device is a shared one.

// Starts pairing.
                TuyaHomeSdk.getActivator().newBleActivator().startActivator(bleActivatorBean, new IBleActivatorListener() {
                    @Override
                    public void onSuccess(DeviceBean deviceBean) {
                        // The device is paired.
                        Log.i("DEVICEPAIRING", "device paired succesfully");
                    }

                    @Override
                    public void onFailure(int code, String msg, Object handle) {
                        // Failed to pair the device.
                        Log.i("DEVICEPAIRING", "device pairing failed");
                    }
                });

            }
        });

        buttonScanDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts scanning.

                TuyaHomeSdk.getBleOperator().startLeScan(scanSetting, new TyBleScanResponse() {
                    @Override
                    public void onResult(ScanDeviceBean bean) {
                        Log.e("BEAN", bean.toString());
                        Log.e("BLESCAN", "bleScan succesful");

                        Toast.makeText(homeActivity.this, "bleScan succesful", Toast.LENGTH_SHORT).show();

                        TuyaHomeSdk.getActivatorInstance().getActivatorDeviceInfo(
                                bean.getProductId(),
                                bean.getUuid(),
                                bean.getMac(),
                                new ITuyaDataCallback<ConfigProductInfoBean>() {
                                    @Override
                                    public void onSuccess(ConfigProductInfoBean result) {
                                        Log.e("datacallback", "Success");
                                        Toast.makeText(homeActivity.this, "dataCallback succesful", Toast.LENGTH_SHORT).show();


                                        beans.add(bean);
                                        textViewProductID.setText(bean.getProductId());
                                        textViewDeviceID.setText(bean.getUuid());
                                        //textViewDeviceName.setText(result.getIcon());



                                        new DownloadImageTask((ImageView) findViewById(R.id.imageViewDeviceImage))
                                                .execute(result.getIcon());


                                    }

                                    @Override
                                    public void onError(String errorCode, String errorMessage) {
                                        Log.e("datacallback", "Error");
                                        Toast.makeText(homeActivity.this, "dataCallback error", Toast.LENGTH_SHORT).show();
                                    }
                                });




                    }
                });

            }
        });

        buttonUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("LOCKCONNECTION", "isBLEConnected: " + tuyaLockDevice.isBLEConnected());

                if ( tuyaLockDevice.isBLEConnected() ){

                    tuyaLockDevice.connect(new ConnectListener() {
                        @Override
                        public void onStatusChanged(boolean online) {
                            Log.i("LOCKCONNECTION", "onStatusChanged  online: " + online);
                        }
                    });
                }

                //tuyaLockDevice.unlock(testUser.userId);
            }


        });

    }

    private void createHome(){


        TuyaHomeSdk.getHomeManagerInstance().createHome("defaultHome", 0, 0, "geoName", rooms, new ITuyaHomeResultCallback() {
            @Override
            public void onSuccess(HomeBean bean) {
                // do something
                Toast.makeText(homeActivity.this, "home created", Toast.LENGTH_SHORT).show();
                Log.e("Home", "Created");
            }
            @Override
            public void onError(String errorCode, String errorMsg) {
                // do something
                Toast.makeText(homeActivity.this, "home creation failed", Toast.LENGTH_SHORT).show();
                Log.e("Home", errorCode +" "+ errorMsg);
            }
        });

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }






}