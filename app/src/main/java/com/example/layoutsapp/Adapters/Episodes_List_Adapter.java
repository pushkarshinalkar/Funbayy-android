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
import com.example.layoutsapp.ModelClasses.Episodes_ModelClass;
import com.example.layoutsapp.R;
import com.example.layoutsapp.WebView.Series_WatchNow_Webview;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Episodes_List_Adapter extends RecyclerView.Adapter<Episodes_List_Adapter.ViewHolder> {

    private List<Episodes_ModelClass> userList;

//    String epposition

    public Episodes_List_Adapter(List<Episodes_ModelClass>userList) {
        this.userList=userList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        int resource = userList.get(position).getImageview();
        String name=userList.get(position).getNameText();
        String date=userList.get(position).getDateText();
        String desc=userList.get(position).getDescText();
        String img =userList.get(position).getImageLink();
        String epno = userList.get(position).getEpNo();
        String epseasonno = userList.get(position).getEpSeasonNo();
        String eptmdbid = userList.get(position).getEptmdbID();
        Context context = userList.get(position).getEpcontext();

        holder.setData(name,date,desc,img,epno,context);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), epno, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), Series_WatchNow_Webview.class);
                intent.putExtra("EpisodeNo",epno);
                intent.putExtra("SeasonNo",epseasonno);
                intent.putExtra("tmdbID",eptmdbid);
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
        private TextView Epdate;
        private TextView Epdesc;
        private TextView Epno;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //here use xml ids
            //give different name not like constructor
            Epimage=itemView.findViewById(R.id.cardimageEpisodeitem);
            Epdate = itemView.findViewById(R.id.datetextItemcard);
            Epdesc = itemView.findViewById(R.id.itemcarddesctext);
            Epname = itemView.findViewById(R.id.episodename);
            Epno = itemView.findViewById(R.id.episodeNoid);


        }

        public void setData(String name, String date, String desc,String imagelink,String epno,Context context) {

            Picasso.get().load(imagelink).into(Epimage);
//            if (context!=null){
//                Glide.with(context).load(imagelink).transition(DrawableTransitionOptions.withCrossFade()).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).into(Epimage);
//            }


            Epname.setText(name);
            Epdate.setText(date);
            Epdesc.setText(desc);
            Epno.setText(epno);


        }
    }
}
