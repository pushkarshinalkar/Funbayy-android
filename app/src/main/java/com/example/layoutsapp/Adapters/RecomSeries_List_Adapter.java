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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.layoutsapp.Activities.Series_Description_Activity;
import com.example.layoutsapp.ModelClasses.Series_ModelClass;
import com.example.layoutsapp.R;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecomSeries_List_Adapter extends RecyclerView.Adapter<RecomSeries_List_Adapter.ViewHolder> {

    private List<Series_ModelClass> userList;

//    String epposition

    public RecomSeries_List_Adapter(List<Series_ModelClass> userList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design_recommendations, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        int resource = userList.get(position).getImageview();
        String name = userList.get(position).getNameText();
//        String date=userList.get(position).getDateText();
//        String desc=userList.get(position).getDescText();
        String img = userList.get(position).getImageLink();
        String epno = userList.get(position).getEpNo();

        Context context = userList.get(position).getimgContext();

        holder.setData(name, img, epno, context);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), epno, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), Series_Description_Activity.class);
                intent.putExtra("MovieID", epno);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //view holder class


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView Epimage;
        private TextView Epname;
        //        private TextView Epdate;
//        private TextView Epdesc;
        private TextView Epno;

        private Context imgcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //here use xml ids
            //give different name not like constructor
            Epimage = itemView.findViewById(R.id.cardimageEpisodeitemReco);
//            Epdate = itemView.findViewById(R.id.datetextItemcard);
//            Epdesc = itemView.findViewById(R.id.itemcarddesctext);
            Epname = itemView.findViewById(R.id.episodenameReco);
//            Epno = itemView.findViewById(R.id.episodeNoid);


        }

        public void setData(String name, String imagelink, String epno, Context context) {

            Picasso.get().load(imagelink).into(Epimage);
//            if (context!=null){
//                Glide.with(context).load(imagelink).transition(DrawableTransitionOptions.withCrossFade()).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).into(Epimage);
//            }



            Epname.setText(name);
//            Epdate.setText(date);
//            Epdesc.setText(desc);
//            Epno.setText(epno);


        }
    }
}
