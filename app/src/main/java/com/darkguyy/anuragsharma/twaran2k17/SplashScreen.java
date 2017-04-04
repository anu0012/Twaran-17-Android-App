package com.darkguyy.anuragsharma.twaran2k17;

/**
 * Created by anuragsharma on 29/03/17.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread t = new Thread() {
            public void run() {

                try {
                    sleep(2000);

                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }
}