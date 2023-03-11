package com.seeksolution.projectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.seeksolution.projectdemo.Adapter.SliderAdapter;
import com.seeksolution.projectdemo.Adapter.SubcriberPackageAdapter;
import com.seeksolution.projectdemo.Adapter.VideoAdapter;
import com.seeksolution.projectdemo.Api.RetrofitClient;
import com.seeksolution.projectdemo.Model.PackageResponse;
import com.seeksolution.projectdemo.Model.Packages;
import com.seeksolution.projectdemo.Model.Slider;
import com.seeksolution.projectdemo.Model.SliderResponse;
import com.seeksolution.projectdemo.Model.VideoModel;
import com.seeksolution.projectdemo.Model.VideoModelResponse;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private TextView dashboard_tv_email;
    private ImageButton dashboard_iv_logout;
    private SliderView sliderView2;
    private ArrayList<Slider> sliderArrayList;
    private SliderAdapter sliderAdapter;

    //recycler view
    private RecyclerView recyclerView1, recyclerView2, recyclerView3 , recyclerView4 , recyclerView5 , recyclerView6;
    ArrayList<VideoModel> videoList1, videoList2 , videoList3 , videoList4 , videoList5 , videoList6;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();

//user deatils
        dashboard_tv_email = (TextView) findViewById(R.id.dashboard_tv_email);
        dashboard_iv_logout = (ImageButton) findViewById(R.id.dashboard_iv_logout);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_email",null);
        String user_email = sharedPreferences.getString("user_email",null);

        if(user_email!=null && user_id!=null){
            dashboard_tv_email.setText(user_email);
        }

        dashboard_iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear sheared preference
                SharedPreferences sp = getSharedPreferences("user_data",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(DashboardActivity.this, "Log Out Successfully", Toast.LENGTH_SHORT).show();

                Intent i= new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });
//users deatail ends
        sliderView2 = findViewById(R.id.imageSlider);
        sliderArrayList = new ArrayList<>();
        Call<SliderResponse> call = RetrofitClient.getInstance().getAPI().getSliderImages();
        call.enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {
                if(response.isSuccessful()){

                    Toast.makeText(DashboardActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();

                    SliderResponse sliderResponse = response.body();

                    if(sliderResponse.isStatus() == true && sliderResponse.getCode().equals("201")){

                        int SliderSize = sliderResponse.getData().size();
                        for(int i=0; i<SliderSize ; i++){
                            sliderArrayList.add(new Slider(
                                    sliderResponse.getData().get(i).getId(),
                                    sliderResponse.getData().get(i).getSlider_name(),
                                    sliderResponse.getData().get(i).getSlider_pic()
                            ));
                        }

                        SliderAdapter adapter = new SliderAdapter(DashboardActivity.this,sliderArrayList);
                        sliderView2.setSliderAdapter(adapter);
                        sliderView2.startAutoCycle();

                    }else{
                        Toast.makeText(DashboardActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {

            }
        });

        //recycler view1  start
        recyclerView1=(RecyclerView) findViewById(R.id.rv_1);
        //        1st recylerview
        recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        //Calling of Api Retrofit
        videoList1 = new ArrayList<>();

        Call<VideoModelResponse> call1 = RetrofitClient.getInstance().getAPI().getMoviesData();
        call1.enqueue(new Callback<VideoModelResponse>() {
            @Override
            public void onResponse(Call<VideoModelResponse> call, Response<VideoModelResponse> response) {
                if (response.isSuccessful()) {
                    VideoModelResponse modelResponse = response.body();
                    int Arraysize = modelResponse.getData().size();

                    for (int i = 0; i < Arraysize; i++) {
                        if(modelResponse.getData().get(i).getCategory().equals("cartoon")) {
                            videoList1.add(new VideoModel(
                                    modelResponse.getData().get(i).getId(),
                                    modelResponse.getData().get(i).getVedio_url(),
                                    modelResponse.getData().get(i).getVedio_banner(),
                                    modelResponse.getData().get(i).getVedio_description(),
                                    modelResponse.getData().get(i).getYear(),
                                    modelResponse.getData().get(i).getRating(),
                                    modelResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    VideoAdapter adapter = new VideoAdapter(getApplicationContext(), videoList1);
                    recyclerView1.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<VideoModelResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //recyclerview1 end

        //recycler view2  start
        recyclerView2=(RecyclerView) findViewById(R.id.rv_2);
        //        2 recylerview
        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        //Calling of Api Retrofit
        videoList2 = new ArrayList<>();

        Call<VideoModelResponse> call2 = RetrofitClient.getInstance().getAPI().getMoviesData();
        call2.enqueue(new Callback<VideoModelResponse>() {
            @Override
            public void onResponse(Call<VideoModelResponse> call, Response<VideoModelResponse> response) {
                if (response.isSuccessful()) {
                    VideoModelResponse modelResponse = response.body();
                    int Arraysize = modelResponse.getData().size();

                    for (int i = 0; i < Arraysize; i++) {
                        if(modelResponse.getData().get(i).getCategory().equals("hindi-movie")) {
                            videoList2.add(new VideoModel(
                                    modelResponse.getData().get(i).getId(),
                                    modelResponse.getData().get(i).getVedio_url(),
                                    modelResponse.getData().get(i).getVedio_banner(),
                                    modelResponse.getData().get(i).getVedio_description(),
                                    modelResponse.getData().get(i).getYear(),
                                    modelResponse.getData().get(i).getRating(),
                                    modelResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    VideoAdapter adapter = new VideoAdapter(getApplicationContext(), videoList2);
                    recyclerView2.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<VideoModelResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //recyclerview2 end

        //recycler view3  start
        recyclerView3=(RecyclerView) findViewById(R.id.rv_3);
        //        3 recylerview
        recyclerView3.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        //Calling of Api Retrofit
        videoList3 = new ArrayList<>();

        Call<VideoModelResponse> call3 = RetrofitClient.getInstance().getAPI().getMoviesData();
        call3.enqueue(new Callback<VideoModelResponse>() {
            @Override
            public void onResponse(Call<VideoModelResponse> call, Response<VideoModelResponse> response) {
                if (response.isSuccessful()) {
                    VideoModelResponse modelResponse = response.body();
                    int Arraysize = modelResponse.getData().size();

                    for (int i = 0; i < Arraysize; i++) {
                        if(modelResponse.getData().get(i).getCategory().equals("Hindi-song")) {
                            videoList3.add(new VideoModel(
                                    modelResponse.getData().get(i).getId(),
                                    modelResponse.getData().get(i).getVedio_url(),
                                    modelResponse.getData().get(i).getVedio_banner(),
                                    modelResponse.getData().get(i).getVedio_description(),
                                    modelResponse.getData().get(i).getYear(),
                                    modelResponse.getData().get(i).getRating(),
                                    modelResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    VideoAdapter adapter = new VideoAdapter(getApplicationContext(), videoList3);
                    recyclerView3.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<VideoModelResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //recyclerview3 end

        //recycler view4  start
        recyclerView4=(RecyclerView) findViewById(R.id.rv_4);
        //        4 recylerview
        recyclerView4.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        //Calling of Api Retrofit
        videoList4 = new ArrayList<>();

        Call<VideoModelResponse> call4 = RetrofitClient.getInstance().getAPI().getMoviesData();
        call4.enqueue(new Callback<VideoModelResponse>() {
            @Override
            public void onResponse(Call<VideoModelResponse> call, Response<VideoModelResponse> response) {
                if (response.isSuccessful()) {
                    VideoModelResponse modelResponse = response.body();
                    int Arraysize = modelResponse.getData().size();

                    for (int i = 0; i < Arraysize; i++) {
                        if(modelResponse.getData().get(i).getCategory().equals("hollywood-movies")) {
                            videoList4.add(new VideoModel(
                                    modelResponse.getData().get(i).getId(),
                                    modelResponse.getData().get(i).getVedio_url(),
                                    modelResponse.getData().get(i).getVedio_banner(),
                                    modelResponse.getData().get(i).getVedio_description(),
                                    modelResponse.getData().get(i).getYear(),
                                    modelResponse.getData().get(i).getRating(),
                                    modelResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    VideoAdapter adapter = new VideoAdapter(getApplicationContext(), videoList4);
                    recyclerView4.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<VideoModelResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //recyclerview4 end

        //recycler view5  start
        recyclerView5=(RecyclerView) findViewById(R.id.rv_5);
        //        5 recylerview
        recyclerView5.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        //Calling of Api Retrofit
        videoList5 = new ArrayList<>();

        Call<VideoModelResponse> call5 = RetrofitClient.getInstance().getAPI().getMoviesData();
        call5.enqueue(new Callback<VideoModelResponse>() {
            @Override
            public void onResponse(Call<VideoModelResponse> call, Response<VideoModelResponse> response) {
                if (response.isSuccessful()) {
                    VideoModelResponse modelResponse = response.body();
                    int Arraysize = modelResponse.getData().size();

                    for (int i = 0; i < Arraysize; i++) {
                        if(modelResponse.getData().get(i).getCategory().equals("Html-videos")) {
                            videoList5.add(new VideoModel(
                                    modelResponse.getData().get(i).getId(),
                                    modelResponse.getData().get(i).getVedio_url(),
                                    modelResponse.getData().get(i).getVedio_banner(),
                                    modelResponse.getData().get(i).getVedio_description(),
                                    modelResponse.getData().get(i).getYear(),
                                    modelResponse.getData().get(i).getRating(),
                                    modelResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    VideoAdapter adapter = new VideoAdapter(getApplicationContext(), videoList5);
                    recyclerView5.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<VideoModelResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //recyclerview5 end

        //recycler view5  start
        recyclerView6=(RecyclerView) findViewById(R.id.rv_6);
        //        5 recylerview
        recyclerView6.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        //Calling of Api Retrofit
        videoList6 = new ArrayList<>();

        Call<VideoModelResponse> call6 = RetrofitClient.getInstance().getAPI().getMoviesData();
        call6.enqueue(new Callback<VideoModelResponse>() {
            @Override
            public void onResponse(Call<VideoModelResponse> call, Response<VideoModelResponse> response) {
                if (response.isSuccessful()) {
                    VideoModelResponse modelResponse = response.body();
                    int Arraysize = modelResponse.getData().size();

                    for (int i = 0; i < Arraysize; i++) {
                        if(modelResponse.getData().get(i).getCategory().equals("CSS")) {
                            videoList6.add(new VideoModel(
                                    modelResponse.getData().get(i).getId(),
                                    modelResponse.getData().get(i).getVedio_url(),
                                    modelResponse.getData().get(i).getVedio_banner(),
                                    modelResponse.getData().get(i).getVedio_description(),
                                    modelResponse.getData().get(i).getYear(),
                                    modelResponse.getData().get(i).getRating(),
                                    modelResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    VideoAdapter adapter = new VideoAdapter(getApplicationContext(), videoList6);
                    recyclerView6.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<VideoModelResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //recyclerview5 end
    }
}