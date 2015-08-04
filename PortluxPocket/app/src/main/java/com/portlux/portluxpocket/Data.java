package com.portlux.portluxpocket;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Pegelow on 2015-08-02.
 * <p/>
 * Singelton class
 */
public final class Data {

    public static final Data INSTANCE = new Data();
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Berth> berths = new ArrayList<Berth>();
    private ArrayList<Contract> contracts = new ArrayList<Contract>();
    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();

    private Data() {

    }

    public static Data getInstance() {
        return INSTANCE;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Berth> getBerths() {
        return berths;
    }

    public void setBerths(ArrayList<Berth> berths) {
        this.berths = berths;
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(ArrayList<Contract> contracts) {
        this.contracts = contracts;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public User getUserWithId(String id) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(id)) {

                return user;
            }
        }
        return null;
    }

    public Berth getBethWithId(String id) {
        for (Berth berth : berths) {
            if (berth.getId().equalsIgnoreCase(id)) {
                return berth;
            }
        }
        return null;
    }

    public Contract getContractWithId(String id) {

        for (Contract contract : contracts) {
            if (contract.getId().equalsIgnoreCase(id)) {
                return contract;
            }
        }
        return null;
    }

    public Ticket getTicketWithId(String id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId().equalsIgnoreCase(id)) {
                return ticket;
            }
        }
        return null;
    }

    public void setUserWithId(String id, User newUser) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(id)) {
                user = newUser;
            }
        }
    }
    public void setBerthWithId(String id, Berth newBerth) {

        for (Berth berth : berths) {
            if (berth.getId().equalsIgnoreCase(id)) {
                berth = newBerth;

            }
        }
    }


}
