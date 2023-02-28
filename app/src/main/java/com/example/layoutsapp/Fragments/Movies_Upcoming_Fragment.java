package com.example.layoutsapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.tv.TvContentRating;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.layoutsapp.Adapters.Movies_List_Adapter;
import com.example.layoutsapp.ModelClasses.Movies_ModelClass;
import com.example.layoutsapp.Needed_Classes.SharedPref;
import com.example.layoutsapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class Movies_Upcoming_Fragment extends Fragment {

    RecyclerView mrecyclerView;
    GridLayoutManager layoutManager;
    List<Movies_ModelClass> userList;
    Movies_List_Adapter adapter;
    int page = 1, limit = 10;

    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    LottieAnimationView loadingLotteani;
    TextView bottomlottieTV;

    String location;
    String url1;
    SharedPref sharedPref;


    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_upcoming_movies, container, false);


    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = view.getContext();
        sharedPref = new SharedPref(context);
        userList = new ArrayList<>();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshMovUp);
        progressBar = view.findViewById(R.id.progressBarupcomingMovies);
        progressBar.setVisibility(View.GONE);
        loadingLotteani = view.findViewById(R.id.animationViewLoadingMovUp);
        bottomlottieTV = view.findViewById(R.id.bottomlottieTextviewMovUp);
//        shimmerFrameLayoutmovie = view.findViewById(R.id.shimmerframeupcomingMovieID);
//        shimmerFrameLayoutmovie.startShimmer();
        mrecyclerView = view.findViewById(R.id.RecyclerViewupcomingMovies);
        layoutManager = new GridLayoutManager(getContext(), 3);
        mrecyclerView.setLayoutManager(layoutManager);
        adapter = new Movies_List_Adapter(userList);
        mrecyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        SharedPreferences sr = PreferenceManager.getDefaultSharedPreferences(context);
        location = sr.getString("locationKey", "en");


        String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";
        RequestQueue requestQueue;

        if (getActivity() != null && sharedPref.getUpcomingMovLink().equals("")) {
//            Toast.makeText(context, "not null", Toast.LENGTH_SHORT).show();

            requestQueue = Volley.newRequestQueue(getActivity());

            JsonObjectRequest jsonObjectRequest0 = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String link = response.getString("Movies_Upcoming_Link");
                        sharedPref.setUpcomingMovLink(link);
                        url1 = decriptFromApi(link) + location + "&page=1";
//                        Toast.makeText(context, url1, Toast.LENGTH_SHORT).show();

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    String page = response.getString("total_pages");
                                    limit = Integer.parseInt(page);

                                } catch (JSONException e) {
                                    Toasty.warning(context, "Something went wrong").show();
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toasty.warning(context, "No Internet connection").show();

                            }
                        });

                        requestQueue.add(jsonObjectRequest);


                        getDataFromAPI(page, limit);

                    } catch (JSONException e) {
                        Toasty.warning(context, "Something went wrong").show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toasty.warning(context, "No Internet connection").show();

                }
            });

            requestQueue.add(jsonObjectRequest0);
        } else {
//            Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
            requestQueue = Volley.newRequestQueue(getActivity());
            String link = sharedPref.getUpcomingMovLink();
//            Toast.makeText(context, "link from pref is "+link, Toast.LENGTH_SHORT).show();
            url1 = link + location + "&page=1";
//            Toast.makeText(context, url1, Toast.LENGTH_SHORT).show();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String page = response.getString("total_pages");
                        limit = Integer.parseInt(page);

                    } catch (JSONException e) {
                        Toasty.warning(context, "Something went wrong").show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toasty.warning(context, "No Internet connection").show();

                }
            });

            requestQueue.add(jsonObjectRequest);


            getDataFromAPI(page, limit);
        }

        mrecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    //scrolled to BOTTOM
                    progressBar.setVisibility(View.VISIBLE);
                    page++;
                    getDataFromAPI(page, limit);
//                    Toast.makeText(getContext(), "Reached Bottom", Toast.LENGTH_SHORT).show();
                } else if (!recyclerView.canScrollVertically(-1) && dy < 0) {
//                    Toast.makeText(getContext(), "Reached Top", Toast.LENGTH_SHORT).show();
                    //scrolled to TOP
                }

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                userList.clear();
                getDataFromAPI(1, limit);
                adapter.notifyDataSetChanged();
//                    Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
//                    deleteCache(getContext());
            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.BLACK);


    }

    private void getDataFromAPI(int page, int limit) {
        if (page > limit) {
            // checking if the page number is greater than limit.
            // displaying toast message in this case when page>limit.
            Toast.makeText(getContext(), "That's all the data..", Toast.LENGTH_SHORT).show();

            // hiding our progress bar.
//            loadingPB.setVisibility(View.GONE);
        }


        String url1 = sharedPref.getUpcomingMovLink() + location + "&page=" + page;


        RequestQueue requestQueue;
        if (getActivity() != null) {


            requestQueue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsArr = response.getJSONArray("results");
                        for (int i = 0; i < jsArr.length(); i++) {
                            JSONObject jsObj = jsArr.getJSONObject(i);
                            String idT = jsObj.getString("id");
                            String name = jsObj.getString("title");
                            String imgLink = jsObj.getString("poster_path");
                            userList.add(new Movies_ModelClass(name, "https://image.tmdb.org/t/p/w500" + imgLink, idT, context));
//                            containerShimmer.stopShimmer();
                            loadingLotteani.setVisibility(View.GONE);
                            bottomlottieTV.setVisibility(View.GONE);
                            ;
                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        Toasty.warning(context, "Something went wrong").show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Toasty.warning(context,"No Internet connection").show();

                }
            });

            requestQueue.add(jsonObjectRequest);

        }


    }

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
    public String decriptFromApi(String Link) {
        String sentence = Link;
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }
}