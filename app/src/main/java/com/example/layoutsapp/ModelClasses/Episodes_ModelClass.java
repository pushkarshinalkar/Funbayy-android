package com.example.layoutsapp.ModelClasses;

import android.content.Context;

public class Episodes_ModelClass {

    private String Epimageviewlink;
    private String Epname1;
    private String Epdate1;
    private String Epdesc1;
    private String EpNo;
    private String EpSeasonNo;
    private String EptmdbID;
    private Context Epcontext;
    //new code



    public Episodes_ModelClass(String NameText, String DateText, String DescText, String imageviewlink, String EpisodeNo, String SeasonNo, String TmdbID, Context contextofep)
    {
        this.Epimageviewlink=imageviewlink;
        this.Epname1=NameText;
        this.Epdate1=DateText;
        this.Epdesc1=DescText;
        this.EpNo=EpisodeNo;
        this.EpSeasonNo=SeasonNo;
        this.EptmdbID = TmdbID;
        this.Epcontext = contextofep;
    }

//    public int getImageview() {
//        return imageview;
//    }

    public String getNameText() {
        return Epname1;
    }

    public String getDateText()
    {
        return Epdate1;
    }


    public String getDescText() {
        return Epdesc1;
    }

    public  String getImageLink(){
        return  Epimageviewlink;
    }

    public  String getEpNo(){return EpNo; }

    public  String getEpSeasonNo(){return EpSeasonNo; }

    public  String getEptmdbID(){return EptmdbID; }

    public  Context getEpcontext(){return Epcontext; }



}
