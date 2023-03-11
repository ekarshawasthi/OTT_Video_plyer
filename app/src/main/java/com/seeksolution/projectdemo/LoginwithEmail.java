package com.seeksolution.projectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.seeksolution.projectdemo.Api.RetrofitClient;
import com.seeksolution.projectdemo.Model.LoginResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginwithEmail extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText et_login_email;
    private TextInputEditText et_login_pwd;
    private Button btn_loginwithemail;
    public String email , password;
    private TextView tv_email, tv_password;


    public static final Pattern EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);
    public static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,16}$");
    //Password must contain one digit from 1 to 9, one lowercase letter, one uppercase letter,
    // one special character, no space, and it must be 8-16 characters long.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginwith_email);
        getSupportActionBar().hide();

        et_login_email = (TextInputEditText) findViewById(R.id.et_login_email);
        et_login_pwd =  (TextInputEditText) findViewById(R.id.et_login_pwd);
        btn_loginwithemail = (Button) findViewById(R.id.btn_loginwithemail);

        tv_email = (TextView) findViewById(R.id.tv_login_email);
        tv_password = (TextView) findViewById(R.id.tv_login_password);

        btn_loginwithemail.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        email = et_login_email.getText().toString().trim();
        password = et_login_pwd.getText().toString().trim();

        if (validateEmail(email) | validatePassword(password)) {
//            Toast.makeText(this, ""+user+email+password+phone+gender, Toast.LENGTH_SHORT).show();
            if (validateEmail(email) && validatePassword(password)) {
//                Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show();

                //Filled
                //Api Hit

                Call<LoginResponse> call = RetrofitClient.getInstance().getAPI().
                        createLogin(
                                email,
                                password
                        );
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            if (loginResponse.isStatus() == true && loginResponse.getCode().equals("201")) {
//                                Toast.makeText(LoginwithEmail.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                //store the data in shared preference
                                SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_id",loginResponse.getData().get(0).getId());
                                editor.putString("user_email",loginResponse.getData().get(0).getEmail());
                                editor.putString("user_name",loginResponse.getData().get(0).getName());
                                editor.putString("user_token",loginResponse.getData().get(0).getToken());

                                editor.commit();

                                Intent intent = new Intent(LoginwithEmail.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(LoginwithEmail.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginwithEmail.this, "Internet Issue", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public boolean validateEmail(String email){
        if (email.isEmpty()){
            tv_email.setText("Email Field is required");
            tv_email.setTextColor(Color.parseColor("#ffffff"));
//            et_login_email.setBackgroundResource(R.drawable.error_layout);
            return false;
        }
        if(!EMAIL_ADDRESS_REGEX.matcher(email).matches()){
            tv_email.setText("Invalid Email");
            tv_email.setTextColor(Color.parseColor("#ffffff"));
//            et_login_email.setBackgroundResource(R.drawable.error_layout);
            return false;
        }
        tv_email.setText("Success");
        tv_email.setTextColor(Color.parseColor("#4CAF50"));
//        et_login_email.setBackgroundResource(R.drawable.success_layout);
        return true;
    }

    public boolean validatePassword(String password){
        if (password.isEmpty()){
            tv_password.setText("Password Field is required");
            tv_password.setTextColor(Color.parseColor("#ffffff"));
//            et_login_pwd.setBackgroundResource(R.drawable.error_layout);
            return false;
        }
        if(!PASSWORD_REGEX.matcher(password).matches()){
            tv_password.setText("Invalid Password");
            tv_password.setTextColor(Color.parseColor("#ffffff"));
//            et_login_pwd.setBackgroundResource(R.drawable.error_layout);
            return false;
        }
        tv_password.setText("Success");
        tv_password.setTextColor(Color.parseColor("#4CAF50"));
//        et_login_pwd.setBackgroundResource(R.drawable.success_layout);
        return true;
    }
}