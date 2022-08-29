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
import com.tuya.smart.android.ble.builder.BleConnectBuilder;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.device.api.IPropertyCallback;
import com.tuya.smart.android.user.api.ILoginCallback;
import com.tuya.smart.android.user.api.ILogoutCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.ConfigProductInfoBean;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.bean.MemberBean;
import com.tuya.smart.home.sdk.bean.MemberWrapperBean;
import com.tuya.smart.home.sdk.bean.RoomBean;
import com.tuya.smart.home.sdk.callback.ITuyaGetMemberListCallback;
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback;
import com.tuya.smart.home.sdk.callback.ITuyaResultCallback;
import com.tuya.smart.home.sdk.callback.ITuyaRoomResultCallback;
import com.tuya.smart.optimus.lock.api.ITuyaBleLock;
import com.tuya.smart.optimus.lock.api.ITuyaLockManager;
import com.tuya.smart.optimus.lock.api.callback.ConnectListener;
import com.tuya.smart.optimus.sdk.TuyaOptimusSdk;

import com.tuya.smart.sdk.api.IBleActivatorListener;
import com.tuya.smart.sdk.api.IDevListener;
import com.tuya.smart.sdk.api.IResultCallback;
import com.tuya.smart.sdk.api.ITuyaDataCallback;

import com.tuya.smart.sdk.api.ITuyaDevice;
import com.tuya.smart.sdk.bean.BleActivatorBean;
import com.tuya.smart.sdk.bean.DeviceBean;
import com.tuya.smart.sdk.optimus.lock.bean.ble.BLELockUser;
import com.tuya.smart.sdk.optimus.lock.manager.TuyaDeviceWrapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class homeActivity extends AppCompatActivity {

    // IDS FOUND: bf50c09zc4tivlad , d44f560be7df21d7

    CardView cardViewDevices;

    Button buttonScanDevices, buttonUnlock, buttonCheckOnline, buttonConnectTo;
    TextView textViewProductID, textViewDeviceID, textViewDeviceName;

    ITuyaBleLock tuyaLockDevice;
    BLELockUser testUser;

    HomeBean currentHome;
    User currentUser;

    ImageView imageViewDeviceImage;

    List<String> rooms = new ArrayList<>();
    List<ScanDeviceBean> beans = new ArrayList<>();
    ITuyaDevice mDevice;

    boolean isScanning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonConnectTo = findViewById(R.id.buttonConnectTo);
        buttonScanDevices = findViewById(R.id.buttonScanDevices);
        buttonUnlock = findViewById(R.id.buttonUnlock);
        buttonCheckOnline = findViewById(R.id.buttonCheckOnline);

        textViewProductID = findViewById(R.id.textViewProductID);
        textViewDeviceID = findViewById(R.id.textViewDeviceID);
        textViewDeviceName = findViewById(R.id.textViewDeviceName);

        imageViewDeviceImage = findViewById(R.id.imageViewDeviceImage);

        cardViewDevices = findViewById(R.id.CardViewDevices);
        cardViewDevices.setClickable(false);
        cardViewDevices.setBackgroundColor(Color.LTGRAY);

        TuyaOptimusSdk.init(getApplicationContext());
        ITuyaLockManager tuyaLockManager = TuyaOptimusSdk.getManager(ITuyaLockManager.class);


        LeScanSetting scanSetting = setBLEFilter();
        loginUser();


        cardViewDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //addRoom("testRoom");

                BleActivatorBean bleActivatorBean = new BleActivatorBean();

                // `mScanDeviceBean`: returned by `ScanDeviceBean` in the scanning result callback.
                bleActivatorBean.homeId = currentHome.getHomeId(); // homeId
                bleActivatorBean.address = beans.get(0).getAddress(); // The device IP address.
                bleActivatorBean.deviceType = beans.get(0).getDeviceType(); // The type of device.
                bleActivatorBean.uuid = beans.get(0).getUuid(); // The UUID.
                bleActivatorBean.productId = beans.get(0).getProductId(); // The product ID.
                //bleActivatorBean.isShare = (beans.get(0).getFlag() >> 2) & 0x01 == 1;// The flag that indicates whether the device is a shared one.

                // Starts pairing.
                TuyaHomeSdk.getActivator().newBleActivator().startActivator(bleActivatorBean, new IBleActivatorListener() {
                    @Override
                    public void onSuccess(DeviceBean deviceBean) {
                        // The device is paired.
                        Log.e("BLEPAIRING", "PAIRING SUCCESFUL");

                    }

                    @Override
                    public void onFailure(int code, String msg, Object handle) {
                        // Failed to pair the device.
                        Log.e("BLEPAIRING", "ERROR: " + code + " " + msg);
                    }
                });

            }
        });

        buttonScanDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts scanning.

                if (isScanning) {

                    isScanning = false;
                    TuyaHomeSdk.getBleOperator().stopLeScan();

                } else {

                    isScanning = true;

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
            }
        });

        buttonUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TuyaHomeSdk.newDeviceInstance( "d44f560be7df21d7").removeDevice(new IResultCallback() {
                    @Override
                    public void onError(String code, String error) {
                        Log.e("REMOVEDEVICE", "FAILED: " + code + " " + error);
                    }

                    @Override
                    public void onSuccess() {

                        Log.e("REMOVEDEVICE", "DEVICE REMOVED");
                        mDevice.unRegisterDevListener();
                    }
                });


                TuyaHomeSdk.newDeviceInstance("d44f560be7df21d7").getDeviceProperty(new IPropertyCallback<Map>() {
                    @Override
                    public void onError(String code, String error) {
                        Log.e("DEVICEPROPERTIES", "ERROR: " + code + " " + error);
                    }

                    @Override
                    public void onSuccess(Map result) {
                        Log.e("DEVICEPROPERTIES", "PROPERTIES: : " + result.toString());
                    }
                });
            }


        });

        buttonCheckOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("LOCKCONNECTION", "isBLEOnline: " + tuyaLockDevice.isBLEConnected());

                List<BleConnectBuilder> builderList = new ArrayList<>();

                BleConnectBuilder bleConnectBuilder1 = new BleConnectBuilder();

                bleConnectBuilder1.setDevId("d44f560be7df21d7"); // The value of `devId` for Device 1.
                bleConnectBuilder1.setDirectConnect(true);

                builderList.add(bleConnectBuilder1); // Adds Device 1.

                TuyaHomeSdk.getBleManager().connectBleDevice(builderList);
                Log.e("LOCKCONNECTION", "isBLEOnline: " + tuyaLockDevice.isBLEConnected());

                TuyaHomeSdk.getUserInstance().cancelAccount(new IResultCallback() {
                    @Override
                    public void onError(String code, String error) {
                        Log.e("ACCOUNTDELETION", code + " " + error);
                    }
                    @Override
                    public void onSuccess() {
                        Log.e("ACCOUNTDELETION", "SUCCESSFULL");
                    }
                });

            }
        });

        buttonConnectTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDevice = TuyaHomeSdk.newDeviceInstance("bf50c09zc4tivlad");
                mDevice.registerDevListener(deviceListener);
                Log.e("ADDDEVICELISTENER", "LISTENER ADDED");

                List<DeviceBean> deviceList = currentHome.getDeviceList();

                for (DeviceBean bean: deviceList
                     ) {

                    Log.e("DEVICE ",  bean.toString());

                }

                mDevice.resetFactory(new IResultCallback() {
                    @Override
                    public void onError(String code, String error) {
                        Log.e("FACTORYRESET ",  code + " " + error);
                    }

                    @Override
                    public void onSuccess() {
                        Log.e("FACTORYRESET ",  "SUCCESS");
                    }
                });

                mDevice.removeDevice(new IResultCallback() {
                    @Override
                    public void onError(String code, String error) {
                        Log.e("REMOVEDEVICE", "FAILED: " + code + " " + error);
                    }

                    @Override
                    public void onSuccess() {
                        Log.e("REMOVEDEVICE", "DEVICE REMOVED");
                        mDevice.unRegisterDevListener();
                    }
                });

                tuyaLockDevice = tuyaLockManager.getBleLock("d44f560be7df21d7");

                tuyaLockDevice.connect(new ConnectListener() {
                    @Override
                    public void onStatusChanged(boolean online) {
                        Log.e("LOCKCONNECT", "onStatusChanged  online: " + online);
                    }
                });

                tuyaLockDevice.unlock("1");


                /*tuyaLockDevice.addLockUser("filippo@laterramaggiore.it", true, true, 0, 0, null, new ITuyaResultCallback<Boolean>() {
                    @Override
                    public void onError(String code, String message) {
                        Log.e("LOCKUSER", "add lock user failed: code = " + code + "  message = " + message);
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        Log.e("LOCKUSER", "add lock user success");
                    }
                });*/

            }
        });

    }

    private void createHome(){

        rooms.add("dualLocker");

        TuyaHomeSdk.getHomeManagerInstance().createHome("defaultHome", 0, 0, "geoName", rooms, new ITuyaHomeResultCallback() {
            @Override
            public void onSuccess(HomeBean bean) {
                // do something
                Toast.makeText(homeActivity.this, "home created", Toast.LENGTH_SHORT).show();
                Log.e("Home", "Created");

                currentHome = bean;

                String s = bean.getRooms().toString();
                Log.e("ROOMS", s);

                TuyaHomeSdk.getMemberInstance().queryMemberList(bean.getHomeId(), new ITuyaGetMemberListCallback() {
                    @Override
                    public void onSuccess(List<MemberBean> memberBeans) {
                        // do something
                        Log.e("MEMBERS", memberBeans.toString());
                    }
                    @Override
                    public void onError(String errorCode, String error) {
                        // do something
                    }
                });


            }
            @Override
            public void onError(String errorCode, String errorMsg) {
                // do something
                Toast.makeText(homeActivity.this, "home creation failed", Toast.LENGTH_SHORT).show();
                Log.e("Home", errorCode +" "+ errorMsg);
            }
        });

    }

    private void addRoom(String roomName) {

        TuyaHomeSdk.newHomeInstance(currentHome.getHomeId()).addRoom(roomName, new ITuyaRoomResultCallback() {
            @Override
            public void onSuccess(RoomBean bean) {
                Toast.makeText(homeActivity.this, "Room " + roomName + " created", Toast.LENGTH_SHORT).show();
                Log.e("ROOM", "Room " + roomName + " created");

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                Toast.makeText(homeActivity.this, "Room " + roomName + " not created", Toast.LENGTH_SHORT).show();
                Log.e("ROOM", errorCode + " " + errorMsg);
            }

        });
    }

    private void loginUser(){

        TuyaHomeSdk.getUserInstance().loginWithEmail("39", "filippo@laterramaggiore.it", "test1234", new ILoginCallback() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(homeActivity.this, "LOGIN succesful", Toast.LENGTH_SHORT).show();
                Log.e("LOGIN", "User logged in");
                currentUser = user;
                createHome();
            }

            @Override
            public void onError(String code, String error) {
                Toast.makeText(homeActivity.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                Log.e("LOGIN", "ERROR: " + code + " " + error);
            }
        });

    }

    private LeScanSetting setBLEFilter(){
        return new LeScanSetting.Builder()
                .setTimeout(60000) // The duration of the scanning. Unit: milliseconds.
                .addScanType(ScanType.SINGLE) // `ScanType.SINGLE`: scans for Bluetooth LE devices.
                // `.addScanType(ScanType.SIG_MESH)`: scans for other types of devices.
                .build();

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

    IDevListener deviceListener = new IDevListener() {
        @Override
        public void onDpUpdate(String devId, String dpStr) {
            Log.e("DEVICELISTENER", "device updated");
        }

        @Override
        public void onRemoved(String devId) {
            Log.e("DEVICELISTENER", "device removed");
        }

        @Override
        public void onStatusChanged(String devId, boolean online) {
            Log.e("DEVICELISTENER", "device status changed, isonline: " + online );
        }

        @Override
        public void onNetworkStatusChanged(String devId, boolean status) {

        }

        @Override
        public void onDevInfoUpdate(String devId) {

        }
    };

}