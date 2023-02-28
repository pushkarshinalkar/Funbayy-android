package com.example.layoutsapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.layoutsapp.Fragments.Anime_Movies_Fragment;
import com.example.layoutsapp.Fragments.Anime_Series_Fragment;
import com.example.layoutsapp.Fragments.Movies_Popular_Fragment;
import com.example.layoutsapp.Fragments.Movies_TopRated_Fragment;
import com.example.layoutsapp.Fragments.Movies_Upcoming_Fragment;
import com.example.layoutsapp.Fragments.Profile_Fragment;
import com.example.layoutsapp.Fragments.Series_OnTheAir_Fragment;
import com.example.layoutsapp.Fragments.Series_Popular_Fragment;
import com.example.layoutsapp.Fragments.Series_TopRated_Fragment;
import com.example.layoutsapp.Needed_Classes.BottomSheetProviderDialog;
import com.example.layoutsapp.Needed_Classes.BottomSheet_RateUs_Dialog;
import com.example.layoutsapp.R;
import com.example.layoutsapp.Needed_Classes.SharedPref;
import com.example.layoutsapp.SideMenu.FAQ_Side_Activity;
import com.example.layoutsapp.SideMenu.HowTo_Side_Activity;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.makeramen.roundedimageview.RoundedImageView;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import es.dmoral.toasty.Toasty;

import static android.service.controls.ControlsProviderService.TAG;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    ChipNavigationBar chipNavigationBar;
    DrawerLayout drawerLayout;
    FloatingActionButton floatingActionButton;
    FloatingActionButton floatingActionButtonSettings;
    SharedPref sharedpref;
    FrameLayout mainFrameContainer;
    FrameLayout replaceProfile;
    ChipGroup movChipGroup;
    ChipGroup seriesChipGroup;
    ChipGroup animeChipGroup;

    ConstraintLayout constraintLayoutchangeCol;


    SearchView searchViewMovies;
    SearchView searchViewSeries;
    TextView profileDisplayName;
    RoundedImageView fbImagedisplay;

    TextView funbayyName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        sharedpref = new SharedPref(this);

        fbImagedisplay = findViewById(R.id.roundedImageView);
        funbayyName = findViewById(R.id.funbaynameMainID);
        funbayyName.setVisibility(View.INVISIBLE);
        mainFrameContainer = findViewById(R.id.frameContainerLists);
        searchViewMovies = findViewById(R.id.searchViewMovies);
        searchViewSeries = findViewById(R.id.searchViewSeries);
        movChipGroup = findViewById(R.id.chipGroupMovieID);
        seriesChipGroup = findViewById(R.id.chipGroupSeriesID);
        animeChipGroup = findViewById(R.id.chipGroupAnimeID);

        constraintLayoutchangeCol = findViewById(R.id.constraintLayMainAc);
        profileDisplayName = findViewById(R.id.profileDisplayName);
        String usernameDisplay = "@" + sharedpref.getNameFromLogIn();
        profileDisplayName.setText(usernameDisplay);
        profileDisplayName.setVisibility(View.GONE);
        fbImagedisplay.setVisibility(View.GONE);

        getClearPrefAndUpdate();
        seriesChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Fragment fragment = null;
                if (checkedId == R.id.chipPopularSeries) {
                    fragment = new Series_Popular_Fragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameContainerLists,
                                    fragment).commit();
                } else if (checkedId == R.id.chipTopRatedSeries) {
                    fragment = new Series_TopRated_Fragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameContainerLists,
                                    fragment).commit();
                } else if (checkedId == R.id.chipUpcomingSeries) {
                    fragment = new Series_OnTheAir_Fragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameContainerLists,
                                    fragment).commit();
                }

            }
        });
        movChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Fragment fragment = null;
                if (checkedId == R.id.chipPopular) {
//                    chipMov1.setChipBackgroundColorResource(android.R.color.holo_purple);
                    fragment = new Movies_Popular_Fragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameContainerLists,
                                    fragment).commit();
                } else if (checkedId == R.id.chipTopRated) {
                    fragment = new Movies_TopRated_Fragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameContainerLists,
                                    fragment).commit();
                } else if (checkedId == R.id.chipUpcoming) {
                    fragment = new Movies_Upcoming_Fragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameContainerLists,
                                    fragment).commit();
                }

            }
        });


        animeChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Fragment fragment = null;
                if (checkedId == R.id.chipAnimeMovies) {
//                    chipMov1.setChipBackgroundColorResource(android.R.color.holo_purple);
                    fragment = new Anime_Movies_Fragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameContainerLists,
                                    fragment).commit();
                } else if (checkedId == R.id.chipAnimeSeries) {
                    fragment = new Anime_Series_Fragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameContainerLists,
                                    fragment).commit();
                }

            }
        });

        if (sharedpref.loadNightModeState() == null) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (sharedpref.loadNightModeState()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        chipNavigationBar = findViewById(R.id.ChipBottom);
        chipNavigationBar.setItemSelected(R.id.Movie_frag_button, true);
        drawerLayout = findViewById(R.id.drawerlayout);
        floatingActionButton = findViewById(R.id.floatingActionButton2);
        floatingActionButtonSettings = findViewById(R.id.floatingActionButton);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainerLists,
                new Movies_Popular_Fragment()).commit();
        searchViewSeries.setVisibility(View.GONE);
        bottomMenu();
        seriesChipGroup.setVisibility(View.GONE);
        animeChipGroup.setVisibility(View.GONE);

        setNavigationViewListener();

        searchViewMovies.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SearchResult_Activity.class);
                intent.putExtra("movORser", "Movies");
                intent.putExtra("queryKey", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchViewSeries.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SearchResult_Activity.class);
                intent.putExtra("movORser", "Series");
                intent.putExtra("queryKey", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

//        if (previousFrag!=null){
//            switch (previousFrag){
//                case "movie":{
//
//                }
//                case "series":{
//
//                }
//                case "anime":{
//
//                }
//                case "profile":{
//
//                }
//            }
//        }
    }


    private void setNavigationViewListener() {

        NavigationView navigationView = findViewById(R.id.navview);
        navigationView.setItemIconTintList(null);


        Menu menu = navigationView.getMenu();
        //first Title item
        MenuItem tools = menu.findItem(R.id.followUsId);
        SpannableString a = new SpannableString(tools.getTitle());
        a.setSpan(new TextAppearanceSpan(this, R.style.SideMenuTitleText), 0, a.length(), 0);
        tools.setTitle(a);
        //Second Title item
        MenuItem tools2 = menu.findItem(R.id.HelpId);
        SpannableString b = new SpannableString(tools2.getTitle());
        b.setSpan(new TextAppearanceSpan(this, R.style.SideMenuTitleText), 0, b.length(), 0);
        tools2.setTitle(b);
        //Second Title item
        MenuItem tools3 = menu.findItem(R.id.WhatsNewId);
        SpannableString c = new SpannableString(tools3.getTitle());
        c.setSpan(new TextAppearanceSpan(this, R.style.SideMenuTitleText), 0, c.length(), 0);
        tools3.setTitle(c);
        //Second Title item
        MenuItem tools4 = menu.findItem(R.id.FeedbackId);
        SpannableString d = new SpannableString(tools4.getTitle());
        d.setSpan(new TextAppearanceSpan(this, R.style.SideMenuTitleText), 0, d.length(), 0);
        tools4.setTitle(d);


        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.Inst_button) {
            RequestQueue requestQueue;

            String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

            requestQueue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        String InstaLink = response.getString("Instagram_Link");
                        Uri uri = Uri.parse(InstaLink);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    } catch (
                            JSONException e) {
                        Toasty.warning(MainActivity.this, "Something went wrong").show();
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

        } else if (itemId == R.id.Twitter_button) {
            showWebsite();

        } else if (itemId == R.id.YouTube_button) {
            RequestQueue requestQueue;

            String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

            requestQueue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        String Discord = response.getString("Discord_Link");
                        Uri uri = Uri.parse(Discord);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    } catch (
                            JSONException e) {
                        Toasty.warning(MainActivity.this, "Something went wrong").show();
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

        } else if (itemId == R.id.How_to) {
            Intent intent = new Intent(MainActivity.this, HowTo_Side_Activity.class);
            startActivity(intent);

        } else if (itemId == R.id.FAQ) {
            Intent intent2 = new Intent(MainActivity.this, FAQ_Side_Activity.class);
            startActivity(intent2);

        } else if (itemId == R.id.Updates) {
            RequestQueue requestQueue;

            String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

            requestQueue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        Boolean isUpdateAvailable = response.getBoolean("UpdateAvailable");
                        if (isUpdateAvailable) {
                            Toasty.info(MainActivity.this, "New Update Available\nDownload from website").show();
                        } else {
                            Toasty.info(MainActivity.this, "You are on the latest version").show();
                        }

                    } catch (
                            JSONException e) {
                        Toasty.warning(MainActivity.this, "Something went wrong").show();
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

        } else if (itemId == R.id.Rateus_id) {
            BottomSheet_RateUs_Dialog bottomSheet = new BottomSheet_RateUs_Dialog();
            bottomSheet.show(getSupportFragmentManager(),
                    "RateUsbottomDialog");

        } else if (itemId == R.id.Report_id) {

            RequestQueue requestQueue;

            String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

            requestQueue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        String mailid = response.getString("Gmail_Link");
                        String[] addresses = {mailid};
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:"));
                        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Bugs in Funbayy");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent);

                    } catch (
                            JSONException e) {
                        Toasty.warning(MainActivity.this, "Something went wrong").show();
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
        return true;
    }


    private void bottomMenu() {
//        chipNavigationBar.setOnItemSelectedListener
//                (new ChipNavigationBar.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(int i) {
        chipNavigationBar.setOnItemSelectedListener
                (i -> {
                    Fragment fragment=null;
                    if (i == R.id.Movie_frag_button) {
                        constraintLayoutchangeCol.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.loginBackground));

                        profileDisplayName.setVisibility(View.GONE);
                        fbImagedisplay.setVisibility(View.GONE);
                        funbayyName.setVisibility(View.INVISIBLE);
                        searchViewSeries.setVisibility(View.GONE);
                        searchViewMovies.setVisibility(View.VISIBLE);
                        seriesChipGroup.setVisibility(View.GONE);
                        movChipGroup.setVisibility(View.VISIBLE);
                        animeChipGroup.setVisibility(View.GONE);
                        fragment = new Movies_Popular_Fragment();
                    } else if (i == R.id.Series_frag_button) {
                        constraintLayoutchangeCol.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.loginBackground));

                        profileDisplayName.setVisibility(View.GONE);
                        fbImagedisplay.setVisibility(View.GONE);
                        funbayyName.setVisibility(View.GONE);
                        searchViewSeries.setVisibility(View.VISIBLE);
                        searchViewMovies.setVisibility(View.GONE);
                        seriesChipGroup.setVisibility(View.VISIBLE);
                        movChipGroup.setVisibility(View.GONE);
                        animeChipGroup.setVisibility(View.GONE);
                        fragment = new Series_Popular_Fragment();
                    } else if (i == R.id.Upcoming_frag_button) {
                        constraintLayoutchangeCol.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.loginBackground));

                        profileDisplayName.setVisibility(View.GONE);
                        fbImagedisplay.setVisibility(View.GONE);
                        funbayyName.setVisibility(View.VISIBLE);
                        searchViewSeries.setVisibility(View.GONE);
                        searchViewMovies.setVisibility(View.GONE);
                        seriesChipGroup.setVisibility(View.GONE);
                        movChipGroup.setVisibility(View.GONE);
                        animeChipGroup.setVisibility(View.VISIBLE);
                        fragment = new Anime_Movies_Fragment();
                    } else if (i == R.id.Account_frag_button) {

                        funbayyName.setVisibility(View.GONE);

                        searchViewSeries.setVisibility(View.GONE);
                        searchViewMovies.setVisibility(View.GONE);
                        seriesChipGroup.setVisibility(View.GONE);
                        movChipGroup.setVisibility(View.GONE);
                        animeChipGroup.setVisibility(View.GONE);

                        fragment = new Profile_Fragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameContainerLists,
                                        fragment).commit();
                        fragment = null;
                        fbImagedisplay.setVisibility(View.VISIBLE);
                        constraintLayoutchangeCol.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.bottomNavCol));
                        profileDisplayName.setVisibility(View.VISIBLE);
                    }
                    if (fragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameContainerLists,
                                        fragment).commit();
                    }
//
                });
    }


    public void settingsCode(View view) {

        Intent intent = new Intent(MainActivity.this, Settings_Activity.class);
        startActivity(intent);
    }


    public void sidemenucode(View view) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else drawerLayout.openDrawer(GravityCompat.START);


        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {


            @Override
            public void onDrawerOpened(@NonNull @NotNull View drawerView) {

                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_backbutt_icon));


            }

            @Override
            public void onDrawerClosed(@NonNull @NotNull View drawerView) {
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_sidemenu));

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }


    public void getClearPrefAndUpdate() {
        String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";


        RequestQueue requestQueue;


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean clearPref=false;
                boolean updateAvailable=false;
                try {

                    clearPref = response.getBoolean("ClearSharedPref");
                    updateAvailable = response.getBoolean("UpdateAvailable");
//                    Toast.makeText(MainActivity.this,String.valueOf(updateAvailable) , Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Toasty.warning(MainActivity.this, "Something went wrong").show();
                    e.printStackTrace();
                }
                if (updateAvailable){
                    showDialogUpdateDownload();
                }
                if(clearPref){
//                    Toast.makeText(MainActivity.this, sharedpref.getApiKey(), Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = MainActivity.this.getSharedPreferences("mainSharedPrefFile", Context.MODE_PRIVATE);
                    pref.edit().clear().apply();
                    Toasty.error(MainActivity.this,"Failed to get Verification from server").show();
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

    public void showWebsite() {
        RequestQueue requestQueue;

        String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String websiteLink = response.getString("Website_Link");
                    Uri uri = Uri.parse(websiteLink);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                } catch (
                        JSONException e) {
                    Toasty.warning(MainActivity.this, "Something went wrong").show();
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

    public void showDialogUpdateDownload(){
        Dialog dialog;
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog_download_availabe);
        dialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(MainActivity.this,R.drawable.custom_dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay_UPdownload);
        Button Cancel = dialog.findViewById(R.id.btn_cancel_UPdownload);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWebsite();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //    @Override
//    protected void onDestroy() {
////        deleteCache(this);
//        super.onDestroy();
//    }
//
//    public static void deleteCache(Context context) {
//        try {
//            File dir = context.getCacheDir();
//            deleteDir(dir);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//            return dir.delete();
//        } else if (dir != null && dir.isFile()) {
//            return dir.delete();
//        } else {
//            return false;
//        }
//    }
}
