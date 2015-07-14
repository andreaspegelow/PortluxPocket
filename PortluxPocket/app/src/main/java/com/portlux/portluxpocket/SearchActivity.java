package com.portlux.portluxpocket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
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


public class SearchActivity extends Activity implements TextWatcher, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener, PropertyChangeListener{
    EditText searchField;
    ListView listView;
    SearchListAdapter listAdapter;
    ArrayAdapter adapter;

    SearchModel model;
    Switch switchButton;
    ArrayList<User> searchResult = new ArrayList<User>();
    ArrayList search = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);


        searchField = (EditText) findViewById(R.id.searchField);
        searchField.addTextChangedListener(this);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        listAdapter = new SearchListAdapter(getLayoutInflater());
        listView.setAdapter(listAdapter);

        switchButton = (Switch) findViewById(R.id.switchSearchView);
        switchButton.setOnCheckedChangeListener(this);
        search.add("s");
        search.add("e");
        search.add("t");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, search);


        //Create the model
        model = new SearchModel(this);
        model.addChangeListener(this);



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
        searchResult = model.search(searchField.getText().toString());
        listAdapter.updateData(searchResult);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = searchResult.get(position);

        // create an Intent to go to the detaildview
        Intent intent = new Intent(this, DetailedViewActivity.class);

        //Put content
        intent.putExtra("name", user.getName());
        intent.putExtra("member", user.isMember());
        intent.putExtra("phone", user.getCellphoneNumber());

        // start the next Activity using your Intent
        startActivity(intent);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switchButton.setText("Byt till User");
            listView.setAdapter(adapter);

        } else {
            switchButton.setText("Byt till Båt");
            listView.setAdapter(listAdapter);
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if(event.getNewValue().equals("done")){
            //do an empty search and fill the list
            searchResult = model.search("");
            listAdapter.updateData(searchResult);
        }




    }
}
