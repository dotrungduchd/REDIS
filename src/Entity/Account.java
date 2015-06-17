/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.count;

/**
 *
 * @author dotrungduchd
 */
public class Account{
    private String accountID;
    private String accountName;
    private double tInCash;
    private double iOutCash;
    private double cash;
    private String cashID;
    private String walletSignature;
    private boolean isSuspended;

    private static long count = 0;
    
    public Account(String accountName)
    {
        this.accountID = String.valueOf(count);
        this.accountName = accountName;
        this.tInCash = 100000;
        this.iOutCash = 100000;
        this.cash = 100000;
        this.cashID = "cash" + count;
        this.walletSignature = "available";
        this.isSuspended = false;
    }
    
    /**
     * @return the accountID
     */
    public String getAccountID() {
        return accountID;
    }

    /**
     * @param accountID the accountID to set
     */
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * @return the tInCash
     */
    public double gettInCash() {
        return tInCash;
    }

    /**
     * @param tInCash the tInCash to set
     */
    public void settInCash(double tInCash) {
        this.tInCash = tInCash;
    }

    /**
     * @return the iOutCash
     */
    public double getiOutCash() {
        return iOutCash;
    }

    /**
     * @param iOutCash the iOutCash to set
     */
    public void setiOutCash(double iOutCash) {
        this.iOutCash = iOutCash;
    }

    /**
     * @return the cash
     */
    public double getCash() {
        return cash;
    }

    /**
     * @param cash the cash to set
     */
    public void setCash(double cash) {
        this.cash = cash;
    }

    /**
     * @return the cashID
     */
    public String getCashID() {
        return cashID;
    }

    /**
     * @param cashID the cashID to set
     */
    public void setCashID(String cashID) {
        this.cashID = cashID;
    }

    /**
     * @return the walletSignature
     */
    public String getWalletSignature() {
        return walletSignature;
    }

    /**
     * @param walletSignature the walletSignature to set
     */
    public void setWalletSignature(String walletSignature) {
        this.walletSignature = walletSignature;
    }

    /**
     * @return the isSuspended
     */
    public boolean isIsSuspended() {
        return isSuspended;
    }

    /**
     * @param isSuspended the isSuspended to set
     */
    public void setIsSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }
    
    
    
    
    
}
