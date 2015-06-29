package com.portlux.portluxpocket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class SearchActivity extends Activity implements TextWatcher {
    EditText searchField;
    ListView listView;
    ArrayAdapter listAdapter;
    SearchModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);



        searchField = (EditText)findViewById(R.id.searchField);
        searchField.addTextChangedListener(this);
        listView = (ListView)findViewById(R.id.list);
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(listAdapter);

        //Create the model
        model = new SearchModel(this);


    }
    public void notifyInternetRequestDone(){

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
        listAdapter.clear();
        for(User user: model.search(searchField.getText().toString())){
            listAdapter.add(user.getName());
        }
        listAdapter.notifyDataSetChanged();
    }


}
