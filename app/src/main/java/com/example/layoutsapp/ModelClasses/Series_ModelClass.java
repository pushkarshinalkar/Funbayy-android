package com.example.layoutsapp.ModelClasses;


import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Series_ModelClass {

    private String Epimageviewlink;
    private String Epname1;
    //    private String Epdate1;
//    private String Epdesc1;
    private String EpNo;

    private Context movImgContext;
    //new code



    public Series_ModelClass(String NameText, String imageviewlink, String EpisodeNo, Context imgContext)
    {
        this.Epimageviewlink=imageviewlink;
        this.Epname1=NameText;
//        this.Epdate1=DateText;
//        this.Epdesc1=DescText;
        this.EpNo=EpisodeNo;
        this.movImgContext=imgContext;
    }

//    public int getImageview() {
//        return imageview;
//    }

    public String getNameText() {
        return Epname1;
    }

//    public String getDateText()
//    {
//        return Epdate1;
//    }
//
//
//    public String getDescText() {
//        return Epdesc1;
//    }

    public  String getImageLink(){
        return  Epimageviewlink;
    }

    public  String getEpNo(){return EpNo; }
    public  Context getimgContext(){return movImgContext; }



}

