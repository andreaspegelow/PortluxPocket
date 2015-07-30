package com.portlux.portluxpocket;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class DetailedUserViewActivity extends ActionBarActivity {

    ArrayAdapter adapter;
    ArrayList a;
    private LinearLayout tenacyrightsList;
    private LinearLayout ownershipList;
    private LinearLayout ticketList;
    private ArrayList<Contract> ownershipContracts = new ArrayList<Contract>();
    private ArrayList<Contract> tenacyContracts = new ArrayList<Contract>();
    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    private final static int MAX_PASSES = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_user_view);

        //UI connection
        TextView textViewName = (TextView) findViewById(R.id.tvName);
        TextView textViewMember = (TextView) findViewById(R.id.tvSetMember);
        TextView textViewPhone = (TextView) findViewById(R.id.tvCellphone);
        TextView textViewPersonalNumber = (TextView) findViewById(R.id.tvPersonalNumber);
        TextView textViewEmail = (TextView) findViewById(R.id.tvEmail);

        LinearLayout ownershipListFrame = (LinearLayout) findViewById(R.id.ownershipListFrame);
        LinearLayout tenacyrightsListFrame = (LinearLayout) findViewById(R.id.tenacyrightsListFrame);
        LinearLayout ticketListFrame = (LinearLayout) findViewById(R.id.ticketListFrame);

        ownershipList = (LinearLayout) findViewById(R.id.ownershipList);
        tenacyrightsList = (LinearLayout) findViewById(R.id.tenacyrightsList);
        ticketList = (LinearLayout) findViewById(R.id.ticketList);

        TextView tenacyrightsListTitle = (TextView) findViewById(R.id.tenacyrightsListTitle);
        TextView ownershipListTitle = (TextView) findViewById(R.id.OwnershipTitle);
        TextView ticketListTitle = (TextView) findViewById(R.id.ticketListTitle);

        User user = (User) getIntent().getExtras().getSerializable("id");
        for (int i = 0; i < MAX_PASSES; i++) {
            ownershipContracts.add((Contract) getIntent().getExtras().getSerializable("ownership" + (i + 1)));
        }
        for (int i = 0; i < MAX_PASSES; i++) {
            tenacyContracts.add((Contract) getIntent().getExtras().getSerializable("tenacy" + (i + 1)));
        }
        for (int i = 0; i < MAX_PASSES; i++) {
            tickets.add((Ticket) getIntent().getExtras().getSerializable("ticket" + (i + 1)));
        }

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // fill the ownerrightlist
        // or remove it if they dont exist for the particular user
        if (user.getOwnershipContracts().size() > 0) {
            if (user.getOwnershipContracts().size() == 1) {
                //dynamically set title
                Log.d("debug",user.getOwnershipContracts().size() + "");
                ownershipListTitle.setText(getString(R.string.OwnershipTitle));

            }
            for (int i = 0; i < user.getOwnershipContracts().size(); i++) {
                final View view = inflater.inflate(R.layout.detailedownerright, null);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                TextView text = (TextView) view.findViewById(R.id.berth);
                CheckBox free = (CheckBox) view.findViewById(R.id.checkboxFree);
                text.setText(ownershipContracts.get(i).getBerth());
                free.setChecked(ownershipContracts.get(i).isFree());

                ownershipList.addView(view);

            }
        } else {
            //Remove the list completely
            ((ViewManager) ownershipListFrame.getParent()).removeView(ownershipListFrame);
        }

        // fill the tenancylist
        if (user.getTenancyContracts().size() > 0) {


            if (user.getTenancyContracts().size() == 1) {
                //dynamically set title
                tenacyrightsListTitle.setText(getString(R.string.TenancyTitle));

            }
            for (int i = 0; i < user.getTenancyContracts().size(); i++) {
                View view = inflater.inflate(R.layout.detailedtenacy, null);
                view.setOnClickListener(null);
                TextView text = (TextView) view.findViewById(R.id.berth);
                text.setText(tenacyContracts.get(i).getBerth());
                tenacyrightsList.addView(view);
            }
        } else {
            //Remove the list completely
            ((ViewManager) tenacyrightsListFrame.getParent()).removeView(tenacyrightsListFrame);
        }

        // fill the ticketlist
        if (user.getTickets().size() > 0) {
            //dynamically set title
            ticketListTitle.setText(getString(R.string.QueueTitleLong));
            if (user.getTickets().size() != 1) {
                ticketListTitle.setText(getString(R.string.QueueTitleLongPlural));
            }
            for (int i = 0; i < user.getTickets().size(); i++) {
                View view = inflater.inflate(R.layout.detailedticket, null);
                view.setOnClickListener(null);
                TextView queue = (TextView) view.findViewById(R.id.queue);
                TextView place = (TextView) view.findViewById(R.id.place);
                TextView wish = (TextView) view.findViewById(R.id.wish);
                queue.setText(tickets.get(i).getQueue());
                place.setText(tickets.get(i).getPlace());
                wish.setText(tickets.get(i).getWish());
                ticketList.addView(view);

            }
        }
        View view = inflater.inflate(R.layout.createticketbutton, null);
        ticketList.addView(view);


        //Set basic info
        textViewName.setText(user.getFirstName() + " " + user.getLastName());
        textViewPhone.setText(user.getCellphoneNumber());
        textViewEmail.setText(user.getEmail());
        textViewPersonalNumber.setText(user.getPersonalIdentityNumber());
        if (user.isMember()) {
            textViewMember.setText("Ja");
        } else {
            textViewMember.setText("Nej");
        }


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_user_view, menu);
        return true;
    }


}
