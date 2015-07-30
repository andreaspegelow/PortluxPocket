package com.portlux.portluxpocket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andreas on 2015-07-30.
 *
 */

public class DetailedUserListAdapter extends BaseAdapter {
    private static final int TYPE_OWNERITEM = 0;
    private static final int TYPE_TENACYITEM = 1;
    private static final int TYPE_TICKETITEM = 2;
    private static final int TYPE_MAX_COUNT = 3;
    private ArrayList data;
    private ArrayList ownerrights;
    private ArrayList tenancyrights;
    private ArrayList tickets;
    private LayoutInflater inflater;

    public DetailedUserListAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        ownerrights = new ArrayList();
    }
    public void addOwnershipItem(String id){
        data.add(id);
        ownerrights.add(data.size()-1);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ownerrights.size();
    }

    @Override
    public Object getItem(int position) {
        return ownerrights.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        // check if the view already exists
        if (convertView == null) {

            // Inflate the custom row layout.
            convertView = inflater.inflate(R.layout.rowuser, null);

            // create a new Holder with subviews
            holder = new ViewHolder();


            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive steps
            holder = (ViewHolder) convertView.getTag();
        }
        return null;
    }
    public static class ViewHolder {
        public TextView textView;
    }
}
