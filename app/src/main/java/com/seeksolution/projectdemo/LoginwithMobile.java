package com.seeksolution.projectdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class LoginwithMobile extends AppCompatActivity {

    private EditText otp_et_mobile , otp_et_otp;
    private Button otp_btn_send , otp_btn_resend , otp_btn_verify;

    //firebase
    private FirebaseAuth mAuth;
    private String VerificationCode;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginwith_mobile);

        //firebase
        mAuth = FirebaseAuth.getInstance();

        otp_et_mobile = (EditText) findViewById(R.id.otp_et_mobile);
        otp_et_otp = (EditText) findViewById(R.id.otp_et_otp);

        otp_btn_send = (Button) findViewById(R.id.otp_btn_send);
        otp_btn_resend = (Button) findViewById(R.id.otp_btn_resend);
        otp_btn_verify = (Button) findViewById(R.id.otp_btn_verify);

        otp_et_otp.setVisibility(View.GONE);
        otp_btn_resend.setVisibility(View.GONE);
        otp_btn_verify.setVisibility(View.GONE);

//        otp_btn_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String phone = otp_et_mobile.getText().toString().trim();
//                sendVerificationOtp("+91"+phone);
//            }
//        });

        otp_btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        otp_btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

//    private void sendVerificationOtp(String s) {
//
//        PhoneAuthOptions options = PhoneAuthOptions
//                .newBuilder(mAuth)
//                .setPhoneNumber(s)
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setActivity(LoginwithMobile.this)
//                .setCallbacks(mCallback)
//                .build();
//
//        PhoneAuthProvider.verifyPhoneNumber(options);
//
//    }
//
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//            String OTP = phoneAuthCredential.getSmsCode();
//            Toast.makeText(LoginwithMobile.this, ""+OTP, Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onVerificationFailed(@NonNull FirebaseException e) {
//
//            Toast.makeText(LoginwithMobile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//
//            VerificationCode = s;
//            Toast.makeText(LoginwithMobile.this, ""+VerificationCode, Toast.LENGTH_SHORT).show();
//        }
//    };
}