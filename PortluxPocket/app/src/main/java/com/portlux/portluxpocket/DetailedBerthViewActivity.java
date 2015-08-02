package com.portlux.portluxpocket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.TextView;


public class DetailedBerthViewActivity extends ActionBarActivity {

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

        final Berth berth = instance.getBethWithId(getIntent().getExtras().getString("berth"));
        berthTextView.setText(berth.getBerth());
        harbourTextView.setText(berth.getHarbour());

        final User tempOwnershipUser = instance.getUserWithId(berth.getOwnershipUserId());

        if (tempOwnershipUser != null) {
            ownerTextView.setText(tempOwnershipUser.getFullName());
            ownerTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(),DetailedUserViewActivity.class);
                    intent.putExtra("user", tempOwnershipUser.getId());
                    startActivity(intent);
                }
            });
        } else {
            ((ViewManager) ownerTextView.getParent()).removeView(ownerTextView);
            TextView title = (TextView) findViewById(R.id.textViewOwnershipTitle);
            ((ViewManager) title.getParent()).removeView(title);
        }
        final User tempTenacyUser = instance.getUserWithId(berth.getTenancyUserId());

        if (tempTenacyUser != null) {
            tenacyTextView.setText(tempTenacyUser.getFullName());
            tenacyTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), DetailedUserViewActivity.class);
                    intent.putExtra("user", tempTenacyUser.getId());
                    startActivity(intent);
                }
            });
        } else {
            TextView title = (TextView) findViewById(R.id.textViewTenacyTitle);
            ((ViewManager) tenacyTextView.getParent()).removeView(tenacyTextView);
            ((ViewManager) title.getParent()).removeView(title);
        }

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
}
