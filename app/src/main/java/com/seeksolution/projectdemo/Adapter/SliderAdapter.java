package com.seeksolution.projectdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.seeksolution.projectdemo.Model.Slider;
import com.seeksolution.projectdemo.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.MyViewHolder> {

    public Context context;
    ArrayList<Slider> arrayList =new ArrayList<>();

    public SliderAdapter(Context context, ArrayList<Slider> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.mycustom_slider,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Picasso.get().load(arrayList.get(position).getSlider_pic()).into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends ViewHolder{

        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.iv_slider);
        }
    }
}
