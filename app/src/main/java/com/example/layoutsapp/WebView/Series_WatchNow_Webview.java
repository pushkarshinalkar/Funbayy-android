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
import com.example.layoutsapp.Needed_Classes.SharedPref;
import com.example.layoutsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class Series_WatchNow_Webview extends AppCompatActivity {

    String EpisodeNo;
    String SeasonNo;
    String TMDBid;
    WebView webview;
    ProgressBar progressBarSerWatch;
    final Context mainContext = Series_WatchNow_Webview.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_watch_now_series_webview);

        webview = findViewById(R.id.seriesWebviewID);
        webview.setBackgroundColor(Color.parseColor("#101417"));
        EpisodeNo = getIntent().getStringExtra("EpisodeNo");
        SeasonNo = getIntent().getStringExtra("SeasonNo");
        TMDBid = getIntent().getStringExtra("tmdbID");
        progressBarSerWatch = findViewById(R.id.progressBarSerWebview);
        SharedPref sharedPref = new SharedPref(mainContext);

        if (sharedPref.getWatchnowSeriesLink().equals("")){
            String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(mainContext);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String SeriesUrl = response.getString("Watchnow_Series_embed")+TMDBid+"&s="+SeasonNo+"&e="+EpisodeNo;
                        sharedPref.setWatchnowSeriesLink(response.getString("Watchnow_Series_embed"));
                        loadwebview(decriptFromApi(SeriesUrl));

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
            loadwebview(sharedPref.getWatchnowSeriesLink()+TMDBid+"&s="+SeasonNo+"&e="+EpisodeNo);
//            Toast.makeText(mainContext, sharedPref.getWatchnowSeriesLink(), Toast.LENGTH_SHORT).show();
        }







    }
    private void loadwebview(String url){
        webview.loadUrl(url);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBarSerWatch.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Toasty.warning(Series_WatchNow_Webview.this,"Make sure you have Adblocker in your browser",1).show();
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url))
                );
                return true;
            }
        });
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