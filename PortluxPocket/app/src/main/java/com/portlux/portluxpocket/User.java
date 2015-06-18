package com.portlux.portluxpocket;

/**
 * Created by Andreas Pegelow on 2015-06-18.
 */
public class User {

    private final String id;
    private final String name;
    private final String phoneNumber;
    private final boolean member;
    private final String email;
    private final String city;
    private final String personalIdentityNumber;
    private final String cellphoneNumber;
    private final Contract[] contracts;


    public User(String ID, String name, String phoneNumber, boolean member, String email, String city,  String personalIdentityNumber, String cellphoneNumber, Contract[] contracts) {
        this.id = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.member = member;
        this.email = email;
        this.city = city;
        this.personalIdentityNumber = personalIdentityNumber;
        this.cellphoneNumber = cellphoneNumber;
        this.contracts = contracts;
    }

    public Contract[] getContracts() {
        return contracts;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public String getPersonalIdentityNumber() {
        return personalIdentityNumber;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public boolean isMember() {
        return member;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
