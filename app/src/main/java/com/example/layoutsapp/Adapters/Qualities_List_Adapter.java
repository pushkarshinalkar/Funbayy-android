package com.example.layoutsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.layoutsapp.ModelClasses.Quality_ModelClass;
import com.example.layoutsapp.Needed_Classes.BottomSheetDialog;
import com.example.layoutsapp.Needed_Classes.BottomSheetProviderDialog;
import com.example.layoutsapp.R;
import com.example.layoutsapp.WebView.Movies_Download_Webview_Dialog;
import com.example.layoutsapp.WebView.Movies_Server2_Webview;
import com.example.layoutsapp.WebView.Movies_WatchNow_Webview;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Qualities_List_Adapter extends RecyclerView.Adapter<Qualities_List_Adapter.ViewHolder> {


    private List<Quality_ModelClass> userList;

    public Qualities_List_Adapter(List<Quality_ModelClass> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public Qualities_List_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design_qualities, parent, false);
        return new Qualities_List_Adapter.ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull Qualities_List_Adapter.ViewHolder holder, int position) {


        String type = userList.get(position).getType();
        Context context = userList.get(position).getContext();
        String quality = userList.get(position).getQuality();
        String size = userList.get(position).getSize();

        String magnetlink = userList.get(position).getMagnetLink();
        Boolean isDownloadType = userList.get(position).getIsDownloadType();

        holder.setData(type, quality, size,magnetlink);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDownloadType){
                    Bundle bundle = new Bundle();
                    bundle.putString("MagnetIDdownload", magnetlink );
                    BottomSheetDialogFragment bottomSheetDialogFragment = new Movies_Download_Webview_Dialog();
                    bottomSheetDialogFragment.setArguments(bundle);
                    bottomSheetDialogFragment.show(((FragmentActivity)context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                }else {
                    Intent intent = new Intent(v.getContext(), Movies_Server2_Webview.class);
                    intent.putExtra("MagnetID", magnetlink);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Typetextview;
        private TextView Qualitytextview;
        private TextView Sizetextview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //here use xml ids
            //give different name not like constructor

            Typetextview = itemView.findViewById(R.id.TypeQualityTextView);
            Qualitytextview = itemView.findViewById(R.id.qualityNoTextView);
            Sizetextview = itemView.findViewById(R.id.SizeQualityTextView);


        }

        public void setData(String type, String qualityNo, String size,String magnetLink) {

            Typetextview.setText(type);
            Qualitytextview.setText(qualityNo);
            Sizetextview.setText(size);

        }
    }
}
