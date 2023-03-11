package com.seeksolution.projectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.seeksolution.projectdemo.Adapter.VideoAdapter;
import com.seeksolution.projectdemo.Api.RetrofitClient;
import com.seeksolution.projectdemo.Model.VideoModel;
import com.seeksolution.projectdemo.Model.VideoModelResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlay extends AppCompatActivity {

    private VideoView videoView;
    private String VedioUrl, Vediocategory,Vedioyear, Vediodesc ;
    private TextView t1 , t2 , t3;

    private RecyclerView recyclerView1;
    ArrayList<VideoModel> videoList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        getSupportActionBar().hide();

        videoView = (VideoView) findViewById(R.id.video_player);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);

        VedioUrl = getIntent().getStringExtra("vedio_url");
        Vediocategory = getIntent().getStringExtra("vedio_category");
        Vedioyear = getIntent().getStringExtra("vedio_year");
        Vediodesc = getIntent().getStringExtra("vedio_desc");

        t1.setText(Vediocategory);
        t2.setText(Vedioyear+"   :  "+Vediocategory+"  :  "+"Hindi");
        t3.setText(Vediodesc);

        videoView.setVideoURI(Uri.parse(VedioUrl));   //Raw folder => Audio vedio file => local Video

        videoView.start();
//        videoView.pause();
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();

        recyclerView1=(RecyclerView) findViewById(R.id.rv);
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
                        if(modelResponse.getData().get(i).getCategory().equals(Vediocategory)) {
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
                Toast.makeText(VideoPlay.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //recyclerview1 end
    }
}