package com.example.nemanja.sensorsandfirebaseapp.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nemanja.sensorsandfirebaseapp.R;

public class BluetoothActivity extends Activity {
    private TextView btTextView;
    private BluetoothAdapter btBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_activity);

        Context btContext = getApplicationContext();
        Resources btResources = getResources();
        RelativeLayout btRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        btTextView = (TextView) findViewById(R.id.tv);
        Button btButtonState = (Button) findViewById(R.id.btn_state);
        Button btButtonEnable = (Button) findViewById(R.id.btn_enable);
        Button btButtonDisable = (Button) findViewById(R.id.btn_disable);
        btBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btButtonState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btBluetoothAdapter.isEnabled()) {
                    btTextView.setText(R.string.bluetooth_on);
                } else {
                    btTextView.setText(R.string.bluetooth_off);
                }
                ImageView bluetooth = (ImageView) findViewById(R.id.bluetooth);
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                bluetooth.startAnimation(animation1);
            }
        });

        btButtonEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!btBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
            }
        });

        btButtonDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btBluetoothAdapter.isEnabled()) {
                    btBluetoothAdapter.disable();
                }
            }
        });
    }
}