package com.portlux.portluxpocket;

import java.io.Serializable;

/**
 * Created by Andreas Pegelow on 2015-06-18.
 */
public class Berth implements Serializable {
    private final String id;
    private final String pier;
    private final String name;
    private final String harbour;
    private final String ownership;
    private final String tenancy;


    public Berth(String ID, String pier, String harbour, String name, String ownershipID, String tenancyID ){
        id = ID;
        this.pier = pier;
        this.name = name;
        this.harbour = harbour;
        this.ownership = ownershipID;
        this.tenancy = tenancyID;
    }


    public String getHarbour() {
        return harbour;
    }

    /**
     *
     * @returns the name of the berth on the pier
     */
    public String getName() {
        return name;
    }

    public String getPier() {
        return pier;
    }

    /**
     *
     * @returns the Berth which is a concatenation of the Pier and the name
     */
    public String getBerth() {
        return pier+name;
    }

    public String getOwnershipID() {
        return ownership;
    }

    public String getTenancyID() {
        return tenancy;
    }

    public String getId() {
        return id;
    }

}
