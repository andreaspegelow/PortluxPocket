package com.portlux.portluxpocket;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Andreas on 2015-07-26.
 */
public class Usertab extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private SearchListAdapter listAdapter;
    private ArrayList<User> data;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.usertab,container,false);
        listView = (ListView) v.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        listAdapter = new SearchListAdapter(inflater);
        listView.setAdapter(listAdapter);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = data.get(position);

        // create an Intent to go to the detaildview
        Intent intent = new Intent(context, DetailedViewActivity.class);


        //Put content
        intent.putExtra("id", user);


        // start the next Activity using your Intent
        startActivity(intent);

    }
    public void updateData(ArrayList data){
        this.data= data;
        listAdapter.updateData(data);

    }
    public void setInitData(Context context, ArrayList users, ArrayList tickets){
        this.context = context;
        data = users;
        listAdapter.setInitData(users, tickets);
    }
}
