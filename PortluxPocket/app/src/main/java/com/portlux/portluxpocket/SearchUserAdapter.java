package com.portlux.portluxpocket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andreas Pegelow on 2015-07-08.
 */
public class SearchUserAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<User> users;
    private ArrayList<Contract> contracts;
    private ArrayList<Ticket> tickets;
    private boolean empty = false;

    public SearchUserAdapter(LayoutInflater inflater) {

        this.inflater = inflater;
        users = new ArrayList<User>();

    }

    public void setInitData(ArrayList<Contract> contracts, ArrayList<Ticket> tickets) {
        this.contracts = contracts;
        this.tickets = tickets;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(users.get(position).getId());
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
            holder = (ViewHolder) convertView.getTag();
        }

        User user = (User) getItem(position);
        Data instance = Data.getInstance();

        //Set the name
        holder.textViewName.setText(user.getFirstName() + " " + user.getLastName());

        String ownershipContracts = "";
        String tenancyContracts = "";

        //Create the strings contracts to display.
        for (String id : user.getOwnershipContracts()) {
            ownershipContracts += instance.getContractWithId(id).getBerth() + ", ";
        }
        for (String id : user.getTenancyContracts()) {
            tenancyContracts += instance.getContractWithId(id).getBerth() + ", ";
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


        String queues = "";

        //Set queue string
        for (String id : user.getTickets()) {
            for (Ticket ticket : tickets) {
                if (id.equalsIgnoreCase(ticket.getId())) {
                    queues += ticket.getQueue() + ", ";
                }
            }
        }
        for (String id : user.getTickets()) {
            instance.getTicketWithId(id);
            queues += instance.getTicketWithId(id).getQueue() + ", ";
        }
        //remove the lats ", "
        if (queues.length() > 0) {
            queues = queues.substring(0, queues.length() - 2);
        }

        holder.textViewQueue.setText(queues);


        return convertView;
    }

    private static class ViewHolder {
        public TextView textViewName;
        public TextView textViewTenancy;
        public TextView textViewOwnership;
        public TextView textViewQueue;


    }

    public void updateData(ArrayList<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
}
