package com.seeksolution.projectdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seeksolution.projectdemo.Model.VideoModel;
import com.seeksolution.projectdemo.R;
import com.seeksolution.projectdemo.VideoPlay;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    public Context context;
    ArrayList<VideoModel> videodata;

    public VideoAdapter(Context context, ArrayList<VideoModel> videodata) {
        this.context = context;
        this.videodata = videodata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mycustom_rc,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Picasso.get().load(Uri.parse(videodata.get(position).getVedio_banner())).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return videodata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_id);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            VideoModel item = videodata.get(position);
//            Toast.makeText(context, ""+item.getVedio_url()+item.category, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, VideoPlay.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("vedio_url",item.getVedio_url());
            intent.putExtra("vedio_category",item.getCategory());
            intent.putExtra("vedio_year",item.getYear());
            intent.putExtra("vedio_desc",item.getVedio_description());
            context.startActivity(intent);

        }
    }
}
