package com.example.layoutsapp.WebView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.layoutsapp.Activities.Movies_Description_Activity;
import com.example.layoutsapp.Needed_Classes.IsInternetConnected;
import com.example.layoutsapp.Needed_Classes.SharedPref;
import com.example.layoutsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class Movies_Trailer_Webview extends AppCompatActivity {

    WebView trailerMovie;
    String movId;
    String serId;
    String responseUrl;
    Context mainContext = Movies_Trailer_Webview.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.trailer_movie_webview);
        SharedPref sharedPref = new SharedPref(mainContext);
        trailerMovie = findViewById(R.id.movieTrailerId);
        trailerMovie.setBackgroundColor(Color.parseColor("#101417"));
        trailerMovie.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = trailerMovie.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        if (getIntent().getStringExtra("MovOrSer").equals("Mov")){
            movId = getIntent().getStringExtra("movId");
            responseUrl = sharedPref.getMoviesDescLink()+movId+"/videos?api_key="+sharedPref.getApiKey();
        }else {
            serId = getIntent().getStringExtra("SerId");
            responseUrl = sharedPref.getSeriesDescLink()+serId+"/videos?api_key="+sharedPref.getApiKey();
        }


        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(mainContext);

        if (getIntent().getStringExtra("MovOrSer").equals("Mov")){

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, responseUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsArr = response.getJSONArray("results");
                        String key=null ;
                        for (int i = 0;i<jsArr.length();i++){
                            JSONObject jobj = jsArr.getJSONObject(i);
                            key = jobj.getString("key");
                            if (jobj.getString("name").equals("Official Trailer")){
                                break;
                            }
                        }
                        trailerMovie.loadUrl("https://www.youtube.com/embed/"+key);
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
        }else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, responseUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsArr = response.getJSONArray("results");
                        String key=null ;
                        for (int i = 0;i<jsArr.length();i++){
                            JSONObject jobj = jsArr.getJSONObject(i);
                            key = jobj.getString("key");
                            if (jobj.getString("name").equals("Trailer")){
                                break;
                            }
                        }
                        trailerMovie.loadUrl("https://www.youtube.com/embed/"+key);
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
        }




    }
}