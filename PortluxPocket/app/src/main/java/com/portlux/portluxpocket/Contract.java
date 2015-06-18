package com.portlux.portluxpocket;

/**
 * Created by Andreas Pegelow on 2015-06-18.
 */
public class Contract {
    private final String id;
    private final Contract.contractType contractType;
    private final String berthID;
    private final String userId;
    private final boolean free;
    private final boolean vacant;

    public Contract(String ID, contractType contractType, String berthID, String userId, boolean free, boolean vacant) {


        this.id = ID;
        this.contractType = contractType;
        this.berthID = berthID;
        this.userId = userId;
        this.free = free;
        this.vacant = vacant;
    }

    public String getId() {
        return id;
    }

    public Contract.contractType getContractType() {
        return contractType;
    }

    public String getBerthID() {
        return berthID;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isFree() {
        return free;
    }

    public boolean isVacant() {
        return vacant;
    }

    public enum contractType {ACCESSRIGHT, TENANCY}

    ;


}
