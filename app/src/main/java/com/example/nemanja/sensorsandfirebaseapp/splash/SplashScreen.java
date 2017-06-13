package com.example.nemanja.sensorsandfirebaseapp.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.nemanja.sensorsandfirebaseapp.MainActivity;
import com.example.nemanja.sensorsandfirebaseapp.R;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}