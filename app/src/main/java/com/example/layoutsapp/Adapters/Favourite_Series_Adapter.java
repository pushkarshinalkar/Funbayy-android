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

import com.example.layoutsapp.Activities.Movies_Description_Activity;
import com.example.layoutsapp.Activities.Series_Description_Activity;
import com.example.layoutsapp.Database.AppDatabase;
import com.example.layoutsapp.Database.AppDatabaseSer;
import com.example.layoutsapp.Database.User;
import com.example.layoutsapp.Database.UserSer;
import com.example.layoutsapp.R;
import com.example.layoutsapp.WebView.Series_WatchNow_Webview;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Favourite_Series_Adapter extends RecyclerView.Adapter<Favourite_Series_Adapter.MyViewHolder> {

    private Context context;
    private List<UserSer> userList;
    public Favourite_Series_Adapter(Context context) {
        this.context = context;
    }

    public void setUserList(List<UserSer> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favourite_movies, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvMovName.setText(this.userList.get(position).seriesName);
        String noofseasonstext = this.userList.get(position).noOfSeasons;
        holder.tvReleaseYr.setText("Total Seasons "+noofseasonstext);
        holder.movID = this.userList.get(position).seriesID;
        String movieIDsend = this.userList.get(position).seriesID;
        String movieNameFirst = this.userList.get(position).seriesName;

        holder.removeFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNewUser(movieNameFirst);
//                upDateList();
                userList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
//        holder.imgLink = this.userList.get(position).imageLink;
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+this.userList.get(position).imageLinkSeriesSmall).into(holder.smallimg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(), movieIDsend, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), Series_Description_Activity.class);
                intent.putExtra("MovieID", movieIDsend);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  this.userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvMovName;
        TextView tvReleaseYr;
        String movID;
        String imgLink;
        ImageView removeFav;
        ImageView smallimg;

        public MyViewHolder(View view) {
            super(view);
            tvMovName = view.findViewById(R.id.item_title);// OR sereis name
            tvReleaseYr = view.findViewById(R.id.item_subText);// OR no of seasons
            removeFav = view.findViewById(R.id.item_remove_favourites);
            smallimg = view.findViewById(R.id.favourite_smallimg);



        }
    }
    private void deleteNewUser(String firstName){
        AppDatabaseSer db  = AppDatabaseSer.getDbInstance(context.getApplicationContext());

//        User user = new User();
        db.userDaoSer().deleteBySeriesUserId(firstName);

//        finish();

    }
}