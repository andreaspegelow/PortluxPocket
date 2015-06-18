package com.portlux.portluxpocket;

/**
 * Created by Andreas on 2015-06-18.
 */
public class Berth {
    private final String pier;
    private final String name;
    private final String harbour;
    private final String accessRightsUserID;
    private final String tenancyUserID;
    private final String berthID;


    public Berth(String pier, String name, String harbour, String accessRightsUserID, String tenancyUserID, String berthID){

        this.pier = pier;
        this.name = name;
        this.harbour = harbour;
        this.accessRightsUserID = accessRightsUserID;
        this.tenancyUserID = tenancyUserID;
        this.berthID = berthID;
    }

    public String getTenancyUserID() {
        return tenancyUserID;
    }

    public String getAccessRightsUserID() {
        return accessRightsUserID;
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

    public String getBerthID() {
        return berthID;
    }
}
