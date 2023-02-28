package com.example.layoutsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import com.example.layoutsapp.R;
import com.example.layoutsapp.Needed_Classes.SharedPref;

public class SplashScreen_Activity extends AppCompatActivity {

    //    RoundedImageView splashLogo;
    SharedPref sharedpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        sharedpref = new SharedPref(this);

        if (sharedpref.loadNightModeState()==null){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        else if (sharedpref.loadNightModeState()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        if (!sharedpref.getPinFromLogIn().equals("")) {
            if (sharedpref.getUsePIN()) {
                Intent ne = new Intent(SplashScreen_Activity.this, Login_Activity.class);
                startActivity(ne);
                finish();
            }
            else {
                Intent intent = new Intent(SplashScreen_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else {
            Intent intent = new Intent(SplashScreen_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish();
        }
//        splashLogo = findViewById(R.id.roundedImageViewSplash);
//        SharedPref shared3 = new SharedPref(SplashScreen.this);
//        if (shared3.loadNightModeState()){
//            splashLogo.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fb_icon_dark));
//
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent ne = new Intent(SplashScreen.this,MainActivity.class);
//                startActivity(ne);
//                finish();
//
//            }
//        },3000);
//    }
    }
}