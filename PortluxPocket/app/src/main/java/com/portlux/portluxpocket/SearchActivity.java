package com.portlux.portluxpocket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity implements TextWatcher, AdapterView.OnItemClickListener {
    EditText searchField;
    ListView listView;
    SearchListAdapter listAdapter;
    SearchModel model;
    ArrayList<User> searchResult = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);



        searchField = (EditText)findViewById(R.id.searchField);
        searchField.addTextChangedListener(this);
        listView = (ListView)findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        listAdapter = new SearchListAdapter(getLayoutInflater());
        listView.setAdapter(listAdapter);

        //Create the model
        model = new SearchModel(this);


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
        intent.putExtra("name",user.getName());
        intent.putExtra("member",user.isMember());
        intent.putExtra("phone",user.getCellphoneNumber());

        // start the next Activity using your Intent
        startActivity(intent);

    }
}
