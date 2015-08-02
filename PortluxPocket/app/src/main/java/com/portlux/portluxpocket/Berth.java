package com.portlux.portluxpocket;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Instant;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by Andreas Pegelow on 2015-06-18.
 */
public class Berth implements Serializable {
    private final String id;
    private final String pier;
    private final String name;
    private final String harbour;
    private final String ownershipContractId;
    private final String tenancyContractId;
    private final String ownershipUserId;
    private final String tenancyUserId;
    private final ArrayList<GuestPeriod> freePeriods = new ArrayList<GuestPeriod>();

    public Berth(String ID, String pier, String harbour, String name, String contractOwnershipID, String contractTenancyID, String userOwnershipId, String userTenancyId) {
        id = ID;
        this.pier = pier;
        this.name = name;
        this.harbour = harbour;
        this.ownershipContractId = contractOwnershipID;
        this.tenancyContractId = contractTenancyID;
        this.ownershipUserId = userOwnershipId;
        this.tenancyUserId = userTenancyId;

    }


    public String getHarbour() {
        return harbour;
    }

    /**
     * @returns the name of the berth on the pier
     */
    public String getName() {
        return name;
    }

    public String getPier() {
        return pier;
    }

    /**
     * @returns the Berth which is a concatenation of the Pier and the name
     */
    public String getBerth() {
        return pier + name;
    }

    public String getOwnershipContractId() {
        return ownershipContractId;
    }

    public String getTenancyContractId() {
        return tenancyContractId;
    }

    public String getId() {
        return id;
    }

    public String getOwnershipUserId() {
        return ownershipUserId;
    }

    public String getTenancyUserId() {
        return tenancyUserId;
    }

    public void addFreePeriod(GuestPeriod period) {
        freePeriods.add(period);
    }

    public ArrayList<GuestPeriod> getFreePeriods() {
        return freePeriods;
    }

    public boolean isFreeForGuests() {

        for (GuestPeriod period : freePeriods) {
            if (period.isNow()) {
                return true;
            }

        }
        return false;
    }
}
