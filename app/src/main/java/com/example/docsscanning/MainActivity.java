package com.example.docsscanning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,
                        Dashboard.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);


            Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
    }
}