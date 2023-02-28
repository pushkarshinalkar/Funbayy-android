package com.example.layoutsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.layoutsapp.Adapters.Episodes_List_Adapter;
import com.example.layoutsapp.ModelClasses.Episodes_ModelClass;
import com.example.layoutsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Series_SeasonsEpisodesList_Activity extends AppCompatActivity {

    RecyclerView mrecyclerView;
    LinearLayoutManager layoutManager;
    List<Episodes_ModelClass> userList;
    Episodes_List_Adapter adapter;
    String seasonID;
    String tmdbID;
    String SeasonsTotalID;
    String SeasonNO;
    String url1 = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/SeriesDetailsFile.json?alt=media";
    String positionval;
    String urlnoofseasons;
    ImageView seriesWatchNowBigImage;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_now_series);
        seriesWatchNowBigImage = findViewById(R.id.seriesImageID);
        String whichSeriesCard = getIntent().getStringExtra("seriesnokey");
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/MovieSmallimg1.jpg?alt=media").into(seriesWatchNowBigImage);

        autoCompleteTextView = findViewById(R.id.autoCompletespinnerText);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Series_SeasonsEpisodesList_Activity.this);


        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET,
                url1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    switch (whichSeriesCard) {
                        case "1": {
                            SeasonsTotalID = response.getString("Series1SeasonsListID");
                            if (!Series_SeasonsEpisodesList_Activity.this.isDestroyed()) {  // check if activity is destroyed as it is a background task and will run even if activity is destroyed
                                Glide.with(Series_SeasonsEpisodesList_Activity.this).load("https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/SeriesBigimg1.jpg?alt=media").into(seriesWatchNowBigImage);
                            }
//                            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/SeriesBigimg1.jpg?alt=media").into(seriesWatchNowBigImage);
                            Toast.makeText(Series_SeasonsEpisodesList_Activity.this, SeasonsTotalID, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "2": {
                            SeasonsTotalID = response.getString("Series2SeasonsListID");
//                            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/SeriesBigimg2.jpg?alt=media").into(seriesWatchNowBigImage);
                            if (!Series_SeasonsEpisodesList_Activity.this.isDestroyed()) {
                                Glide.with(Series_SeasonsEpisodesList_Activity.this).load("https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/SeriesBigimg2.jpg?alt=media").into(seriesWatchNowBigImage);
                            }
                            Toast.makeText(Series_SeasonsEpisodesList_Activity.this, SeasonsTotalID, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "3": {
                            SeasonsTotalID = response.getString("Series3SeasonsListID");
//                            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/SeriesBigimg3.jpg?alt=media").into(seriesWatchNowBigImage);
                            if (!Series_SeasonsEpisodesList_Activity.this.isDestroyed()) {
                                Glide.with(Series_SeasonsEpisodesList_Activity.this).load("https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/SeriesBigimg3.jpg?alt=media").into(seriesWatchNowBigImage);
                            }
                            Toast.makeText(Series_SeasonsEpisodesList_Activity.this, SeasonsTotalID, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "4": {
                            SeasonsTotalID = response.getString("Series4SeasonsListID");
//                            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/SeriesBigimg4.jpg?alt=media").into(seriesWatchNowBigImage);
                            if (!Series_SeasonsEpisodesList_Activity.this.isDestroyed()) {
                                Glide.with(Series_SeasonsEpisodesList_Activity.this).load("https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/SeriesBigimg4.jpg?alt=media").into(seriesWatchNowBigImage);
                            }
                            Toast.makeText(Series_SeasonsEpisodesList_Activity.this, SeasonsTotalID, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "5": {
                            SeasonsTotalID = response.getString("Series5SeasonsListID");
//                            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/SeriesBigimg5.jpg?alt=media").into(seriesWatchNowBigImage);
                            if (!Series_SeasonsEpisodesList_Activity.this.isDestroyed()) {
                                Glide.with(Series_SeasonsEpisodesList_Activity.this).load("https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/SeriesBigimg5.jpg?alt=media").into(seriesWatchNowBigImage);
                            }
                            Toast.makeText(Series_SeasonsEpisodesList_Activity.this, SeasonsTotalID, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }


                    urlnoofseasons = "https://api.tvmaze.com/shows/" + SeasonsTotalID + "/seasons";

//        spinner2 = findViewById(R.id.spinner2);


                    ArrayList<String> arrayList = new ArrayList<>();
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Series_SeasonsEpisodesList_Activity.this, R.layout.list_item_spinner, arrayList);

                    autoCompleteTextView.setAdapter(arrayAdapter);


                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                            urlnoofseasons, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            try {
                                JSONObject obj = response.getJSONObject(2);
//                    Log.d("Shubham", "onResponse: Title is: " +
//                            obj.getString("url"));
//                    Toast.makeText(WatchNow_Series.this, obj.getString("id"), Toast.LENGTH_SHORT).show();
//                    String str = String.valueOf(response.length());
                                for (int i = 1; i <= response.length(); i++) {
                                    arrayList.add("Season " + i);
                                }

                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                autoCompleteTextView.setAdapter(arrayAdapter);
                                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        positionval = String.valueOf(position + 1);
                                        SeasonNO = positionval;

                                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                                                url1, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
//                                      JSONObject objid = response.getJSONObject("Series1Season1ID");
                                                    seasonID = response.getString("Series" + whichSeriesCard + "Season" + SeasonNO + "ID");
                                                    tmdbID = response.getString("Series" + whichSeriesCard + "tmdbID");
                                                    Toast.makeText(Series_SeasonsEpisodesList_Activity.this, "id with positionval is" + seasonID, Toast.LENGTH_SHORT).show();

                                                    String url = "https://api.tvmaze.com/seasons/" + seasonID + "/episodes";
//                                        Toast.makeText(WatchNow_Series.this, url, Toast.LENGTH_SHORT).show();
                                                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                                                            url, null, new Response.Listener<JSONArray>() {
                                                        @Override
                                                        public void onResponse(JSONArray response) {
//                                                Toast.makeText(WatchNow_Series.this, "Array request of ep detais success", Toast.LENGTH_SHORT).show();

                                                            try {
                                                                userList = new ArrayList<>();
                                                                for (int i = 0; i < response.length(); i++) {
                                                                    JSONObject obj = response.getJSONObject(i);

                                                                    String Namefromepisode = obj.getString("name");

                                                                    String Datefromepisode = obj.getString("airdate");

                                                                    String Descfromepisode = obj.getString("summary");

                                                                    String ImageLinkfromepisode = obj.getJSONObject("image").getString("original");


//                                                        Toast.makeText(WatchNow_Series.this, tmdbID, Toast.LENGTH_SHORT).show();
//                                                        Toast.makeText(WatchNow_Series.this, "no of episodes are "+response.length(), Toast.LENGTH_SHORT).show();
                                                                    Context context = Series_SeasonsEpisodesList_Activity.this;
                                                                    String EpisodeNo = String.valueOf(i + 1);
                                                                    userList.add(new Episodes_ModelClass(Namefromepisode, Datefromepisode, Descfromepisode, ImageLinkfromepisode, EpisodeNo, SeasonNO, tmdbID, context));

                                                                }

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

                                                    requestQueue.add(jsonArrayRequest);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
//                Log.d("Shubham", "Something went wrong");
//                                    Toast.makeText(WatchNow_Series.this, "Something went wrong!", Toast.LENGTH_SHORT).show();


                                            }
                                        });
                                        requestQueue.add(jsonObjectRequest);
                                        //make position val int and so you will get season no selected
                                        // here only store value of position+1 and when passing intent later also pass this season no
                                    }

                                });


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Shubham", "Something went wrong");
//                Toast.makeText(WatchNow_Series.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                        }
                    });

                    requestQueue.add(jsonArrayRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("Shubham", "Something went wrong");
//                                    Toast.makeText(WatchNow_Series.this, "Something went wrong!", Toast.LENGTH_SHORT).show();


            }
        });
        requestQueue.add(jsonObjectRequest1);

//        Toast.makeText(WatchNow_Series.this, positionval, Toast.LENGTH_SHORT).show();

//        Toast.makeText(WatchNow_Series.this, positionval, Toast.LENGTH_SHORT).show();


    }

    private void initRecyclerView() {
        mrecyclerView = findViewById(R.id.RecyclerViewEpList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mrecyclerView.setLayoutManager(layoutManager);
        adapter = new Episodes_List_Adapter(userList);
        mrecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

}