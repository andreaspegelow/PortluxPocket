package com.portlux.portluxpocket;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    Usertab tab1 = new Usertab();
    Berthtab tab2 = new Berthtab();
    Guesttab tab3 = new Guesttab();

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return tab1;
            case 1:
                return tab2;
            case 2:
                return tab3;
        }

        return null;

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    public void setInitData(Context context, ArrayList<Contract> contracts, ArrayList<Ticket> tickets, ArrayList<User> users) {
        tab1.setInitData(context, tickets, contracts);
        tab2.setInitData(context, contracts, users);
        tab3.setInitData(context);


    }

    public void updateUserData(ArrayList<User> data) {
        tab1.updateData(data);

    }

    public void setUserListEmpty() {
        tab1.setListEmpty();
    }

    public void setUserListFull() {
        tab1.setListFull();
    }


    public void updateBerthData(ArrayList<Berth> data) {
        tab2.updateData(data);
    }

    public void setBerthListFull() {
        tab2.setListFull();
    }

    public void setBerthListEmpty() {
        tab2.setListEmpty();

    }

    public void updateFreeBerthData(ArrayList<Berth> data) {
        tab3.updateData(data);
    }

    public void setFreeBerthListFull() {
        tab3.setListFull();
    }

    public void setFreeBerthListEmpty() {
        tab3.setListEmpty();

    }
}