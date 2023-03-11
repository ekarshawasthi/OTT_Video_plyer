package com.seeksolution.projectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button login_with_email;
    private Button signupForm, login_with_otp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        login_with_email = (Button) findViewById(R.id.login_with_email);
        signupForm = (Button) findViewById(R.id.signupForm);
        login_with_otp = (Button) findViewById(R.id.login_with_otp);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_email",null);
        String user_email = sharedPreferences.getString("user_email",null);

        if(user_email!=null && user_id!=null){
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        login_with_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginwithEmail.class);
                startActivity(intent);
            }
        });

        signupForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, UserRegister.class);
                startActivity(i);
            }
        });

        login_with_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginwithMobile.class);
                startActivity(i);
            }
        });
    }
}