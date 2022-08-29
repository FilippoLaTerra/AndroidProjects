package com.lock.usbcontroltests;

import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;

import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;


public class MainActivity extends AppCompatActivity implements ArduinoListener {
    private Arduino arduino;
    private TextView textView;
    private Button slot1, slot2;
    private boolean isOpenSlot1, isOpenSlot2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.devicename);
        textView.setMovementMethod(new ScrollingMovementMethod());
        slot1 = findViewById(R.id.buttonSlot1);
        slot2 = findViewById(R.id.buttonSlot2);

        isOpenSlot1 = false;
        isOpenSlot2 = false;

        arduino = new Arduino(this, 19200);
        display("Please plug an Arduino via OTG.\nOn some devices you will have to enable OTG Storage in the phone's settings.\n\n");

        slot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isOpenSlot1 = !isOpenSlot1;

                sendData(1, isOpenSlot1);

            }
        });

        slot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isOpenSlot2 = !isOpenSlot2;

                sendData(2, isOpenSlot2);

            }
        });
    }

    protected void sendData(int slot, boolean isOpen){

        String command = (isOpen? "O" : "C") + slot + "\n";
        Toast.makeText(getApplicationContext(), "COMMAND: " + command, Toast.LENGTH_SHORT).show();

        byte[] dataToByte = command.getBytes(StandardCharsets.UTF_8);

        arduino.send(dataToByte);

        Toast.makeText(getApplicationContext(), "SLOT: " + slot + " OPEN: " + isOpen, Toast.LENGTH_SHORT).show();
    }

    /*protected void sendData(int slot, boolean isOpen){

        byte[] dataToByte = new byte[] {(byte) slot, (byte) (isOpen?1:0)};

        arduino.send(dataToByte);

        Toast.makeText(getApplicationContext(), "SLOT: " + slot + " OPEN: " + isOpen, Toast.LENGTH_SHORT).show();
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        arduino.setArduinoListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        arduino.send(new byte[]{-1, -1});
        arduino.unsetArduinoListener();
        arduino.close();
    }

    @Override
    public void onArduinoAttached(UsbDevice device) {
        display("Arduino attached!");
        arduino.open(device);
    }

    @Override
    public void onArduinoDetached() {
        display("Arduino detached");
    }

    @Override
    public void onArduinoMessage(byte[] bytes) {
        display("> "+new String(bytes));
    }

    @Override
    public void onArduinoOpened() {
        display("ARDUINO OPENED");

    }

    @Override
    public void onUsbPermissionDenied() {
        display("Permission denied... New attempt in 3 sec");
        arduino.reopen();


    }

    public void display(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.append(message+"\n");
            }
        });
    }
}