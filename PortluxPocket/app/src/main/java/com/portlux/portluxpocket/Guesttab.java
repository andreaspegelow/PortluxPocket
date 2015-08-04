package com.portlux.portluxpocket;

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

import java.util.ArrayList;

/**
 * Created by Andreas on 2015-07-26.
 */
public class Guesttab extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private SearchFreeBerthAdapter listAdapter;
    private ArrayAdapter emptylist;
    private ArrayList<Berth> data;
    private Context context;
    private ArrayList msg = new ArrayList();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.guesttab, container, false);
        listView = (ListView) v.findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        msg.add("Inga resultat hittades");

        listAdapter = new SearchFreeBerthAdapter(inflater);
        listView.setAdapter(listAdapter);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listView.getAdapter().equals(listAdapter)) {

            Berth berth = data.get(position);

            // create an Intent to go to the detaildview
            Intent intent = new Intent(context, DetailedBerthViewActivity.class);
            intent.putExtra("berth", berth.getId());

            startActivity(intent);
        }

    }

    public void updateData(ArrayList<Berth> data) {
        this.data = data;
        listAdapter.updateData(data);

    }

    public void setInitData(Context context) {
        this.context = context;

        emptylist = new ArrayAdapter(context, android.R.layout.simple_list_item_1, msg);
    }

    /**
     * Switches between an empty list and on filled with users
     */
    public void setListEmpty() {
        listView.setAdapter(emptylist);

    }

    public void setListFull() {
        listView.setAdapter(listAdapter);

    }
}