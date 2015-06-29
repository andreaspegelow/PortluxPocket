package com.portlux.portluxpocket;

/**
 * Created by Andreas Pegelow on 2015-06-18.
 */
public class Berth {
    private final String id;
    private final String pier;
    private final String name;
    private final String harbour;
    private final Contract accessRight;
    private final Contract tenancy;

    public Berth(String ID, String pier, String harbour, String name, Contract ownership, Contract tenancy ){
        id = ID;

        this.pier = pier;
        this.name = name;
        this.harbour = harbour;
        this.accessRight = ownership;
        this.tenancy = tenancy;
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
        return pier;
    }

}
