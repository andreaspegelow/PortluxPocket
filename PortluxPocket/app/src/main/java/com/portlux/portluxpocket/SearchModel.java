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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Andreas Pegelow on 2015-06-17.
 */
public class SearchModel {
    private Activity view;
    private ProgressDialog loadingDialog;


    private ArrayList<Berth> berths = new ArrayList<Berth>();
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Contract> contracts = new ArrayList<Contract>();
    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();

    private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();


    public SearchModel(Activity view) {
        this.view = view;
        loadingDialog = new ProgressDialog(view);
        loadingDialog.setMessage(Values.LOADINGMESSAGE);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        getDataFromDatabase();


    }

    /**
     * searches for user that has the name or berth that that contains the querry
     * sortMode determine if the list should be sorted based on the name or berth
     * 0 = name
     * 1 = berth
     *
     * @param querry
     * @param sortMode sets the mode
     * @return
     */
    public ArrayList<User> search(String querry, final boolean sortMode) {
        ArrayList<User> searchResult = new ArrayList<User>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(querry.toLowerCase())) {
                searchResult.add(user);
            }
        }


        //sort by name
        Collections.sort(searchResult, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {

                return user1.getName().compareTo(user2.getName());
            }
        });


        return searchResult;
    }

    private void getDataFromDatabase() {
        callUsers();
        callBerths();
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Values.REQUEST_TIME_OUT,
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Values.REQUEST_TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue
        queue.add(stringRequest);

    }

    private void ParseXMLUsersToObject(String data) throws XmlPullParserException, IOException {
        boolean parsingContract = false;
        boolean parsingTicket = false;

        boolean realContract = false;
        boolean realTicket = false;

        String id = "";
        String name = "";
        String phoneNumber = "";
        boolean member = false;
        String email = "";
        String city = "";
        String personalIdentityNumber = "";
        String cellphoneNumber = "";
        ArrayList<String> tempTenancyContracts = new ArrayList<String>();
        ArrayList<String> tempOwnershipContracts = new ArrayList<String>();

        //Define temp variables to create a contract from
        String tempContractId = "";
        Contract.contractType tempContractType = Contract.contractType.OWNERSHIP;
        String tempBerthID = "";
        String tempBerth = "";
        String tempUserId = "";
        boolean tempFree = false;
        boolean tempVacant = false;

        ArrayList<String> tempTickets = new ArrayList<String>();

        //Define temp variables to create a ticket from
        String tempTicketId = "";
        String tempQueue = "";
        String tempPlace = "";
        String tempStart = "";
        String tempWish = "";
        String tempUserID = "";
        boolean tempOwnership = false;
        boolean tempTenancy = false;

        //Create the parser
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();

        parser.setInput(new StringReader(data));
        int eventType = parser.getEventType();


        //loop through the string and look for tags corresponding to the class
        while (eventType != XmlPullParser.END_DOCUMENT) {

            //Basic info
            if (eventType == XmlPullParser.START_TAG && !parsingContract && !parsingTicket) {

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
                    else {
                        member = true;
                    }
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
                    if(!parser.isEmptyElementTag()){
                        parsingContract = true;
                    }else{
                        skip(parser);
                    }

                } else if (parser.getName().equalsIgnoreCase("tickets")) {
                    if(!parser.isEmptyElementTag()){
                        parsingTicket = true;
                    }else{
                        skip(parser);
                    }
                }
                //Parsing contracts
            } else if (eventType == XmlPullParser.START_TAG && parsingContract && !parsingTicket) {
                if (parser.getName().equalsIgnoreCase("id")) {
                    tempContractId = parser.nextText();
                    realContract = true;
                } else if (parser.getName().equalsIgnoreCase("type")) {
                    if (parser.nextText().equalsIgnoreCase("Hyresrätt")) {
                        tempContractType = Contract.contractType.TENANCY;
                        tempTenancy = true;
                    } else {
                        tempOwnership = true;
                        tempContractType = Contract.contractType.OWNERSHIP;
                    }

                } else if (parser.getName().equalsIgnoreCase("berthid")) {
                    tempBerthID = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("berth")) {
                    tempBerth = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("userid")) {
                    tempUserId = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("free")) {
                    if (parser.nextText().equalsIgnoreCase("0")) {
                        tempFree = false;
                    } else {
                        tempFree = true;
                    }
                } else if (parser.getName().equalsIgnoreCase("vacant")) {
                    if (parser.nextText().equalsIgnoreCase("0")) {
                        tempVacant = false;
                    } else {
                        tempVacant = true;

                    }
                }
                //Parsing ticket
            } else if (eventType == XmlPullParser.START_TAG && !parsingContract && parsingTicket) {
                if (parser.getName().equalsIgnoreCase("id")) {
                    tempTicketId = parser.nextText();
                    realTicket = true;
                } else if (parser.getName().equalsIgnoreCase("queue")) {
                    tempQueue = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("place")) {
                    tempPlace = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("start")) {
                    tempStart = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("wish")) {
                    tempWish = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("userid")) {
                    tempUserID = parser.nextText();
                } else if (parser.getName().equalsIgnoreCase("contracttypes")) {
                    String temp = parser.nextText();
                    if (temp.equalsIgnoreCase("Nyttjanderätt")) {
                        tempOwnership = true;
                    } else if (temp.equalsIgnoreCase("Hyresrätt")) {
                        tempTenancy = true;
                    }
                }


                //Add the user to the list along with the contracts and tickets
            } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                users.add(new User(id, name, phoneNumber, member, email, city, personalIdentityNumber, cellphoneNumber, (ArrayList) tempTenancyContracts.clone(), (ArrayList) tempOwnershipContracts.clone(), (ArrayList) tempTickets.clone()));
                tempOwnershipContracts.clear();
                tempTenancyContracts.clear();
                tempTickets.clear();


                //Add the current contract to the correct contract list.
            } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("contracts") ) {

                if (tempOwnership) {
                    tempOwnershipContracts.add(tempContractId);
                } else if (tempTenancy) {
                    tempTenancyContracts.add(tempContractId);

                }

                contracts.add(new Contract(tempContractId, tempContractType, tempBerthID, tempBerth, tempUserId, tempFree, tempVacant));
                realContract = false;
                tempOwnership = false;
                tempTenancy = false;
                parsingContract = false;

                //Add the ticket
            } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("tickets")  ) {

                tempTickets.add(tempTicketId);
                tickets.add(new Ticket(tempTicketId, tempQueue, tempPlace, tempStart, tempWish, tempUserID, tempOwnership, tempTenancy));
                realTicket = false;
                tempOwnership = false;
                tempTenancy = false;
                parsingTicket = false;

            }
            eventType = parser.next();

        }

        //TODO: Combined loading okeyGO

        //Sorting
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {

                return user1.getName().compareTo(user2.getName());
            }
        });
        loadingDialog.dismiss();
        notifyListeners("Users loading done", "not done", "Users loading done");
      

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
        String tempId = "";
        String tempPier = "";
        String tempName = "";
        String tempHarbour = "";
        String tempOwnershipID = "";
        String tempTenancyID = "";

        //Define temp variables to create a contract from
        String tempContractId = "";
        String contractType = "";


        boolean parsingContract = false;

        int eventType = parser.getEventType();


        //loop through the string and look for tags corresponding to the class
        while (eventType != XmlPullParser.END_DOCUMENT) {

            //Basic info
            if (eventType == XmlPullParser.START_TAG && !parsingContract ) {
                if (parser.getName().equalsIgnoreCase("id")) {
                    tempId = parser.nextText().toString();
                } else if (parser.getName().equalsIgnoreCase("pier")) {
                    tempPier = parser.nextText().toString();
                } else if (parser.getName().equalsIgnoreCase("harbour")) {
                    tempHarbour = parser.nextText().toString();
                } else if (parser.getName().equalsIgnoreCase("name")) {
                    tempName = parser.nextText().toString();
                } else if (parser.getName().equalsIgnoreCase("contracts")) {
                    parsingContract = true;
                }
            } else if (eventType == XmlPullParser.START_TAG && parsingContract) {
                if (parser.getName().equalsIgnoreCase("id")) {
                    tempContractId = parser.nextText().toString();
                } else if (parser.getName().equalsIgnoreCase("type")) {
                    contractType = parser.nextText().toString();
                }


            } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                berths.add(new Berth(tempId, tempPier, tempHarbour, tempName, tempOwnershipID, tempTenancyID));
                tempOwnershipID = null;
                tempTenancyID = null;

            } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("contracts")) {
                parsingContract = false;

                if (contractType.equalsIgnoreCase("Nyttjanderätt")) {
                    tempOwnershipID = tempContractId;
                } else {
                    tempTenancyID = tempContractId;
                }


            }
            eventType = parser.next();
        }
        notifyListeners("Berth loading done", "not done", "Berth loading done");

    }

    private void notifyListeners(String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }


    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public ArrayList<Berth> getBerths() {
        return berths;
    }

}

