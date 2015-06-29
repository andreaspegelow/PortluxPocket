package com.portlux.portluxpocket;

/**
 * Created by Andreas Pegelow on 2015-06-18.
 */
public class Contract {
    private final String id;
    private final Contract.contractType contractType;
    private final String berthID;
    private final String userId;
    private boolean free = false;
    private boolean vacant=false;

    public Contract(String ID, contractType contractType, String berthID, String userId, boolean free, boolean vacant) {

        this.id = ID;
        this.contractType = contractType;
        this.berthID = berthID;
        this.userId = userId;
        this.free = free;
        this.vacant = vacant;
    }

    public Contract(String ID, contractType contractType, String berthID, String userId) {
        this.id = ID;
        this.contractType = contractType;
        this.berthID = berthID;
        this.userId = userId;
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

    public enum contractType {OWNERSHIP, TENANCY}

    ;


}
