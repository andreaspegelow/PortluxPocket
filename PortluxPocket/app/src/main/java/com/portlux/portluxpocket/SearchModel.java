package com.portlux.portluxpocket;

import java.util.ArrayList;

/**
 * Created by Andreas Pegelow on 2015-06-17.
 */
public class SearchModel {

    ArrayList users;
    ArrayList berths;
    
    public SearchModel(){
        retrieveData();
        
    }
    public void search(String querry){


    }

    private void retrieveData() {
        retrieveUsers();
        retrieveBerths();
    }

    private void retrieveBerths() {
    }

    private void retrieveUsers() {
    }
}
