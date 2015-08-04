package com.portlux.portluxpocket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pegelow on 2015-08-03.
 */

public class SearchFreeBerthAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Berth> berths;
    Berth berth;



    public SearchFreeBerthAdapter(LayoutInflater inflater) {

        this.inflater = inflater;
        berths = new ArrayList<Berth>();

    }


    @Override
    public int getCount() {
        return berths.size();
    }

    @Override
    public Object getItem(int position) {
        return berths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(berths.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        // check if the view already exists
        if (convertView == null) {

            // Inflate the custom row layout.
            convertView = inflater.inflate(R.layout.rowberth, null);

            // create a new Holder with subviews
            holder = new ViewHolder();
            holder.textViewBerth = (TextView) convertView.findViewById(R.id.rowBerth);
            holder.textViewFrom= (TextView) convertView.findViewById(R.id.rowFrom);
            holder.textViewUntil = (TextView) convertView.findViewById(R.id.rowUntil);


            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive steps
            holder = (ViewHolder) convertView.getTag();
        }

        berth = (Berth) getItem(position);

        //Set
        holder.textViewBerth.setText(berth.getBerth());
        for(GuestPeriod period: berth.getFreePeriods()){
            if(period.isNow()){

                //TODO: format dates
                holder.textViewFrom.setText(period.getFrom().getYear() + "-0" + period.getFrom().getMonthOfYear() + "-" + period.getFrom().getDayOfMonth());
                holder.textViewUntil.setText(period.getUntil().getYear() + "-" + period.getUntil().getMonthOfYear() + "-" + period.getUntil().getDayOfMonth());
            }
        }



        return convertView;
    }


    private static class ViewHolder {
        public TextView textViewBerth;
        public TextView textViewFrom;
        public TextView textViewUntil;



    }

    public void updateData(ArrayList<Berth> berths) {
        this.berths = berths;
        notifyDataSetChanged();
    }

}

