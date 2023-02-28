package com.example.layoutsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.layoutsapp.Adapters.Episodes_List_Adapter;
import com.example.layoutsapp.Adapters.RecomMovies_List_Adapter;
import com.example.layoutsapp.Adapters.RecomSeries_List_Adapter;
import com.example.layoutsapp.Adapters.StarCast_List_Adapter;
import com.example.layoutsapp.Database.AppDatabase;
import com.example.layoutsapp.Database.AppDatabaseSer;
import com.example.layoutsapp.Database.User;
import com.example.layoutsapp.Database.UserSer;
import com.example.layoutsapp.ModelClasses.Episodes_ModelClass;
import com.example.layoutsapp.ModelClasses.Favourites_ModelClass;
import com.example.layoutsapp.ModelClasses.Movies_ModelClass;
import com.example.layoutsapp.ModelClasses.Series_ModelClass;
import com.example.layoutsapp.ModelClasses.StarCast_ModelClass;
import com.example.layoutsapp.Needed_Classes.BottomSheetProviderDialog;
import com.example.layoutsapp.Needed_Classes.SharedPref;
import com.example.layoutsapp.R;
import com.example.layoutsapp.WebView.Movies_Trailer_Webview;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Series_Description_Activity extends AppCompatActivity {

    ToggleButton buttonFavorite1;

    ConstraintLayout descConst;

    List<Favourites_ModelClass> FavList;

    RecyclerView mrecyclerView;
    LinearLayoutManager layoutManager;
    List<Episodes_ModelClass> userList;
    Episodes_List_Adapter adapter;

    TextView movienamee;

    String seriesNamewithoutPlus;

    ToggleButton downloadbtnToggleSeries;

    Context mainContext = this;

    Button tralorbutt;

    ImageView movBigimg;


String smallSerImglink;
    TextView movDecrip;


    String MovieNo;
    String movName;


    int noOfSeasons;

    String defUrl;
    String defApiKey;
    List<StarCast_ModelClass> userListSC;
    StarCast_List_Adapter adapterSC;
    RecyclerView recyclerViewStarCast;

    List<Series_ModelClass> userListReco;
    RecomSeries_List_Adapter adapterReco;
    RecyclerView recyclerViewReco;

    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_series_activity);

        SharedPref shared1 = new SharedPref(Series_Description_Activity.this);
        if (shared1.loadNightModeState()==null){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        else if (shared1.loadNightModeState()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        MovieNo = getIntent().getStringExtra("MovieID");
        AppDatabaseSer db  = AppDatabaseSer.getDbInstance(this.getApplicationContext());
        autoCompleteTextView = findViewById(R.id.autoCompletespinnerTextSer);
        descConst = findViewById(R.id.descripConstraintSer);
        buttonFavorite1 = findViewById(R.id.button_favorite1Ser);
        tralorbutt = findViewById(R.id.trailorButtSer);
        movienamee = findViewById(R.id.nameofmovieSer);
        movBigimg = findViewById(R.id.MovieBigDescImgSer);
        movDecrip = findViewById(R.id.movieDecriptionTextSer);
        downloadbtnToggleSeries = findViewById(R.id.downloadBTNtoggleSeries);

        userListSC = new ArrayList<>();
        recyclerViewStarCast = findViewById(R.id.starcastRecyclerViewIDSer);
        recyclerViewStarCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterSC = new StarCast_List_Adapter(userListSC);
        recyclerViewStarCast.setAdapter(adapterSC);

        userListReco = new ArrayList<>();
        recyclerViewReco = findViewById(R.id.recomendationsRecyclerViewIDSer);
        recyclerViewReco.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterReco = new RecomSeries_List_Adapter(userListReco);
        recyclerViewReco.setAdapter(adapterReco);

        boolean isLiked = db.userDaoSer().isSeriesLiked(MovieNo);
        if (isLiked){
            buttonFavorite1.setChecked(true);
        }

        if (shared1.getSeriesDescLink().equals("")){
            String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(mainContext);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        defUrl = response.getString("Series_Desc_url");
                        defApiKey = response.getString("Api_key");
                        shared1.setSeriesDescLink(defUrl);
                        shared1.setApiKey(defApiKey);

                        String url = decriptFromApi(defUrl)+MovieNo+"?api_key="+decriptFromApi(defApiKey);
                        String urlCast = decriptFromApi(defUrl)+MovieNo+"/credits?api_key="+decriptFromApi(defApiKey);
                        String urlReco = decriptFromApi(defUrl)+MovieNo+"/recommendations?api_key="+decriptFromApi(defApiKey)+"&language=en-US&page=1";

                        getBasicDetailsApi(url,defUrl,defApiKey);
                        getCastDetailsApi(urlCast);
                        getRecomDetailsApi(urlReco);

                    } catch (JSONException e) {
                        Toasty.warning(mainContext, "Something went wrong").show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toasty.warning(mainContext, "No Internet Connection1").show();

                }
            });

            requestQueue.add(jsonObjectRequest);
        }else{
            defUrl = shared1.getSeriesDescLink();
            defApiKey = shared1.getApiKey();
//            shared1.setSeriesDescLink(defUrl);
//            shared1.setApiKey(defApiKey);

            String url = defUrl+MovieNo+"?api_key="+defApiKey;
            String urlCast = defUrl+MovieNo+"/credits?api_key="+defApiKey;
            String urlReco = defUrl+MovieNo+"/recommendations?api_key="+defApiKey+"&language=en-US&page=1";

            getBasicDetailsApi(url,defUrl,defApiKey);
            getCastDetailsApi(urlCast);
            getRecomDetailsApi(urlReco);
        }





        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        buttonFavorite1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                compoundButton.startAnimation(scaleAnimation);
                if (b) {
//                    Toast.makeText(mainContext, MovieNo, Toast.LENGTH_SHORT).show();
                    if (seriesNamewithoutPlus!=null&&smallSerImglink!=null&&MovieNo!=null){
                        saveNewUser(seriesNamewithoutPlus,String.valueOf(noOfSeasons),smallSerImglink,MovieNo);
                    }else {
                        Toasty.error(mainContext,"Series not loaded yet").show();
                        buttonFavorite1.setChecked(false);
                    }
                } else {
//                    Toast.makeText(mainContext, "UnAdded to fav", Toast.LENGTH_SHORT).show();
                    deleteNewUser(seriesNamewithoutPlus);
                }
            }
        });


        downloadbtnToggleSeries.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    Toasty.error(mainContext,"Download of whole season is not available\nPlease download episodes seperatly from video player",1).show();
                }
                compoundButton.startAnimation(scaleAnimation);

            }
        });
        tralorbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainContext, Movies_Trailer_Webview.class);
                intent.putExtra("SerId",MovieNo);
                intent.putExtra("MovOrSer","Ser");
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onDestroy() {
//        deleteCache(this);
//        super.onDestroy();
//    }
//    public static void deleteCache(Context context) {
//        try {
//            File dir = context.getCacheDir();
//            deleteDir(dir);
//        } catch (Exception e) { e.printStackTrace();}
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
//        } else if(dir!= null && dir.isFile()) {
//            return dir.delete();
//        } else {
//            return false;
//        }
//    }

    private void initRecyclerView() {
        mrecyclerView = findViewById(R.id.RecyclerViewEpListSer);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mrecyclerView.setLayoutManager(layoutManager);
        adapter = new Episodes_List_Adapter(userList);
        mrecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private void saveNewUser(String firstName, String lastName,String imglink,String movid) {
        AppDatabaseSer db  = AppDatabaseSer.getDbInstance(this.getApplicationContext());

        UserSer user = new UserSer();
        user.seriesName = firstName;
        user.noOfSeasons = lastName;
        user.imageLinkSeriesSmall = imglink;
        user.seriesID = movid;
        db.userDaoSer().insertUser(user);

//        finish();

    }

    private void deleteNewUser(String firstName){
        AppDatabaseSer db  = AppDatabaseSer.getDbInstance(this.getApplicationContext());

//        User user = new User();
        db.userDaoSer().deleteBySeriesUserId(firstName);

//        finish();

    }

    public void getEP(String urlGetEP,String seasonNo) {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Series_Description_Activity.this);
        JsonObjectRequest jsonObjectRequestRequestEP = new JsonObjectRequest(Request.Method.GET,
                urlGetEP, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    userList = new ArrayList<>();
                    JSONArray arrmain = response.getJSONArray("episodes");
//                    Toast.makeText(mainContext, "No of Ep are"+arrmain.length(), Toast.LENGTH_SHORT).show();
                    for (int i = 0 ; i<arrmain.length();i++){
                        JSONObject op1 = arrmain.getJSONObject(i);
                        String Namefromepisode = op1.getString("name");

                        String Datefromepisode = op1.getString("air_date");

                        String Descfromepisode = op1.getString("overview");

                        String ImageLinkfromepisode = op1.getString("still_path");

                        String EpisodeNo = op1.getString("episode_number");
//                        Toast.makeText(mainContext, ImageLinkfromepisode, Toast.LENGTH_SHORT).show();
                        Context context = Series_Description_Activity.this;
                        userList.add(new Episodes_ModelClass(Namefromepisode, Datefromepisode, Descfromepisode, "https://image.tmdb.org/t/p/w500" + ImageLinkfromepisode, EpisodeNo, seasonNo, MovieNo, context));


                    }
//                    Toast.makeText(mainContext, "Size of User List is"+userList.size(), Toast.LENGTH_SHORT).show();
                    initRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Shubham", "Something went wrong");
//                                                Toast.makeText(WatchNow_Series.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequestRequestEP);
    }

    private void getBasicDetailsApi(String url,String shortDefurl,String apiKey){
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Series_Description_Activity.this);

        JsonObjectRequest jsonObjectRequest5 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String bigimgMov = response.getString("backdrop_path");
                    noOfSeasons = response.getInt("number_of_seasons");
                    smallSerImglink = response.getString("poster_path");
//                    Toast.makeText(mainContext, "No of Seasons is"+noOfSeasons, Toast.LENGTH_SHORT).show();


                    ArrayList<String> arrayList = new ArrayList<>();
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Series_Description_Activity.this, R.layout.list_item_spinner, arrayList);

                    autoCompleteTextView.setAdapter(arrayAdapter);

                    for (int i = 1; i <=noOfSeasons; i++) {
                        arrayList.add("Season " + i);
                    }

                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    autoCompleteTextView.setAdapter(arrayAdapter);
                    autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String positionval = String.valueOf(position + 1);
                            String SeasonNO = positionval;
//                            Toast.makeText(mainContext, "SeasonNo selecteeed is "+SeasonNO, Toast.LENGTH_SHORT).show();
                            String urlGetEP = shortDefurl+MovieNo+"/season/"+SeasonNO+"?api_key="+apiKey+"&language=en-US";
                            getEP(urlGetEP,SeasonNO);
                        }

                    });
                    Picasso.get().load("https://image.tmdb.org/t/p/w500"+bigimgMov).into(movBigimg);
                    movName = response.getString("name");
                    seriesNamewithoutPlus = movName;
                    movienamee.setText(movName);
                    movName = movName.replaceAll(" ","+");
//                    Toast.makeText(Series_Description_Activity.this, movName, Toast.LENGTH_SHORT).show();
                    movDecrip.setText(response.getString("overview"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("Shubham", "Something went wrong");


            }
        });

        requestQueue.add(jsonObjectRequest5);
    }

    private void getCastDetailsApi(String url){
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(mainContext);
        JsonObjectRequest jsonObjectRequest6 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray arrmain = response.getJSONArray("cast");
                    for (int i=0;i<arrmain.length();i++){
                        JSONObject jsobj = arrmain.getJSONObject(i);
                        String name = jsobj.getString("name");
//                        Toast.makeText(Movies_Description_Activity.this, name, Toast.LENGTH_SHORT).show();
                        String rolename = jsobj.getString("character");
                        String dept = jsobj.getString("known_for_department");
                        String imglink = jsobj.getString("profile_path");
                        userListSC.add(new StarCast_ModelClass(name, rolename , dept, "https://image.tmdb.org/t/p/w500" + imglink));

                    }
                    adapterSC.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(mainContext, "Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mainContext, "Something Wrong", Toast.LENGTH_SHORT).show();
//                Log.d("Shubham", "Something went wrong");


            }
        });

        requestQueue.add(jsonObjectRequest6);
    }

    private void getRecomDetailsApi(String url){
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(mainContext);
        JsonObjectRequest jsonObjectRequest7 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray arrmain = response.getJSONArray("results");
                    for (int i=0;i<arrmain.length();i++){
                        JSONObject jsobj = arrmain.getJSONObject(i);
                        String name = jsobj.getString("name");
                        String RecoSerID = jsobj.getString("id");
                        String imglink = jsobj.getString("poster_path");
                        userListReco.add(new Series_ModelClass(name, "https://image.tmdb.org/t/p/w500" + imglink , RecoSerID ,mainContext ));

                    }
                    adapterReco.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(mainContext, "Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mainContext, "Something Wrong", Toast.LENGTH_SHORT).show();
//                Log.d("Shubham", "Something went wrong");


            }
        });

        requestQueue.add(jsonObjectRequest7);
    }

    public String decriptFromApi(String Link) {
        String sentence = Link;
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }
}