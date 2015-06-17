package com.portlux.portluxpocket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Andreas Pegelow on 2015-06-17.
 */
public class SearchModel {
    Activity view;

    ArrayList users;
    ArrayList berths;

    public SearchModel(Activity view) {
        this.view = view;
        retrieveData();

    }

    public void search(String querry) {


    }

    private void retrieveData() {
        retrieveUsers();
        retrieveBerths();
    }

    private void retrieveBerths() {
        //TODO: Make the URl be a parameter in the method
        String URL = "http://test.palholmen.se/api/user/list/xml";
        //String URL = "http://www.google.com";


//        //Encode the URL
//        try {
//            URL = URLEncoder.encode(URL, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//           // e.printStackTrace();
//            Log.d("Portlux", "Faild to encode the URL: for berth");
//        }
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(view);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Portlux:", response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Portlux:","Error: " + error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void retrieveUsers() {
        //TODO: Make the URl be a parameter in the method
        String URL = "http://test.palholmen.se/api/user/list/xml";

        //Encode the URL
        try {
            URL = URLEncoder.encode(URL, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("Portlux", "Faild to encode the URL: for users");
        }
    }
}
