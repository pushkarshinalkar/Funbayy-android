package com.example.layoutsapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.layoutsapp.Adapters.RecomMovies_List_Adapter;
import com.example.layoutsapp.Adapters.StarCast_List_Adapter;
import com.example.layoutsapp.Database.AppDatabase;
import com.example.layoutsapp.Database.User;
import com.example.layoutsapp.ModelClasses.Favourites_ModelClass;
import com.example.layoutsapp.ModelClasses.Movies_ModelClass;
import com.example.layoutsapp.ModelClasses.StarCast_ModelClass;
import com.example.layoutsapp.Needed_Classes.BottomSheetProviderDialog;
import com.example.layoutsapp.R;
import com.example.layoutsapp.Needed_Classes.SharedPref;
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

public class Movies_Description_Activity extends AppCompatActivity {


    ConstraintLayout descConst;

    List<Favourites_ModelClass> FavList;
    ToggleButton buttonFavorite1;
    ToggleButton downloadbtnToggle;

    TextView movienamee;

    Context mainContext = this;

    public String whichtoggle;
    String movNo;

    Button tralorbutt;
    Button watchnowButt;

    String smallimgLink;
    ImageView movBigimg;

    String releseYear;

    TextView movType;
    TextView movStars;
    TextView movRating;
    TextView movWatchTime;
    TextView movLanguage;
    TextView movDecrip;


    String MovieNo;
    String movName;
    String movNameWithoutPlus;


    ChipGroup chipGroupQuality;


    List<StarCast_ModelClass> userListSC;
    StarCast_List_Adapter adapterSC;
    RecyclerView recyclerViewStarCast;

    List<Movies_ModelClass> userListReco;
    RecomMovies_List_Adapter adapterReco;
    RecyclerView recyclerViewReco;
    String defUrl;
    String defApiKey;
    private static final int STORAGE_PERMISSION_CODE = 100;
//    LottieAnimationView lottieAnimationViewBigimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_movie_activity);
        MovieNo = getIntent().getStringExtra("MovieID");

        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

//        User user = new User();


//        Toast.makeText(mainContext, "is liked "+isLiked, Toast.LENGTH_SHORT).show();

        SharedPref shared1 = new SharedPref(Movies_Description_Activity.this);
        if (shared1.loadNightModeState() == null) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if (shared1.loadNightModeState()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        descConst = findViewById(R.id.descripConstraint);
        buttonFavorite1 = findViewById(R.id.button_favorite1);
        downloadbtnToggle = findViewById(R.id.downloadBTNtoggle);
        tralorbutt = findViewById(R.id.trailorButt);
        watchnowButt = findViewById(R.id.watchnowButt);
        movienamee = findViewById(R.id.nameofmovie);
        movBigimg = findViewById(R.id.MovieBigDescImg);
        movType = findViewById(R.id.MovieTagline);
        movDecrip = findViewById(R.id.movieDecriptionText);
        movLanguage = findViewById(R.id.MovieLanguageText);
        movWatchTime = findViewById(R.id.MovieWatchtimeText);
        movRating = findViewById(R.id.MovieRatingText);
        movStars = findViewById(R.id.MovieStarsText);
//        lottieAnimationViewBigimg = findViewById(R.id.ufo_animationView_Bigimg);

        boolean isLiked = db.userDao().isMovieLiked(MovieNo);
        if (isLiked) {
            buttonFavorite1.setChecked(true);
        }

        if (shared1.getMoviesDescLink().equals("")) {
            String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

            RequestQueue requestQueue;


            requestQueue = Volley.newRequestQueue(mainContext);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        defUrl = response.getString("Movie_Desc_url");
                        defApiKey = response.getString("Api_key");
                        shared1.setMoviesDescLink(defUrl);
                        shared1.setApiKey(defApiKey);

                        String url = decriptFromApi(defUrl) + MovieNo + "?api_key=" + decriptFromApi(defApiKey);
                        String urlCast = decriptFromApi(defUrl) + MovieNo + "/credits?api_key=" + decriptFromApi(defApiKey);
                        String urlReco = decriptFromApi(defUrl) + MovieNo + "/recommendations?api_key=" + decriptFromApi(defApiKey) + "&language=en-US&page=1";


                        getDetalilsBasicApi(url);
                        getDetailsCastApi(urlCast);
                        getDetailsRecomApi(urlReco);


                    } catch (JSONException e) {
                        Toasty.warning(Movies_Description_Activity.this, "Something went wrong").show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toasty.warning(Movies_Description_Activity.this, "No Internet Connection1").show();

                }
            });

            requestQueue.add(jsonObjectRequest);
        } else {


            defUrl = shared1.getMoviesDescLink();
            defApiKey = shared1.getApiKey();

            String url = defUrl + MovieNo + "?api_key=" + defApiKey;
            String urlCast = defUrl + MovieNo + "/credits?api_key=" + defApiKey;
            String urlReco = defUrl + MovieNo + "/recommendations?api_key=" + defApiKey + "&language=en-US&page=1";


            getDetalilsBasicApi(url);
            getDetailsCastApi(urlCast);
            getDetailsRecomApi(urlReco);
        }


        userListSC = new ArrayList<>();
        recyclerViewStarCast = findViewById(R.id.starcastRecyclerViewID);
        recyclerViewStarCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterSC = new StarCast_List_Adapter(userListSC);
        recyclerViewStarCast.setAdapter(adapterSC);

        userListReco = new ArrayList<>();
        recyclerViewReco = findViewById(R.id.recomendationsRecyclerViewID);
        recyclerViewReco.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterReco = new RecomMovies_List_Adapter(userListReco);
        recyclerViewReco.setAdapter(adapterReco);


        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        buttonFavorite1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                compoundButton.startAnimation(scaleAnimation);
                if (b) {

                    if (movNameWithoutPlus != null && releseYear != null && MovieNo != null && smallimgLink != null) {
                        saveNewUser(movNameWithoutPlus, releseYear, smallimgLink, MovieNo);
                    } else {
                        Toasty.error(mainContext, "Movie not loaded yet").show();
                        buttonFavorite1.setChecked(false);
                    }

                } else {
//                    Toast.makeText(mainContext, "UnAdded to fav", Toast.LENGTH_SHORT).show();
                    deleteNewUser(movNameWithoutPlus);
                }
            }
        });

        downloadbtnToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

                if (ContextCompat.checkSelfPermission(Movies_Description_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    if (b) {
                        Bundle bundle = new Bundle();
                        bundle.putString("movID", MovieNo);
                        bundle.putString("movNameID", movName);
                        bundle.putString("movYearID", releseYear);
                        bundle.putBoolean("isDownloadable", true);
                        shared1.setDownloadingMovieName(movNameWithoutPlus);
                        BottomSheetProviderDialog bottomSheet = new BottomSheetProviderDialog();
                        bottomSheet.setArguments(bundle);
                        bottomSheet.show(getSupportFragmentManager(),
                                "ModalBottomSheet");
                    }
                } else {
                    downloadbtnToggle.setChecked(false);
                }
                compoundButton.startAnimation(scaleAnimation);

            }
        });

        watchnowButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("movID", MovieNo);
                bundle.putString("movNameID", movName);
                bundle.putString("movYearID", releseYear);
                bundle.putBoolean("isDownloadable", false);
                BottomSheetProviderDialog bottomSheet = new BottomSheetProviderDialog();
                bottomSheet.setArguments(bundle);
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");


            }
        });

        tralorbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Movies_Description_Activity.this, Movies_Trailer_Webview.class);
                intent.putExtra("movId", MovieNo);
                intent.putExtra("MovOrSer", "Mov");
                startActivity(intent);
            }
        });

    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(Movies_Description_Activity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(Movies_Description_Activity.this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toasty.success(Movies_Description_Activity.this, "Permission Granted").show();
            } else {
                Toasty.error(Movies_Description_Activity.this,"Storage Permission Denied").show();
            }
        }
    }

//    @Override
//    protected void onDestroy() {
//        deleteCache(this);
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

    private void saveNewUser(String firstName, String lastName, String imglink, String movid) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        User user = new User();
        user.movieName = firstName;
        user.releaseYear = lastName;
        user.imageLinkMovieSmall = imglink;
        user.movieID = movid;
        db.userDao().insertUser(user);

//        finish();

    }

    private void deleteNewUser(String firstName) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

//        User user = new User();
        db.userDao().deleteByUserId(firstName);

//        finish();

    }

    private void getDetalilsBasicApi(String url) {
        RequestQueue requestQueue;


        requestQueue = Volley.newRequestQueue(mainContext);
        JsonObjectRequest jsonObjectRequest5 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsCoName = response.getJSONArray("production_countries");
                    JSONObject jsCoNameObj = jsCoName.getJSONObject(0);
                    String OriginCo = jsCoNameObj.getString("iso_3166_1");
                    movRating.setText(OriginCo);
//                    String smallimgMov = response.getString("poster_path");
                    String bigimgMov = response.getString("backdrop_path");
                    smallimgLink = response.getString("poster_path");

                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + bigimgMov).into(movBigimg);
//                    Glide.with(Movies_Description_Activity.this).load("https://image.tmdb.org/t/p/w500" + bigimgMov).transition(DrawableTransitionOptions.withCrossFade()).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).into(movBigimg);

                    String releseDate = response.getString("release_date");
                    String lang = response.getString("original_language");
                    movLanguage.setText(lang.toUpperCase());
                    String watchtime = response.getString("runtime") + " min";
                    movWatchTime.setText(watchtime);
                    String rattingCount = response.getString("vote_average") + "/10";
                    movStars.setText(rattingCount);
                    movType.setText(response.getString("tagline"));
                    releseYear = releseDate.substring(0, 4);
//                    Toast.makeText(Movies_Description_Activity.this, releseYear, Toast.LENGTH_SHORT).show();
                    movName = response.getString("title");
                    movNameWithoutPlus = movName;
                    movienamee.setText(movName);
                    movName = movName.replaceAll(" ", "+");
//                    Toast.makeText(Movies_Description_Activity.this, movName, Toast.LENGTH_SHORT).show();
                    movDecrip.setText(response.getString("overview"));
//                    Picasso.get().load("https://image.tmdb.org/t/p/w500"+smallimgMov).into(movSmallimg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                lottieAnimationViewBigimg.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("Shubham", "Something went wrong");


            }
        });

        requestQueue.add(jsonObjectRequest5);
    }

    private void getDetailsCastApi(String url) {
        RequestQueue requestQueue;


        requestQueue = Volley.newRequestQueue(mainContext);
        JsonObjectRequest jsonObjectRequest6 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray arrmain = response.getJSONArray("cast");
                    for (int i = 0; i < arrmain.length(); i++) {
                        JSONObject jsobj = arrmain.getJSONObject(i);
                        String name = jsobj.getString("name");
//                        Toast.makeText(Movies_Description_Activity.this, name, Toast.LENGTH_SHORT).show();
                        String rolename = jsobj.getString("character");
                        String dept = jsobj.getString("known_for_department");
                        String imglink = jsobj.getString("profile_path");
                        userListSC.add(new StarCast_ModelClass(name, rolename, dept, "https://image.tmdb.org/t/p/w500" + imglink));

                    }
                    adapterSC.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(Movies_Description_Activity.this, "Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Movies_Description_Activity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
//                Log.d("Shubham", "Something went wrong");


            }
        });

        requestQueue.add(jsonObjectRequest6);
    }

    private void getDetailsRecomApi(String url) {
        RequestQueue requestQueue;


        requestQueue = Volley.newRequestQueue(mainContext);
        JsonObjectRequest jsonObjectRequest7 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray arrmain = response.getJSONArray("results");
                    for (int i = 0; i < arrmain.length(); i++) {
                        JSONObject jsobj = arrmain.getJSONObject(i);
                        String name = jsobj.getString("title");
                        String RecoMovID = jsobj.getString("id");
                        String imglink = jsobj.getString("poster_path");
                        userListReco.add(new Movies_ModelClass(name, "https://image.tmdb.org/t/p/w500" + imglink, RecoMovID, mainContext));

                    }
                    adapterReco.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(Movies_Description_Activity.this, "Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Movies_Description_Activity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
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