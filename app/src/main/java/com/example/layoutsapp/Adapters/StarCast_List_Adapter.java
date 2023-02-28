package com.example.layoutsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.layoutsapp.Activities.Movies_Description_Activity;
import com.example.layoutsapp.ModelClasses.Movies_ModelClass;
import com.example.layoutsapp.ModelClasses.StarCast_ModelClass;
import com.example.layoutsapp.R;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.IllegalFormatCodePointException;
import java.util.List;

public class StarCast_List_Adapter extends RecyclerView.Adapter<StarCast_List_Adapter.ViewHolder> {

    private List<StarCast_ModelClass> userList;

//    String epposition

    public StarCast_List_Adapter(List<StarCast_ModelClass> userList) {
        this.userList = userList;
    }

    Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
            .setDuration(1000) // how long the shimmering animation takes to do one full sweep
            .setBaseAlpha(1f) //the alpha of the underlying children
            .setHighlightAlpha(0.7f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build();

    // This is the placeholder for the imageView
    ShimmerDrawable shimmerDrawable= new ShimmerDrawable();


    @Override
    public void onViewAttachedToWindow(@NonNull @NotNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        shimmerDrawable.setShimmer(shimmer);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design_starcast, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        int resource = userList.get(position).getImageview();
        String realName = userList.get(position).getSCname();
        String roleName = userList.get(position).getSCrole();
        String department = userList.get(position).getSCdepartment();
        String imagelink = userList.get(position).getSCimageLink();

        holder.setData(realName, roleName, department, imagelink);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), epno, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(v.getContext(), Movies_Description_Activity.class);
//                intent.putExtra("MovieID", epno);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                v.getContext().startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //view holder class


    public class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView castImg;
        private TextView castName;

        private TextView castRolename;

        private TextView castDepartment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //here use xml ids
            //give different name not like constructor

            castImg = itemView.findViewById(R.id.starcastImageID);
            castName = itemView.findViewById(R.id.starcastnameID);
            castRolename = itemView.findViewById(R.id.starCastRolenameID);
            castDepartment = itemView.findViewById(R.id.starCastDepartmentID);


        }

        public void setData(String name, String rolename, String dept, String castImgLink) {

            Picasso.get().load(castImgLink).into(castImg);
//            if (context!=null){
//                Glide.with(context).load(imagelink).placeholder(shimmerDrawable).into(Epimage);
//            }

            castName.setText(name);
            castRolename.setText(rolename);
            castDepartment.setText(dept);

        }
    }
}

