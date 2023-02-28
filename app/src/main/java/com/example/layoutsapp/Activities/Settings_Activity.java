package com.example.layoutsapp.Activities;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.layoutsapp.Database.AppDatabase;
import com.example.layoutsapp.Database.AppDatabaseSer;
import com.example.layoutsapp.Needed_Classes.BottomSheet_DonateUs_Dialog;
import com.example.layoutsapp.Needed_Classes.BottomSheet_RateUs_Dialog;
import com.example.layoutsapp.R;
import com.example.layoutsapp.Needed_Classes.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;


public class Settings_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.settings_activity);
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        SharedPref sharedpref;
        private Dialog dialog;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            if (getContext()!=null){   // just to remove warning that getContext might return null
                sharedpref = new SharedPref(getContext());
            }

            if (sharedpref.loadNightModeState()==null){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }
            else if (sharedpref.loadNightModeState()){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

//            MainActivity mainSettingsActivity = new MainActivity();
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.custom_dialog_layout);
            dialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.custom_dialog_background));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false); //Optional
            // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

            Button Okay = dialog.findViewById(R.id.btn_okay);
            Button Cancel = dialog.findViewById(R.id.btn_cancel);

            Okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences pref = getActivity().getSharedPreferences("mainSharedPrefFile", Context.MODE_PRIVATE);
                    pref.edit().clear().apply();
                    AppDatabaseSer db  = AppDatabaseSer.getDbInstance(getActivity().getApplicationContext());
                    db.clearAllTables();
                    AppDatabase dbmov  = AppDatabase.getDbInstance(getActivity().getApplicationContext());
                    dbmov.clearAllTables();

                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    startActivity(intent);
                }
            });

            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Preference usePINpref = (Preference) findPreference("usePINkey");
            if (usePINpref!=null){
                usePINpref.setOnPreferenceChangeListener((preference, newValue) -> {

                    boolean isOn = (boolean) newValue;
                    sharedpref.setUsePIN(isOn);
                    if (isOn){
                        Toasty.info(getContext(),"Security PIN Enabled").show();
                    }else {
                        Toasty.info(getContext(),"Security PIN Disabled").show();
                    }
                    return true;
                });
            }
            Preference switchpref = (Preference) findPreference("mode");
            if (switchpref != null) {   // just for removing warning

                //            switchpref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                //                @Override
                //                public boolean onPreferenceChange(Preference preference, Object newValue) {

                switchpref.setOnPreferenceChangeListener((preference, newValue) -> {    // Used lambda instead of above code
                    boolean isOn = (boolean) newValue;
                    if (isOn) {

                        Toasty.info(getContext(),"Dark mode Enabled").show();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        sharedpref.setNightModeState(true);

                    } else {
                        Toasty.info(getContext(),"Dark mode Disabled").show();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        sharedpref.setNightModeState(false);
                    }
                    return true;
                });
            }

        }




        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
//            String key = preference.getKey();
            if (preference.getKey().equals("openSettings")) {

                Intent intent = new Intent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    if (getContext() != null) {  // just for removing warning
                        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
                    }
                } else {
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    if (getContext() != null) {   // just for removing warning
                        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
                    }
                }
                getContext().startActivity(intent);
                return true;
            }else if (preference.getKey().equals("Logout")){
                dialog.show();
            }else if (preference.getKey().equals("donatekey")){
                BottomSheet_DonateUs_Dialog bottomSheet = new BottomSheet_DonateUs_Dialog();
                bottomSheet.show(getActivity().getSupportFragmentManager(),
                        "RateUsbottomDialog");
            }else if (preference.getKey().equals("privacy")){
                showWebsite();
            }
            else if (preference.getKey().equals("otherappkey")){
                showWebsiteOtherApps();
            }
            return false;
        }

        public void showWebsite() {
            RequestQueue requestQueue;

            String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

            requestQueue = Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        String websiteLink = response.getString("Privacy_WebsitePage_Link");
                        Uri uri = Uri.parse(websiteLink);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    } catch (
                            JSONException e) {
                        Toasty.warning(getContext(), "Something went wrong").show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Toasty.warning(context,"No Internet Connection2").show();

                }
            });
            requestQueue.add(jsonObjectRequest);
        }

        public void showWebsiteOtherApps() {
            RequestQueue requestQueue;

            String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

            requestQueue = Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        String websiteLink = response.getString("OtherApps_WebsitePage_Link");
                        Uri uri = Uri.parse(websiteLink);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    } catch (
                            JSONException e) {
                        Toasty.warning(getContext(), "Something went wrong").show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Toasty.warning(context,"No Internet Connection2").show();

                }
            });
            requestQueue.add(jsonObjectRequest);
        }

    }



}