package com.portlux.portluxpocket;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DetailedViewActivity extends ActionBarActivity {
    TextView textViewName;
    TextView textViewMember;
    TextView textViewPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        textViewName = (TextView)findViewById(R.id.tvName);
        textViewMember = (TextView)findViewById(R.id.tvSetMember);
        textViewPhone = (TextView)findViewById(R.id.tvCellphone);

        textViewName.setText(getIntent().getExtras().getString("name"));
        textViewPhone.setText(getIntent().getExtras().getString("phone"));
       if(getIntent().getExtras().getBoolean("member")){
           textViewMember.setText("Ja");
       }else{
           textViewMember.setText("Nej");
       }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_view, menu);
        return true;
    }
}
