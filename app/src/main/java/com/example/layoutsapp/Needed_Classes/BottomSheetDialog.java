package com.example.layoutsapp.Needed_Classes;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.layoutsapp.Activities.Movies_Description_Activity;
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
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class BottomSheetDialog extends BottomSheetDialogFragment {


    List<Quality_ModelClass> userList;
    Qualities_List_Adapter adapter;
    RecyclerView mrecyclerView;
//    Context context = getContext();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_dialog,
                container, false);

        String myValue = this.getArguments().getString("message");
        String Movname = this.getArguments().getString("MovName+");
        String Movyear = this.getArguments().getString("MovYear");
        Boolean isDownload = this.getArguments().getBoolean("isDownloadID");

        String urlMain = "https://firebasestorage.googleapis.com/v0/b/splashscreendemo-33c9c.appspot.com/o/Main_funbayy_file.json?alt=media";

        RequestQueue requestQueue;


        requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMain, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String ytsEncrip =response.getString("Yts_magnet_urls");
                    String _1337xEncrip =response.getString("1337x_magnet_urls");
                    String piratebayEncrip =response.getString("pirateBay_magnet_urls");
                    String yts = decriptFromApi(ytsEncrip);
                    String _1337x = decriptFromApi(_1337xEncrip);
                    String pirateBay = decriptFromApi(piratebayEncrip);

                    String urlYTS = yts + Movname + "+" + Movyear + "&category=movies";
                    String urlPirateBay = pirateBay + Movname + "+" + Movyear + "&category=movies";
                    String url1337x = _1337x + Movname + "+" + Movyear + "&category=movies";
                    switch (myValue) {

                        case "server2": {
//                            Toast.makeText(getContext(), "server2", Toast.LENGTH_SHORT).show();
                            getDataYTS(v,urlYTS,isDownload,getContext());

                            break;
                        }
                        case "server3": {
//                            Toast.makeText(getContext(), "server3", Toast.LENGTH_SHORT).show();
                            getDataPirateBay(v,urlPirateBay,isDownload,getContext());
                            break;
                        }
                        case "server4": {
//                            Toast.makeText(getContext(), "server4", Toast.LENGTH_SHORT).show();
                            getData1337x(v,url1337x,isDownload,getContext());
                            break;
                        }
                    }

                } catch (JSONException e) {
                    Toasty.warning(getContext(), "Something went wrong").show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toasty.warning(getContext(), "No Internet Connection1").show();

            }
        });

        requestQueue.add(jsonObjectRequest);







//


        return v;
    }

    public void getDataYTS(View v,String urlYTS,Boolean isDownloadble,Context context) {

        userList = new ArrayList<>();
        mrecyclerView = v.findViewById(R.id.recyclerViewQualitiesID);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new Qualities_List_Adapter(userList);
        mrecyclerView.setAdapter(adapter);
        RequestQueue requestQueue;
        if (getActivity() != null) {


            requestQueue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlYTS, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String page = response.getString("total");
//                        Toast.makeText(getContext(), page, Toast.LENGTH_SHORT).show();
                        JSONArray jsArr = response.getJSONArray("data");
                        JSONObject jsObj = jsArr.getJSONObject(0);
                        JSONArray torrArr = jsObj.getJSONArray("torrents");
                        for (int i = 0; i < torrArr.length(); i++) {
                            JSONObject torLinks = torrArr.getJSONObject(i);
                            String movQualityNo = torLinks.getString("quality");
                            String movTypeName = torLinks.getString("type");
                            String movSizeNo = torLinks.getString("size");
                            String movMagnetLink = torLinks.getString("magnet");
//                                    Toast.makeText(getContext(), movQualityNo, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getContext(), movTypeName, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getContext(), movSizeNo, Toast.LENGTH_SHORT).show();
////                                    String chipName = movQualityNo +" "+ movTypeName;

                            userList.add(new Quality_ModelClass(movTypeName, movQualityNo, movSizeNo, movMagnetLink,isDownloadble,context));
//                                    Chip chip1 = new Chip(v.getContext());
//                                    chip1.setText(chipName);
//                                    chip1.setChipBackgroundColorResource(R.color.bottomNavChipCol);
//                                    chipGroupQuality.addView(chip1);
                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Shubham", "Something went wrong");
                    Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();


                }
            });

            requestQueue.add(jsonObjectRequest);

        }
    }

    public void getDataPirateBay(View v,String urlPirateBay,Boolean isDownloadble,Context context) {

        userList = new ArrayList<>();
        mrecyclerView = v.findViewById(R.id.recyclerViewQualitiesID);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new Qualities_List_Adapter(userList);
        mrecyclerView.setAdapter(adapter);
        RequestQueue requestQueue;
        if (getActivity() != null) {


            requestQueue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlPirateBay, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String page = response.getString("total");
//                        Toast.makeText(getContext(), page, Toast.LENGTH_SHORT).show();
                        JSONArray jsArr = response.getJSONArray("data");
                        for (int i = 0; i < jsArr.length(); i++) {
                            JSONObject torLinks = jsArr.getJSONObject(i);
                            String getqualityANDtype = torLinks.getString("name");
                            String type;
                            String quality;
                            if (getqualityANDtype.toLowerCase().contains("720p")){
                                quality = "720p";
                            }else if (getqualityANDtype.toLowerCase().contains("1080p")){
                                quality = "1080p";
                            }else if (getqualityANDtype.toLowerCase().contains("2160p")){
                                quality = "1080p";
                            }else{
                                quality = "N.D";
                            }

                            if (getqualityANDtype.toLowerCase().contains("brrip")){
                                type = "BrRip";
                            }else if (getqualityANDtype.toLowerCase().contains("bdrip")){
                                type = "BDRip";
                            }else if (getqualityANDtype.toLowerCase().contains("bluray")){
                                type = "BluRay";
                            }else{
                                type = "N.D";
                            }
                            String movSizeNo = torLinks.getString("size");
                            String movMagnetLink = torLinks.getString("magnet");
//                                    Toast.makeText(getContext(), movQualityNo, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getContext(), movTypeName, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getContext(), movSizeNo, Toast.LENGTH_SHORT).show();
////                                    String chipName = movQualityNo +" "+ movTypeName;

                            userList.add(new Quality_ModelClass(type, quality, movSizeNo, movMagnetLink,isDownloadble,context));
//                                    Chip chip1 = new Chip(v.getContext());
//                                    chip1.setText(chipName);
//                                    chip1.setChipBackgroundColorResource(R.color.bottomNavChipCol);
//                                    chipGroupQuality.addView(chip1);
                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Shubham", "Something went wrong");
                    Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();


                }
            });

            requestQueue.add(jsonObjectRequest);

        }
    }

    public void getData1337x(View v,String url_1337x,Boolean isDownloadble,Context context) {
//        Toast.makeText(getContext(), url_1337x, Toast.LENGTH_SHORT).show();
        userList = new ArrayList<>();
        mrecyclerView = v.findViewById(R.id.recyclerViewQualitiesID);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new Qualities_List_Adapter(userList);
        mrecyclerView.setAdapter(adapter);
        RequestQueue requestQueue;
        if (getActivity() != null) {


            requestQueue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_1337x, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String page = response.getString("total");
//                        Toast.makeText(getContext(), page, Toast.LENGTH_SHORT).show();
                        JSONArray jsArr = response.getJSONArray("data");
                        for (int i = 0; i < jsArr.length(); i++) {
                            JSONObject torLinks = jsArr.getJSONObject(i);
                            String getqualityANDtype = torLinks.getString("name");
                            String movSizeNo = torLinks.getString("size");
//                            Toast.makeText(getContext(), movSizeNo, Toast.LENGTH_SHORT).show();
                            String movMagnetLink = torLinks.getString("magnet");
                            String type;
                            String quality;
                            if (getqualityANDtype.toLowerCase().contains("720p")){
                                quality = "720p";
                            }else if (getqualityANDtype.toLowerCase().contains("1080p")){
                                quality = "1080p";
                            }else if (getqualityANDtype.toLowerCase().contains("2160p")){
                                quality = "1080p";
                            }else{
                                quality = "N.D";
                            }

                            if (getqualityANDtype.toLowerCase().contains("brrip")){
                                type = "BrRip";
                            }else if (getqualityANDtype.toLowerCase().contains("bdrip")){
                                type = "BDRip";
                            }else if (getqualityANDtype.toLowerCase().contains("bluray")){
                                type = "BluRay";
                            }else{
                                type = "N.D";
                            }

//                                    Toast.makeText(getContext(), movQualityNo, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getContext(), movTypeName, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getContext(), movSizeNo, Toast.LENGTH_SHORT).show();
////                                    String chipName = movQualityNo +" "+ movTypeName;

                            userList.add(new Quality_ModelClass(type, quality, movSizeNo, movMagnetLink,isDownloadble,context));
//                                    Chip chip1 = new Chip(v.getContext());
//                                    chip1.setText(chipName);
//                                    chip1.setChipBackgroundColorResource(R.color.bottomNavChipCol);
//                                    chipGroupQuality.addView(chip1);
                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Shubham", "Something went wrong");
                    Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();


                }
            });

            requestQueue.add(jsonObjectRequest);

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

