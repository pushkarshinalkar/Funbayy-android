package com.example.layoutsapp.Needed_Classes;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    // TODO: Make decrypting logic correct // Put default links as encrypted links

    Context globalContext;
    SharedPreferences mySharedPref;

    public SharedPref(Context context) {
        globalContext = context;
        mySharedPref = context.getSharedPreferences("mainSharedPrefFile", Context.MODE_PRIVATE);
    }

    // this method will save the nightMode State : True or False
    public void setNightModeState(Boolean state) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }

    // this method will load the Night Mode State
    public Boolean loadNightModeState() {
        return mySharedPref.getBoolean("NightMode", false);
    }


    public void setNameFromLogIn(String name){
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("setLoginPrefNameKey",name);
        editor.apply();
    }

    public String getNameFromLogIn(){

        return mySharedPref.getString("setLoginPrefNameKey","");
    }

    public void setNameFromProfile(String name){
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("setLoginPrefNameKey",name);
        editor.apply();
    }

    public String getNameFromProfile(){

        return mySharedPref.getString("setLoginPrefNameKey","");
    }

    public void setPinFromLogIn(String name){
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("setLoginPrefPinKey",name);
        editor.apply();
    }

    public String getPinFromLogIn(){
        return mySharedPref.getString("setLoginPrefPinKey","");
    }

    public void setUsePIN(Boolean state){
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("setUsePINkey",state);
        editor.apply();
    }

    public Boolean getUsePIN() {
        return mySharedPref.getBoolean("setUsePINkey",true);
    }



    public void setIsfirstInstll(boolean firstinstall) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("isfirstinstall",firstinstall);
        editor.apply();
    }

    public boolean getIsfirstInstall(){
        return mySharedPref.getBoolean("isfirstinstall",false);
    }

    public void setProfileMail(String mailtext) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("profileMailKey",mailtext);
        editor.apply();
    }

    public String getProfileMail(){
        return mySharedPref.getString("profileMailKey","");
    }

    public void setProfilePhoneNo(String PhoneNo) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("profilePhoneNoKey",PhoneNo);
        editor.apply();
    }

    public String getProfilePhoneNo(){
        return mySharedPref.getString("profilePhoneNoKey","");
    }

    public void setPopularMovLink(String MovLink) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("PopMovLink",MovLink);
        editor.apply();
    }

    public void setTopRatedMovLink(String MovLink) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("TopMovLink",MovLink);
        editor.apply();
    }

    public void setUpcomingMovLink(String MovLink) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("UpMovLink",MovLink);
        editor.apply();
    }

    public String getPopularMovLink(){
        String sentence = mySharedPref.getString("PopMovLink","");;
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }

    public String getTopRatedMovLink(){
        String sentence = mySharedPref.getString("TopMovLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }

    public String getUpcomingMovLink(){
        String sentence = mySharedPref.getString("UpMovLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }

    public void setPopularSerLink(String MovLink) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("PopSerLink",MovLink);
        editor.apply();
    }

    public void setOnTheAirSerLink(String MovLink) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("UpSerLink",MovLink);
        editor.apply();
    }

    public void setTopRatedSerLink(String MovLink) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("TopSerLink",MovLink);
        editor.apply();
    }

    public String getPopularSerLink(){
        String sentence = mySharedPref.getString("PopSerLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }

    public String getOnTheAirSerLink(){
        String sentence =  mySharedPref.getString("UpSerLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }

    public String getTopRatedSerLink(){
        String sentence =  mySharedPref.getString("TopSerLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }

    public void setAnimeMovLink(String MovLink) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("AniMovLink",MovLink);
        editor.apply();
    }

    public void setAnimeSerLink(String MovLink) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("AniSerLink",MovLink);
        editor.apply();
    }

    public String getAnimeMovLink(){
        String sentence =  mySharedPref.getString("AniMovLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }

    public String getAnimeSerLink(){
        String sentence =   mySharedPref.getString("AniSerLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");

    }

    public void setWatchnowMovieLink(String MovLink) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("WatchnowMovLink",MovLink);
        editor.apply();
    }



    public void setWatchnowSeriesLink(String SerLink) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("WatchnowSerLink",SerLink);
        editor.apply();
    }

    public String getWatchnowSeriesLink(){
        String sentence =  mySharedPref.getString("WatchnowSerLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }
    public String getWatchnowMovieLink(){
        String sentence =  mySharedPref.getString("WatchnowMovLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }

    public void setMoviesDescLink(String Link) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("MovDescLink",Link);
        editor.apply();
    }

    public void setSeriesDescLink(String Link) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("SerDescLink",Link);
        editor.apply();
    }
    public String getMoviesDescLink(){
        String sentence =  mySharedPref.getString("MovDescLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }
    public String getSeriesDescLink(){
        String sentence =  mySharedPref.getString("SerDescLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }

    public void setApiKey(String Link) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("Api_key",Link);
        editor.apply();
    }

    public String getApiKey(){
        String sentence = mySharedPref.getString("Api_key","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }


    public void setMovSearchLink(String Link) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("MovSearchLink",Link);
        editor.apply();
    }

    public String getMovSearchLink(){
        String sentence = mySharedPref.getString("MovSearchLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }

    public void setSerSearchLink(String Link) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("SerSearchLink",Link);
        editor.apply();
    }

    public String getSerSearchLink(){
        String sentence = mySharedPref.getString("SerSearchLink","");
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }

    public void setDownloadingMovieName(String MovieName) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("DownloadNameID",MovieName);
        editor.apply();
    }

    public String getDownloadingMovieName(){
        return mySharedPref.getString("DownloadNameID","unknown");
    }

//    public void setPreviousFrag(String prevFrag) {
//        SharedPreferences.Editor editor = mySharedPref.edit();
//        editor.putString("previousFragKey",prevFrag);
//        editor.apply();
//    }
//
//    public String getPreviousFrag(){
//        return mySharedPref.getString("previousFragKey","movies");
//    }













}

