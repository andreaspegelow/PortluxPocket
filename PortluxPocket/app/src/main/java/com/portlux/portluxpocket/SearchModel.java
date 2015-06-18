package com.portlux.portluxpocket;

import android.app.Activity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Andreas Pegelow on 2015-06-17.
 */
public class SearchModel {
    Activity view;

    ArrayList users;
    ArrayList<String> berths= new ArrayList<String>();


    public SearchModel(Activity view) {
        this.view = view;
        getDataFromDatabase();


    }

    public void search(String querry) {


    }

    private void getDataFromDatabase() {
        getUsers();
        getBerths();
    }

    private void getBerths() {
        //TODO: Make the URl be a parameter in the method
        String URL = "http://test.palholmen.se/api/berth/list/xml";
        String berthResponse;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(view);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            XMLParser(response);
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Portlux:", "Error: " + error.getMessage());
            }
        });
        // Because the file is to big the default timeout time is to small
        //
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.

        queue.add(stringRequest);

    }

    private void getUsers() {
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

    private void XMLParser(String data) throws XmlPullParserException, IOException {

        //Create the parser
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();


        parser.setInput(new StringReader(data));
        int eventType = parser.getEventType();

        //loop through the string and look for tags "berth"
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName().equalsIgnoreCase("berth")) {
                    berths.add(parser.nextText());
                }
            }
            eventType = parser.next();
        }
        for (String berth : berths) {
            Log.d("XMLParses",berth.toString());
        }


    }
}
