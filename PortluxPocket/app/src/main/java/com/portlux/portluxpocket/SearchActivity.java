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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity implements TextWatcher, AdapterView.OnItemClickListener, PropertyChangeListener {
    private EditText searchField;


    private ArrayAdapter adapter;
    private boolean userDone = false;
    private boolean berthDone = false;

    private SearchModel model;
    private ArrayList<User> searchResult = new ArrayList<User>();
    private ArrayList search = new ArrayList();

    private Toolbar toolbar;

    private boolean searchMode = false;

    //tab
    private ViewPager pager;
    private ViewPagerAdapter tabsAdapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[] = {"Användare", "Båtplatser"};
    private int Numboftabs = 2;


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


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, search);


        tabsAdapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
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
        Log.d("ds", "Paused");
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
        searchResult = model.search(searchField.getText().toString(), searchMode);
        tabsAdapter.updateData(searchResult);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = searchResult.get(position);

        // create an Intent to go to the detaildview
        Intent intent = new Intent(this, DetailedViewActivity.class);

        //Put content
        intent.putExtra("id", user);


        // start the next Activity using your Intent
        startActivity(intent);

    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getNewValue().equals("Berth loading done")) {
            berthDone = true;

        } else if (event.getNewValue().equals("Users loading done")) {
            userDone = true;
        }


       if (userDone && berthDone) {
           tabsAdapter.setInitData(this, model.getContracts(), model.getTickets());
            //do an empty search and fill the list
            searchResult = model.search("", searchMode);
            tabsAdapter.updateData(searchResult);
        }


    }
}
