package com.portlux.portluxpocket;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andreas Pegelow on 2015-07-08.
 */
public class SearchListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<User> data;

    public SearchListAdapter(LayoutInflater inflater) {

        this.inflater = inflater;
        data = new ArrayList<User>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(data.get(position).getId());
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
            holder.textViewName = (TextView) convertView.findViewById(R.id.rowName);
            holder.textViewTenancy = (TextView) convertView.findViewById(R.id.rowBerthsTenancy);
            holder.textViewOwnership = (TextView) convertView.findViewById(R.id.rowBerthsOwnership);
            holder.textViewQueue = (TextView) convertView.findViewById(R.id.rowQueue);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive steps
            // and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }

        User user = (User) getItem(position);

        //Set the name
        holder.textViewName.setText(user.getName());

        String ownershipContracts = "";
        String tenancyContracts = "";


        ArrayList<Contract> oContracts = user.getOwnershipContracts();

        ArrayList<Contract> tContracts = user.getTenancyContracts();


        //Create the strings contracts to display.
        for (Contract contract : oContracts) {
            ownershipContracts += contract.getBerth() + ", ";
        }
        for (Contract contract : tContracts) {
            tenancyContracts += contract.getBerth() + ", ";
        }

        //remove the last ","
        if (ownershipContracts.length() > 0) {
            ownershipContracts = ownershipContracts.substring(0, ownershipContracts.length() - 2);
        }
        if (tenancyContracts.length() > 0) {
            tenancyContracts = tenancyContracts.substring(0, tenancyContracts.length() - 2);
        }


        //set the contract strings
        holder.textViewOwnership.setText(ownershipContracts);
        holder.textViewTenancy.setText(tenancyContracts);

        //Set queue
        for (Ticket ticket : user.getTickets()) {
            holder.textViewQueue.setText(ticket.getQueue());
        }


        return convertView;
    }

    private static class ViewHolder {
        public TextView textViewName;
        public TextView textViewTenancy;
        public TextView textViewOwnership;
        public TextView textViewQueue;

    }

    public void updateData(ArrayList<User> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
