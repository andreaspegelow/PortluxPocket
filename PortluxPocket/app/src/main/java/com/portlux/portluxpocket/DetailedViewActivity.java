package com.portlux.portluxpocket;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DetailedViewActivity extends Activity {
    TextView textViewName;
    TextView textViewMember;
    TextView textViewPhone;
    TextView textViewPersonalNumber;
    TextView textViewEmail;
    ListView list;
    ArrayAdapter adapter;
    ArrayList a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        textViewName = (TextView)findViewById(R.id.tvName);
        textViewMember = (TextView)findViewById(R.id.tvSetMember);
        textViewPhone = (TextView)findViewById(R.id.tvCellphone);
        textViewPersonalNumber = (TextView)findViewById(R.id.tvPersonalNumber);
        textViewEmail = (TextView)findViewById(R.id.tvEmail);

        list = (ListView)findViewById(R.id.itemList);

        a= new ArrayList();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, a);
        list.setAdapter(adapter);

        User user = (User)getIntent().getExtras().getSerializable("id");

        textViewName.setText(user.getName());
        textViewPhone.setText(user.getCellphoneNumber());
        textViewEmail.setText(user.getEmail());
        textViewPersonalNumber.setText(user.getPersonalIdentityNumber());
       if(user.isMember()){
           textViewMember.setText("Ja");
       }else{
           textViewMember.setText("Nej");
       }


        a.add("Nyttjanderätter");

        for(String id: user.getOwnershipContracts()){
            a.add(id);

        }
        a.add("Hyresrätter");
        for(String id: user.getTenancyContracts()){
            a.add(id);

        }
        a.add("Köplatser");
        for(String id: user.getTickets()){
            a.add(id);

        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_view, menu);
        return true;
    }
}
