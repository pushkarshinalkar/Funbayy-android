package com.example.layoutsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.layoutsapp.Adapters.Movies_List_Adapter;
import com.example.layoutsapp.Adapters.Series_List_Adapter;
import com.example.layoutsapp.ModelClasses.Movies_ModelClass;
import com.example.layoutsapp.ModelClasses.Series_ModelClass;
import com.example.layoutsapp.Needed_Classes.SharedPref;
import com.example.layoutsapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SearchResult_Activity extends AppCompatActivity {

    TextView queryDisplayText;
    RecyclerView SearchRecyclerVIew;

    String movSearchUrl;
    String serSearchUrl;
    ProgressBar progressbarsearchResult;
    List<Movies_ModelClass> userList;
    Movies_List_Adapter adapter;
    List<Series_ModelClass> userListSer;
    Series_List_Adapter adapterSer;
    GridLayoutManager layoutManager;
    Context mainContext = SearchResult_Activity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_query_display);
        String isMovorSer = getIntent().getStringExtra("movORser");
        String searchedtext;
        progressbarsearchResult = findViewById(R.id.progressBarSearchResult);
        SharedPref sharedPref = new SharedPref(mainContext);
        userList = new ArrayList<>();
        userListSer = new ArrayList<>();

        if (isMovorSer.equals("Movies")) {
           searchedtext = ("Searched Movie is " + getIntent().getStringExtra("queryKey"));
            queryDisplayText = findViewById(R.id.SearchQueryTextID);
            queryDisplayText.setText(searchedtext);
        }else if (isMovorSer.equals("Series")){
           searchedtext = ("Searched Tv Show is " + getIntent().getStringExtra("queryKey"));
            queryDisplayText = findViewById(R.id.SearchQueryTextID);
            queryDisplayText.setText(searchedtext);
        }

        if (sharedPref.getMovSearchLink().equals("")){
            String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(mainContext);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        movSearchUrl = decriptFromApi(response.getString("Movie_Search_details")) + getIntent().getStringExtra("queryKey") + "&page=1&include_adult=false";
                        serSearchUrl = decriptFromApi(response.getString("Series_Search_details")) + getIntent().getStringExtra("queryKey") + "&page=1&include_adult=false";
                        sharedPref.setMovSearchLink(response.getString("Movie_Search_details"));
                        sharedPref.setSerSearchLink(response.getString("Series_Search_details"));

                        if (isMovorSer.equals("Movies")) {
                            RequestQueue requestQueue;
                            requestQueue = Volley.newRequestQueue(SearchResult_Activity.this);

                            JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET, movSearchUrl, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
//
//                                        String page = response.getString("page");
                                        JSONArray jsArr = response.getJSONArray("results");

                                        for (int i = 0; i < jsArr.length(); i++) {
                                            JSONObject jsObj = jsArr.getJSONObject(i);
                                            String idT = jsObj.getString("id");
                                            String name = jsObj.getString("title");
//                        Toast.makeText(Search_Query_Display.this, name, Toast.LENGTH_SHORT).show();
                                            String imgLink = jsObj.getString("poster_path");
                                            userList.add(new Movies_ModelClass(name, "https://image.tmdb.org/t/p/w500" + imgLink, idT, SearchResult_Activity.this));


                                        }
//                    SearchRecyclerVIew = view.findViewById(R.id.RecyclerViewMovie);
                                        progressbarsearchResult.setVisibility(View.GONE);
                                        SearchRecyclerVIew = findViewById(R.id.SearchRecyclerViewID);
                                        layoutManager = new GridLayoutManager(SearchResult_Activity.this, 3);
                                        SearchRecyclerVIew.setLayoutManager(layoutManager);
                                        adapter = new Movies_List_Adapter(userList);
                                        SearchRecyclerVIew.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
//                        shimmerFrameLayoutmovie.stopShimmer();
//                        constraintLayouttohideShimmer.removeView(shimmerFrameLayoutmovie);

//                                containerShimmer.stopShimmer();
//                                containerShimmer.setVisibility(View.GONE);
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

                            requestQueue.add(jsonObjectRequest3);

                        }
                        else if (isMovorSer.equals("Series")){
                            RequestQueue requestQueue;
                            requestQueue = Volley.newRequestQueue(SearchResult_Activity.this);

                            JsonObjectRequest jsonObjectRequest5 = new JsonObjectRequest(Request.Method.GET, serSearchUrl, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {

                                        String page = response.getString("page");
                                        JSONArray jsArr = response.getJSONArray("results");

                                        for (int i = 0; i < jsArr.length(); i++) {
                                            JSONObject jsObj = jsArr.getJSONObject(i);
                                            String idT = jsObj.getString("id");
                                            String name = jsObj.getString("name");
                                            String imgLink = jsObj.getString("poster_path");
                                            userListSer.add(new Series_ModelClass(name, "https://image.tmdb.org/t/p/w500" + imgLink, idT, SearchResult_Activity.this));

                                        }
                                        progressbarsearchResult.setVisibility(View.GONE);
                                            SearchRecyclerVIew = findViewById(R.id.SearchRecyclerViewID);
                                            layoutManager = new GridLayoutManager(SearchResult_Activity.this,2);
                                            SearchRecyclerVIew.setLayoutManager(layoutManager);
                                            adapterSer = new Series_List_Adapter(userListSer);
                                            SearchRecyclerVIew.setAdapter(adapterSer);
                                            adapterSer.notifyDataSetChanged();

//                                containerShimmer.stopShimmer();
//                                containerShimmer.setVisibility(View.GONE);
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

                    } catch (JSONException e) {
                        Toasty.warning(mainContext, "Something went wrong").show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toasty.warning(mainContext, "No Internet Connection").show();

                }
            });

            requestQueue.add(jsonObjectRequest);
        }else {
            movSearchUrl = sharedPref.getMovSearchLink() + getIntent().getStringExtra("queryKey") + "&page=1&include_adult=false";
            serSearchUrl = sharedPref.getSerSearchLink() + getIntent().getStringExtra("queryKey") + "&page=1&include_adult=false";

            if (isMovorSer.equals("Movies")) {
                RequestQueue requestQueue;
                requestQueue = Volley.newRequestQueue(SearchResult_Activity.this);

                JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET, movSearchUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            String page = response.getString("page");
                            JSONArray jsArr = response.getJSONArray("results");

                            for (int i = 0; i < jsArr.length(); i++) {
                                JSONObject jsObj = jsArr.getJSONObject(i);
                                String idT = jsObj.getString("id");
                                String name = jsObj.getString("title");
//                        Toast.makeText(Search_Query_Display.this, name, Toast.LENGTH_SHORT).show();
                                String imgLink = jsObj.getString("poster_path");
                                userList.add(new Movies_ModelClass(name, "https://image.tmdb.org/t/p/w500" + imgLink, idT, SearchResult_Activity.this));


                            }
//                    SearchRecyclerVIew = view.findViewById(R.id.RecyclerViewMovie);
                            progressbarsearchResult.setVisibility(View.GONE);
                            SearchRecyclerVIew = findViewById(R.id.SearchRecyclerViewID);
                            layoutManager = new GridLayoutManager(SearchResult_Activity.this, 3);
                            SearchRecyclerVIew.setLayoutManager(layoutManager);
                            adapter = new Movies_List_Adapter(userList);
                            SearchRecyclerVIew.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
//                        shimmerFrameLayoutmovie.stopShimmer();
//                        constraintLayouttohideShimmer.removeView(shimmerFrameLayoutmovie);

//                                containerShimmer.stopShimmer();
//                                containerShimmer.setVisibility(View.GONE);
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

                requestQueue.add(jsonObjectRequest3);

            }
            else if (isMovorSer.equals("Series")){
                RequestQueue requestQueue;
                requestQueue = Volley.newRequestQueue(SearchResult_Activity.this);

                JsonObjectRequest jsonObjectRequest5 = new JsonObjectRequest(Request.Method.GET, serSearchUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            String page = response.getString("page");
                            JSONArray jsArr = response.getJSONArray("results");

                            for (int i = 0; i < jsArr.length(); i++) {
                                JSONObject jsObj = jsArr.getJSONObject(i);
                                String idT = jsObj.getString("id");
                                String name = jsObj.getString("name");
                                String imgLink = jsObj.getString("poster_path");
                                userListSer.add(new Series_ModelClass(name,"https://image.tmdb.org/t/p/w500"+imgLink,idT, SearchResult_Activity.this));


                                SearchRecyclerVIew = findViewById(R.id.SearchRecyclerViewID);
                                layoutManager = new GridLayoutManager(SearchResult_Activity.this,2);
                                SearchRecyclerVIew.setLayoutManager(layoutManager);
                                adapterSer = new Series_List_Adapter(userListSer);
                                SearchRecyclerVIew.setAdapter(adapterSer);
                                adapterSer.notifyDataSetChanged();
                            }
                            progressbarsearchResult.setVisibility(View.GONE);

//                                containerShimmer.stopShimmer();
//                                containerShimmer.setVisibility(View.GONE);
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
        }





    }

    public String decriptFromApi(String Link) {
        String sentence = Link;
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }
}