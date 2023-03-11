package com.seeksolution.projectdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.seeksolution.projectdemo.Adapter.SubcriberPackageAdapter;
import com.seeksolution.projectdemo.Api.RetrofitClient;
import com.seeksolution.projectdemo.Model.PackageResponse;
import com.seeksolution.projectdemo.Model.Packages;
import com.seeksolution.projectdemo.Model.UpdatePackageResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_package_list;
    private ArrayList<Packages> packagesArrayList;

    public Button btn_package_proceed;

    public String IntentData_userId, IntentData_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        rv_package_list = (RecyclerView) findViewById(R.id.rc_subscribe_package_list);
        packagesArrayList = new ArrayList<>();

        rv_package_list.setLayoutManager(new LinearLayoutManager(this));

        //Api Call
        //data set prepare
        //Adapter Bind

        btn_package_proceed = (Button) findViewById(R.id.btn_package_proceed);

        btn_package_proceed.setOnClickListener(this);

        //Intent data get
        Bundle extras = getIntent().getExtras();
        IntentData_userId = extras.getString("user_id",null);
        IntentData_userName = extras.getString("user_name",null);

        //Intent data close

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<PackageResponse> call = RetrofitClient.getInstance().getAPI()
                .getSubcriptionPackages();
        call.enqueue(new Callback<PackageResponse>() {
            @Override
            public void onResponse(Call<PackageResponse> call, Response<PackageResponse> response) {
                if(response.isSuccessful()){

//                    Toast.makeText(SubscriptionActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();

                    PackageResponse packageResponse = response.body();

                    if(packageResponse.isStatus() == true && packageResponse.getCode().equals("201")){

                        int PackageSize = packageResponse.getData().size();
                        for(int i=0; i<PackageSize ; i++){
                            packagesArrayList.add(new Packages(
                                    packageResponse.getData().get(i).getId(),
                                    packageResponse.getData().get(i).getPackage_name(),
                                    packageResponse.getData().get(i).getPackage_price(),
                                    "/"+packageResponse.getData().get(i).getPackage_duration(),
                                    packageResponse.getData().get(i).getPackage_desc(),
                                    packageResponse.getData().get(i).getPackage_pic()
                            ));
                        }

                        SubcriberPackageAdapter adapter = new SubcriberPackageAdapter(getApplicationContext(),packagesArrayList);
                        rv_package_list.setAdapter(adapter);
                        //Intent

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                              progressDialog.hide();
                            }
                        },2000);
                    }else{
                        Toast.makeText(SubscriptionActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PackageResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        //get the Subscribed Package
        SharedPreferences sp = getSharedPreferences("user_data",MODE_PRIVATE);
        String package_id = sp.getString("user_package_id",null);
        String package_name = sp.getString("user_package_name",null);

        if(package_id != null && package_name != null){

//            Toast.makeText(this, ""+package_id, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, ""+package_name, Toast.LENGTH_SHORT).show();

            // Create the object of AlertDialog Builder class
            AlertDialog.Builder builder = new AlertDialog.Builder(SubscriptionActivity.this);

            // Set the message show for the Alert time
            builder.setMessage("Hi, "+IntentData_userName+" are going to subscribe the \n"+package_name+" Package \n"+"Press Yes to Proceed");

            // Set Alert Title
            builder.setTitle("Alert !");

            //Set Icon
            builder.setIcon(R.drawable.subscriptions_icon);

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                // When the user click yes button then app will close
//                finish();

                subscribeToPackageApi(IntentData_userId, package_id);

            });

            // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                // If user click no then dialog box is canceled.
                dialog.cancel();
            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();
            // Show the Alert Dialog box
            alertDialog.show();

        }else{

            Toast.makeText(this, "Please Subscribe the Package", Toast.LENGTH_SHORT).show();
        }
        SharedPreferences sp1= getSharedPreferences("user_data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit();
        editor.remove("user_package_id");
        editor.remove("user_package_name");
        editor.commit();

    }

    private void subscribeToPackageApi(String user_id, String package_id) {

        Call<UpdatePackageResponse> call = RetrofitClient.getInstance()
                .getAPI()
                .subscribeToPackage("PUT",user_id,package_id);

        call.enqueue(new Callback<UpdatePackageResponse>() {
            @Override
            public void onResponse(Call<UpdatePackageResponse> call, Response<UpdatePackageResponse> response) {

                if(response.isSuccessful()){

                    UpdatePackageResponse packageResponse = response.body();
                    if(packageResponse.isStatus() == true && packageResponse.getCode().equals("201")){

                        Toast.makeText(SubscriptionActivity.this, ""+packageResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        String packageName = packageResponse.getData().getCurrent_package().getPackage_name();
                        String packageDuration = packageResponse.getData().getCurrent_package().getPackage_duration();
                        String packagePrice = packageResponse.getData().getCurrent_package().getPackage_price();
                        Toast.makeText(SubscriptionActivity.this, "Amount to be paid"+packagePrice, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),PaymentGateway.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(SubscriptionActivity.this, ""+packageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SubscriptionActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdatePackageResponse> call, Throwable t) {

                Log.d("myOutputTag",t.getMessage());
                Toast.makeText(SubscriptionActivity.this, "Error In Api or Internent"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}