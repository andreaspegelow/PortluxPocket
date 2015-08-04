package com.portlux.portluxpocket;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity implements TextWatcher, PropertyChangeListener {
    private EditText searchField;

    private boolean userDone = false;
    private boolean berthDone = false;

    private SearchModel model;
    private ArrayList<Berth> berthSearchResult = new ArrayList<Berth>();
    private ArrayList<Berth> freeBerthSearchResult = new ArrayList<Berth>();
    private ArrayList<User> userSearchResult = new ArrayList<User>();

    private Toolbar toolbar;

    //tab
    private ViewPager pager;
    private ViewPagerAdapter tabsAdapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[] = {"Kunder", "Båtplatser", "Gästplatse"};
    private int Numboftabs = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);

        //Create the model
        model = new SearchModel(this);
        model.addChangeListener(this);

        //UI connection
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        searchField = (EditText) findViewById(R.id.searchField);
        searchField.addTextChangedListener(this);


        tabsAdapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(tabsAdapter);


        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });


        tabs.setViewPager(pager);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        userSearchResult = model.searchForUser(searchField.getText().toString());
        if (!userSearchResult.isEmpty()) {
            tabsAdapter.setUserListFull();
            tabsAdapter.updateUserData(userSearchResult);
        } else {
            tabsAdapter.setUserListEmpty();
        }
        berthSearchResult = model.searchForBerth(searchField.getText().toString());
        if (!berthSearchResult.isEmpty()) {
            tabsAdapter.setBerthListFull();
            tabsAdapter.updateBerthData(berthSearchResult);
        } else {
            tabsAdapter.setBerthListEmpty();
        }
        freeBerthSearchResult = model.searchForFreeBerth(searchField.getText().toString());
        if (!freeBerthSearchResult.isEmpty()) {
            tabsAdapter.setFreeBerthListFull();
            tabsAdapter.updateFreeBerthData(freeBerthSearchResult);
        } else {
            tabsAdapter.setFreeBerthListEmpty();
        }

    }




    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getNewValue().equals("Berth loading done")) {
            berthDone = true;

        } else if (event.getNewValue().equals("Users loading done")) {
            userDone = true;
        }


        if (userDone && berthDone) {
            tabsAdapter.setInitData(this, model.getContracts(), model.getTickets(), model.getUsers());
            //do an empty search and fill the list
            userSearchResult = model.searchForUser("");
            tabsAdapter.updateUserData(userSearchResult);
            berthSearchResult = model.searchForBerth("");
            tabsAdapter.updateBerthData(berthSearchResult);
            freeBerthSearchResult = model.searchForFreeBerth("");
            tabsAdapter.updateFreeBerthData(freeBerthSearchResult);
        }


    }


}
