package com.seeksolution.projectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //hide actionbar
        getSupportActionBar().hide();

        //Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //we can also call Retrofit Api call
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);     //4 sec

    }
}