package com.example.layoutsapp.WebView;

import com.example.layoutsapp.Needed_Classes.SharedPref;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.layoutsapp.Adapters.Movies_List_Adapter;
import com.example.layoutsapp.Adapters.Qualities_List_Adapter;
import com.example.layoutsapp.ModelClasses.Movies_ModelClass;
import com.example.layoutsapp.ModelClasses.Quality_ModelClass;
import com.example.layoutsapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.content.Context.DOWNLOAD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Movies_Download_Webview_Dialog extends BottomSheetDialogFragment {


    WebView downWebview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.webview_dialog_box_download,
                container, false);

        SharedPref sharedPref = new SharedPref(getContext());
        String torrhash = this.getArguments().getString("MagnetIDdownload");

        InputStream is = null;
        try {
            is = getContext().getAssets().open("indexDownload.html");

            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String str = new String(buffer);
//            Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
            String strnew = str.replace("replaceLinkID", torrhash);
            downWebview = v.findViewById(R.id.download_webviewID);
            downWebview.setBackgroundColor(Color.parseColor("#101417"));
            downWebview.getSettings().setJavaScriptEnabled(true);
            downWebview.setWebChromeClient(new MyChrome());


            downWebview.getSettings().setDomStorageEnabled(true);
            downWebview.getSettings().setBlockNetworkImage(false);

            downWebview.loadData(strnew, "text/html; charset=utf-8", "UTF-8");
            downWebview.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    view.loadUrl(url);
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
//                    downWebview.loadUrl("javascript:(function setmag(link){\n" +
//                            "            magnet = link;\n" +
//                            "            return magnet;\n" +
//                            "        })()" );
//                    Toast.makeText(getContext(), "Loaded", Toast.LENGTH_SHORT).show();
                }
            });

            downWebview.setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {
                    DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(url));
                    request.allowScanningByMediaScanner();
                    request.setTitle(sharedPref.getDownloadingMovieName());
                    request.setDescription("Zip");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download");
                    DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
//                Toast.makeText(MainActivity.this, "Download started - see Notification", Toast.LENGTH_SHORT).show();
                    Toasty.success(getContext(), "Download Started Successfully!", Toast.LENGTH_SHORT, true).show();
                    Toasty.info(getContext(), "Check Notification for details", Toast.LENGTH_SHORT, true).show();
                    dm.enqueue(request);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }




        return v;
    }

    private class MyChrome extends WebChromeClient {


        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {
        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getActivity().getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getActivity().getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            getActivity().setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getActivity().getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getActivity().getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getActivity().getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getActivity().getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}


