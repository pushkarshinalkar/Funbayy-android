package com.example.layoutsapp.WebView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.layoutsapp.Needed_Classes.IsInternetConnected;
import com.example.layoutsapp.Needed_Classes.SharedPref;
import com.example.layoutsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class Movies_WatchNow_Webview extends AppCompatActivity {

    WebView webview;
    String noofMov;
    final Context mainContext = Movies_WatchNow_Webview.this;
    ProgressBar progressBarDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.watchnow_movie_webview);
        webview = findViewById(R.id.videoView);
        webview.setBackgroundColor(Color.parseColor("#101417"));
        noofMov = getIntent().getStringExtra("movieID");
        progressBarDownload = findViewById(R.id.progressBarMovWebview);
        SharedPref sharedPref = new SharedPref(mainContext);

//        IsInternetConnected isInternetConnected = new IsInternetConnected();
//        if (!isInternetConnected.internetIsConnected()) {
//            Toast.makeText(Movies_WatchNow_Webview.this, "Please connect to the Internet", Toast.LENGTH_SHORT).show();
//        }
        if (sharedPref.getWatchnowMovieLink().equals("")) {
            String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(mainContext);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String link = response.getString("Watchnow_Movie_embed");
                        sharedPref.setWatchnowMovieLink(link);
                        loadwebview(decriptFromApi(link));

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
        } else {
            loadwebview(sharedPref.getWatchnowMovieLink());
        }


    }

    private void loadwebview(String url) {
        webview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progressBarDownload.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Toasty.warning(Movies_WatchNow_Webview.this,"Make sure you have Adblocker in your browser",1).show();
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url))
                );
                return true;
            }
        });
        webview.loadUrl(url + noofMov);
        webview.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
    }
    public String decriptFromApi(String Link) {
        String sentence = Link;
        StringBuilder sb1=new StringBuilder(sentence);
        sentence = sb1.reverse().toString();
        sentence = sentence.replace("binod","://");
        return sentence.replace("joemama",".");
    }
}