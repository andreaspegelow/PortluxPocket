package com.portlux.portluxpocket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Andreas Pegelow on 2015-06-17.
 */
public class SearchModel {
    boolean done = false;
    Activity view;
    ProgressDialog loadingDialog;


    ArrayList<Berth> berths = new ArrayList<Berth>();
    ArrayList<User> users = new ArrayList<User>();


    public SearchModel(Activity view) {
        this.view = view;
        loadingDialog = new ProgressDialog(view);
        loadingDialog.setMessage(Values.LOADINGMESSAGE);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        getDataFromDatabase();


    }

    public ArrayList<User> search(String querry) {
        ArrayList<User> searchResult= new ArrayList<User>();
        for(User user : users){
            Log.d("search", "loop");
            if(user.getName().toLowerCase().contains(querry.toLowerCase())){
                Log.d("search", user.getName() +", querry: " + querry);
                searchResult.add(user);
            }
        }
        return searchResult;
    }

    private void getDataFromDatabase() {
        callUsers();
        // callBerths();
    }

    private void callBerths() {
        //TODO: Make the URl be a parameter in the method
        String URL = "http://test.palholmen.se/api/berth/list/xml";


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(view);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ParseXMLBerthsToObject(response);
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue
        queue.add(stringRequest);

    }

    private void callUsers() {
        //TODO: Make the URl be a parameter in the method
        String URL = "http://test.palholmen.se/api/user/list/xml";

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(view);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ParseXMLUsersToObject(response);
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue
        queue.add(stringRequest);

    }

    private void ParseXMLUsersToObject(String data) throws XmlPullParserException, IOException {

        String id="";
        String name="";
        String phoneNumber="";
        boolean member=false;
        String email="";
        String city="";
        String personalIdentityNumber="";
        String cellphoneNumber="";
        Contract[] contracts;
        //Create the parser
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();


        parser.setInput(new StringReader(data));
        int eventType = parser.getEventType();


        //loop through the string and look for tags corresponding to the class
        while (eventType != XmlPullParser.END_DOCUMENT) {

            //Basic info
            if (eventType == XmlPullParser.START_TAG) {

                if (parser.getName().equalsIgnoreCase("id")) {
                    id = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("name")) {
                    name = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("phone")) {
                    phoneNumber = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("member")) {
                    String temp = parser.nextText();
                    if (temp.equalsIgnoreCase("0"))
                        member = false;
                    else
                        member = true;
                } else if (parser.getName().equalsIgnoreCase("email")) {
                    email = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("city")) {
                    city = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("personalnumber")) {
                    personalIdentityNumber = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("phoneCell")) {
                    cellphoneNumber = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("createtime")) {
                    skip(parser);
                } else if (parser.getName().equalsIgnoreCase("contracts")) {
                    skip(parser);
                }


            } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                users.add(new User(id, name, phoneNumber, member, email, city, personalIdentityNumber, cellphoneNumber, null));


            }
            eventType = parser.next();

        }

        //TODO: Combined loading okeyGO
        done=true;
        loadingDialog.dismiss();

        Log.d("portlux", "done loading");
    }
    public boolean internetRequestDone(){
        return done;
    }

    /**
     * Skips from the current start_tag to the end and consumes all info in between
     */
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private void ParseXMLBerthsToObject(String data) throws XmlPullParserException, IOException {

        //Create the parser
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();


        parser.setInput(new StringReader(data));


        //Define temp variables to create a berth from
        String tempId;
        String tempPier;
        String tempName;
        String tempHarbour;
        Contract tempAccessRight;
        Contract tempTenancy;

        //Define temp variables to create a contract from
        String tempContractId = "";
        Contract.contractType contractType = Contract.contractType.ACCESSRIGHT;
        String tempBerthID = "";
        String tempUserId = "";
        boolean tempFree = false;
        boolean tempVacant = false;

        int eventType = parser.getEventType();


        //loop through the string and look for tags corresponding to the class
        while (eventType != XmlPullParser.END_DOCUMENT) {

            //Basic info
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName().equalsIgnoreCase("id")) {
                    Log.d("Parser id", parser.nextText().toString());
                } else if (parser.getName().equalsIgnoreCase("pier")) {
                    Log.d("Parser id", parser.nextText().toString());


                    //contract one
                } else if (parser.getName().equalsIgnoreCase("contracts")) {
                    eventType = parser.next();

                    while (eventType == XmlPullParser.START_TAG && !parser.getName().equalsIgnoreCase("contracts")) {

                        if (eventType == XmlPullParser.START_TAG) {
                            if (parser.getName().equalsIgnoreCase("id")) {
                                Log.d("Parser contract", parser.nextText().toString());
                            } else if (parser.getName().equalsIgnoreCase("Type")) {
                                Log.d("Parser contract", parser.nextText().toString());


                            }
                        }
                        eventType = parser.next();


                    }
                }
            }

            // Log.d("Parser", "new item");
            eventType = parser.next();
        }
    }
}

