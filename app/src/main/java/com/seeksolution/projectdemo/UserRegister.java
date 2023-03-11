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
import com.seeksolution.projectdemo.Model.CreateUserResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegister extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText et_name, et_email, et_password, et_mobile;
    private Button reg_submit;
    public String name , email , password, mobile;
    private TextView tv_name, tv_email, tv_password, tv_phone;


    public static final Pattern EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);
    public static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,16}$");
    //Password must contain one digit from 1 to 9, one lowercase letter, one uppercase letter,
    // one special character, no space, and it must be 8-16 characters long.
    public static final Pattern PHONE_REGEX = Pattern.compile("^((\\+*)((0[ -]*)*|((91 )*))((\\d{12})+|(\\d{10})+))|\\d{5}([- ]*)\\d{6}$");
    //03595-259506 , 03592 245902 , 03598245785 , 9775876662 , 0 9754845789 , 0-9778545896
    //+91 9456211568, 91 9857842356 , 919578965389


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        getSupportActionBar().hide();

        et_name = (TextInputEditText) findViewById(R.id.reg_name);
        et_email = (TextInputEditText) findViewById(R.id.reg_email);
        et_password = (TextInputEditText) findViewById(R.id.reg_password);
        et_mobile = (TextInputEditText) findViewById(R.id.reg_mobile);

        tv_name = (TextView) findViewById(R.id.tv_username);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_password = (TextView) findViewById(R.id.tv_password);
        tv_phone = (TextView) findViewById(R.id.tv_phone);


        reg_submit = (Button) findViewById(R.id.reg_submit);

        reg_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //Please Code for validation

        name = et_name.getText().toString().trim();
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();
        mobile = et_mobile.getText().toString().trim();


        if (validateUser(name) | validateEmail(email) | validatePhone(mobile) | validatePassword(password)) {
//            Toast.makeText(this, ""+user+email+password+phone+gender, Toast.LENGTH_SHORT).show();
            if (validateUser(name) && validateEmail(email) && validatePhone(mobile) && validatePassword(password)) {
                Toast.makeText(this, "Signed Up Successfully", Toast.LENGTH_SHORT).show();


                //Retrofit Api Call
                Call<CreateUserResponse> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .createUser(
                                name,
                                email,
                                password,
                                mobile
                        );
                call.enqueue(new Callback<CreateUserResponse>() {
                    @Override
                    public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                        if(response.isSuccessful()){
                            CreateUserResponse userResponse= response.body();
                            if(userResponse.getCode().equals("201") && userResponse.isStatus() == true){
                                Toast.makeText(UserRegister.this, ""+userResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                String NewUser_name = userResponse.getData().get(0).getName();
                                String NewUser_id = userResponse.getData().get(0).getId();

                                //session: for login use (pemanent data store)
//                        SharedPreferences sp = getSharedPreferences("user_data",MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putString("user_id",NewUser_id);
//                        editor.putString("user_name",NewUser_name);
//                        editor.commit();
                                //Intent code
                                Intent intent = new Intent(getApplicationContext(), SubscriptionActivity.class);
                                intent.putExtra("user_id",NewUser_id);
                                intent.putExtra("user_name",NewUser_name);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(UserRegister.this, ""+userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                        Toast.makeText(UserRegister.this, "Internet or Api Issue"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }

    }

    //validation code
    private boolean validateUser(String name){
        if (name.isEmpty()){
            tv_name.setText("User Field is required");
            tv_name.setTextColor(Color.parseColor("#ffffff"));
//            et_name.setBackgroundResource(R.drawable.error_layout);
            return false;
        }
        tv_name.setText("Success");
        tv_name.setTextColor(Color.parseColor("#4CAF50"));
//        et_name.setBackgroundResource(R.drawable.success_layout);
        return true;
    }

    public boolean validateEmail(String email){
        if (email.isEmpty()){
            tv_email.setText("Email Field is required");
            tv_email.setTextColor(Color.parseColor("#ffffff"));
//            et_email.setBackgroundResource(R.drawable.error_layout);
            return false;
        }
        if(!EMAIL_ADDRESS_REGEX.matcher(email).matches()){
            tv_email.setText("Invalid Email");
            tv_email.setTextColor(Color.parseColor("#ffffff"));
//            et_email.setBackgroundResource(R.drawable.error_layout);
            return false;
        }
        tv_email.setText("Success");
        tv_email.setTextColor(Color.parseColor("#4CAF50"));
//        et_email.setBackgroundResource(R.drawable.success_layout);
        return true;
    }

    public boolean validatePhone(String mobile){
        if (mobile.isEmpty()){
            tv_phone.setText("Phone No. is required");
            tv_phone.setTextColor(Color.parseColor("#ffffff"));
//            et_mobile.setBackgroundResource(R.drawable.error_layout);
            return false;
        }
        if(!PHONE_REGEX.matcher(mobile).matches()){
            tv_phone.setText("Invalid Phone");
            tv_phone.setTextColor(Color.parseColor("#ffffff"));
//            et_mobile.setBackgroundResource(R.drawable.error_layout);
            return false;
        }
        tv_phone.setText("Success");
        tv_phone.setTextColor(Color.parseColor("#4CAF50"));
//        et_mobile.setBackgroundResource(R.drawable.success_layout);
        return true;
    }

    public boolean validatePassword(String password){
        if (password.isEmpty()){
            tv_password.setText("Password Field is required");
            tv_password.setTextColor(Color.parseColor("#ffffff"));
//            et_password.setBackgroundResource(R.drawable.error_layout);
            return false;
        }
        if(!PASSWORD_REGEX.matcher(password).matches()){
            tv_password.setText("Invalid Password");
            tv_password.setTextColor(Color.parseColor("#ffffff"));
//            et_password.setBackgroundResource(R.drawable.error_layout);
            return false;
        }
        tv_password.setText("Success");
        tv_password.setTextColor(Color.parseColor("#4CAF50"));
//        et_password.setBackgroundResource(R.drawable.success_layout);
        return true;
    }
}