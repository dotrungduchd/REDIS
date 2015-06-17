/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;

/**
 *
 * @author dotrungduchd
 */
public class User{
    private String username;
    
    private Account account;
    
    private double timeToLive;
    
    private boolean evicable;

    public User(String username, String accountName){
        this.username = username;
        this.timeToLive = 24*60*60;
        this.evicable = true;
        this.account = new Account(accountName);
        
    }
    
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the timeToLive
     */
    public double getTimeToLive() {
        return timeToLive;
    }

    /**
     * @param timeToLive the timeToLive to set
     */
    public void setTimeToLive(double timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * @return the evicable
     */
    public boolean isEvicable() {
        return evicable;
    }

    /**
     * @param evicable the evicable to set
     */
    public void setEvicable(boolean evicable) {
        this.evicable = evicable;
    }
}
