package com.seeksolution.projectdemo.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.seeksolution.projectdemo.MainActivity;
import com.seeksolution.projectdemo.Model.Packages;
import com.seeksolution.projectdemo.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class SubcriberPackageAdapter extends RecyclerView.Adapter<SubcriberPackageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Packages> packagelist;
//    private int rowIndex;
//    private int checkedPosition = 0;

    //me
    private RadioButton rbChecked = null;
    private int rbPosition = 0;
    //me

    public SubcriberPackageAdapter(Context context, ArrayList<Packages> packagelist) {
        this.context = context;
        this.packagelist = packagelist;

        SharedPreferences sp1= context.getSharedPreferences("user_data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit();
        editor.remove("user_package_id");
        editor.remove("user_package_name");
        editor.commit();

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.mycustom_subscription_package_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int index= position;
        holder.package_name.setText(packagelist.get(position).getPackage_name());
        holder.package_price.setText(packagelist.get(position).getPackage_price());
        holder.package_duration.setText(packagelist.get(position).getPackage_duration());
        holder.package_desc.setText(packagelist.get(position).getPackage_desc());

        Picasso.get().load(Uri.parse(packagelist.get(position).package_pic)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                if(index>0){
                    holder.package_background.setBackground(new BitmapDrawable(bitmap));
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }



    @Override
    public int getItemCount() {

        return packagelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout package_background;
        private TextView package_name;
        private TextView package_price;
        private TextView package_duration;
        private TextView package_desc;
        public RadioButton rb_package_sub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            package_background = itemView.findViewById(R.id.rl_package_background);
            package_name = itemView.findViewById(R.id.tv_package_name);
            package_price = itemView.findViewById(R.id.tv_package_price);
            package_duration = itemView.findViewById(R.id.tv_package_duration);
            package_desc = itemView.findViewById(R.id.tv_package_desc);
            rb_package_sub = itemView.findViewById(R.id.rb_package_sub);

            //Radio Button
            if (rbPosition == 0 && rb_package_sub.isChecked())
            {
                rbChecked = rb_package_sub;
                rbPosition = 0;
            }
            rb_package_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton rb = (RadioButton) v;
                    int clickedPos = getAdapterPosition();
                    if (rb.isChecked()){
                        if(rbChecked != null)
                        {
                            rbChecked.setChecked(false);
                        }
                        rbChecked = rb;
                        rbPosition = clickedPos;
                    }
                    else{
                        rbChecked = null;
                        rbPosition = 0;
                    }
//                    Toast.makeText(context, ""+packagelist.get(rbPosition).getPackage_name(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, ""+packagelist.get(rbPosition).getId(), Toast.LENGTH_SHORT).show();

                    //Shared Preference:session
                    SharedPreferences sp = context.getSharedPreferences("user_data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("user_package_id",packagelist.get(rbPosition).getId());
                    editor.putString("user_package_name",packagelist.get(rbPosition).getPackage_name());
                    editor.commit();
                }
            });
            //Radio Button
        }
    }
}
