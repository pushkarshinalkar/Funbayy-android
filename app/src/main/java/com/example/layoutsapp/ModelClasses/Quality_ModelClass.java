package com.example.layoutsapp.ModelClasses;

import android.content.Context;

import com.example.layoutsapp.Activities.MainActivity;

public class Quality_ModelClass {

    private String Type;
    private String Quality;
    private String Size;
    private String MagnetLink;
    private Boolean IsDownloadType;
    private Context context;

    //new code



    public Quality_ModelClass(String type, String quality, String size, String magnetLink,Boolean isDownloadType,Context context)
    {
        this.Type=type;
        this.Quality=quality;
        this.Size=size;
        this.MagnetLink=magnetLink;
        this.IsDownloadType=isDownloadType;
        this.context=context;
    }


    public String getType() {
        return Type;
    }

    public  String getQuality(){
        return  Quality;
    }

    public  String getSize(){return Size; }
    public  String getMagnetLink(){return MagnetLink; }
    public  Boolean getIsDownloadType(){return IsDownloadType; }
    public  Context getContext(){return context; }

}
