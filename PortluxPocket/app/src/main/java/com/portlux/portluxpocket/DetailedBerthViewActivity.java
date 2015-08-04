package com.portlux.portluxpocket;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.DateTime;


public class DetailedBerthViewActivity extends ActionBarActivity {
    private ImageView freeIndicator;

    private TextView freeForGuestsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detailed_berth_view);
        final Data instance = Data.getInstance();
        //UI
        TextView berthTextView = (TextView) findViewById(R.id.textViewBerth);
        TextView harbourTextView = (TextView) findViewById(R.id.textViewHarbour);
        TextView ownerTextView = (TextView) findViewById(R.id.textViewOwnership);
        TextView tenacyTextView = (TextView) findViewById(R.id.textViewTenacy);
        freeForGuestsTextView = (TextView) findViewById(R.id.textViewFreeForGuestsTitle);
        freeIndicator = (ImageView) findViewById(R.id.freeIndicator);
        LinearLayout freePeriodList = (LinearLayout) findViewById(R.id.freePeriodList);


        final Berth berth = instance.getBethWithId(getIntent().getExtras().getString("berth"));
        berthTextView.setText(berth.getBerth());
        harbourTextView.setText(berth.getHarbour());

        if (berth.isFreeForGuests()) {
            setFree();
        }

        final User tempOwnershipUser = instance.getUserWithId(berth.getOwnershipUserId());

        if (tempOwnershipUser != null) {
            ownerTextView.setText(tempOwnershipUser.getFullName());
            ownerTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), DetailedUserViewActivity.class);
                    intent.putExtra("user", tempOwnershipUser.getId());
                    startActivity(intent);
                }
            });
        } else {
            ((ViewManager) ownerTextView.getParent()).removeView(ownerTextView);
            TextView title = (TextView) findViewById(R.id.textViewOwnershipTitle);
            ((ViewManager) title.getParent()).removeView(title);
        }
        final User tempTenancyUser = instance.getUserWithId(berth.getTenancyUserId());

        if (tempTenancyUser != null) {
            tenacyTextView.setText(tempTenancyUser.getFullName());
            tenacyTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), DetailedUserViewActivity.class);
                    intent.putExtra("user", tempTenancyUser.getId());
                    startActivity(intent);
                }
            });
        } else {
            TextView title = (TextView) findViewById(R.id.textViewTenacyTitle);
            ((ViewManager) tenacyTextView.getParent()).removeView(tenacyTextView);
            ((ViewManager) title.getParent()).removeView(title);
        }

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (GuestPeriod period : berth.getFreePeriods()) {
            View view = inflater.inflate(R.layout.guestperiodrow, null);
            TextView from = (TextView) view.findViewById(R.id.textViewFrom);
            TextView until = (TextView) view.findViewById(R.id.textViewUntil);

            //TODO: format dates
            from.setText(period.getFrom().getYear() + "-0" + period.getFrom().getMonthOfYear() + "-" + period.getFrom().getDayOfMonth());
            until.setText(period.getUntil().getYear() + "-" + period.getUntil().getMonthOfYear() + "-" + period.getUntil().getDayOfMonth());
            freePeriodList.addView(view);
        }
        View view = inflater.inflate(R.layout.createfreeperiodbutton, null);

        //TODO: why does this make it work? try remove the line below
        DateTime d = new DateTime(32, 12, 2, 3, 32);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                berth.addFreePeriod(new GuestPeriod(new DateTime(2015, 7, 11, 0, 0), new DateTime(2015, 10, 14, 0, 0)));
                instance.setBerthWithId(berth.getId(), berth);
                if (berth.isFreeForGuests()) {
                    Intent i = new Intent(getBaseContext(), DetailedBerthViewActivity.class);
                    i.putExtra("berth", berth.getId());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
            }
        });
        freePeriodList.addView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_berth_view, menu);
        return true;
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

    public void setFree() {
        freeForGuestsTextView.setText(getString(R.string.freeforguests));
        freeIndicator.setBackground(ContextCompat.getDrawable(this, R.drawable.freeidicator));
        Log.d("ds", "freeee");

    }
}
