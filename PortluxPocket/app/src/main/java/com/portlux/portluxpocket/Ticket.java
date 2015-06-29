package com.portlux.portluxpocket;

import java.util.Date;

/**
 * Created by Andreas on 2015-06-29.
 */
public class Ticket {
    private final String id;
    private final String queue;
    private final String place;
    private final String start;
    private final String wish;
    private final String userID;
    private final boolean ownership;
    private final boolean tenancy;

    public Ticket(String id, String queue, String place, String start, String wish ,String userID, boolean ownership, boolean tenancy ){


        this.id = id;
        this.queue = queue;
        this.place = place;
        this.start = start;
        this.wish = wish;
        this.userID = userID;
        this.ownership = ownership;
        this.tenancy = tenancy;
    }

    public String getId() {
        return id;
    }

    public String getQueue() {
        return queue;
    }

    public String getPlace() {
        return place;
    }

    public String getStart() {
        return start;
    }

    public String getUserID() {
        return userID;
    }

    public boolean isOwnership() {
        return ownership;
    }

    public boolean isTenancy() {
        return tenancy;
    }

    public String getWish() {
        return wish;
    }
}
