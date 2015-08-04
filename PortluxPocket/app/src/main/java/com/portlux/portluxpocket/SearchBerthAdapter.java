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
 * Created by Andreas Pegelow on 2015-07-08.
 */
public class SearchBerthAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
    private LayoutInflater inflater;
    private ArrayList<User> users;
    private ArrayList<Berth> berths;
    private ArrayList<Contract> contracts;
    private Context context;
    private ArrayList checked = new ArrayList<>();
    Berth berth;



    public SearchBerthAdapter(LayoutInflater inflater) {

        this.inflater = inflater;
        berths = new ArrayList<Berth>();

    }

    public void setInitData(Context context, ArrayList<Contract> contracts, ArrayList<User> users) {
        this.contracts = contracts;
        this.users = users;
        this.context = context;
        for(int i = 0 ; i< berths.size();i++) {
            checked.add(true);
        }
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
            holder.textViewTenancy = (TextView) convertView.findViewById(R.id.rowBerthTenancy);
            holder.textViewOwnership = (TextView) convertView.findViewById(R.id.rowBerthOwner);
            holder.checkBoxPower = (CheckBox) convertView.findViewById(R.id.checkboxPower);


            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive steps
            holder = (ViewHolder) convertView.getTag();
        }

        berth = (Berth) getItem(position);

        //Set
        holder.textViewBerth.setText(berth.getBerth());

        String owner = "";
        String tenacy = "";

        for (Contract contract : contracts) {
            if (contract.getId().equalsIgnoreCase(berth.getOwnershipContractId())) {
                for (User user : users) {
                    if (user.getId().equalsIgnoreCase(contract.getUserId())) {
                        owner = user.getFirstName().substring(0, 1) + ". " + user.getLastName();
                    }
                }
            }

        }
        for (Contract contract : contracts) {
            if (contract.getId().equalsIgnoreCase(berth.getTenancyContractId())) {
                for (User user : users) {
                    if (user.getId().equalsIgnoreCase(contract.getUserId())) {
                        tenacy = user.getFirstName().substring(0, 1) + ". " + user.getLastName();
                    }
                }
            }

        }

        holder.textViewOwnership.setText(owner);
        holder.textViewTenancy.setText(tenacy);


        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Write your message here.");


        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    private static class ViewHolder {
        public TextView textViewBerth;
        public TextView textViewTenancy;
        public TextView textViewOwnership;
        public CheckBox checkBoxPower;


    }

    public void updateData(ArrayList<Berth> berths) {
        this.berths = berths;
        notifyDataSetChanged();
    }

}

