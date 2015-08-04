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
public class Usertab extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private SearchUserAdapter listAdapter;
    private ArrayAdapter emptylist;
    private ArrayList<User> data;
    private ArrayList<Contract> contracts;
    private ArrayList<Ticket> tickets;
    private Context context;
    private ArrayList msg = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.usertab, container, false);
        listView = (ListView) v.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        msg.add("Inga resultat hittades");

        listAdapter = new SearchUserAdapter(inflater);
        listView.setAdapter(listAdapter);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listView.getAdapter().equals(listAdapter)) {
            User user = data.get(position);

            // create an Intent to go to the detaildview
            Intent intent = new Intent(context, DetailedUserViewActivity.class);


            //Put content
            intent.putExtra("user", user.getId());

            // start the next Activity using your Intent
            startActivity(intent);
        }

    }

    public void updateData(ArrayList<User> data) {
        this.data = data;
        listAdapter.updateData(data);

    }

    public void setInitData(Context context, ArrayList<Ticket> tickets, ArrayList<Contract> contracts) {
        this.context = context;
        this.contracts = contracts;
        this.tickets=tickets;

        listAdapter.setInitData(contracts, tickets);
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
