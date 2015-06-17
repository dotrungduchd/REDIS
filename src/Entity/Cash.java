/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author dotrungduchd
 */
public class Cash {
    private String cashID;
    private String accountName;
    private double cashAmount;
    private double RemainCashAmount;
    private String sourceID;
    private String sourceSignature;
    private double timeToLive;
    private boolean evicable;

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
     * @return the cashAmount
     */
    public double getCashAmount() {
        return cashAmount;
    }

    /**
     * @param cashAmount the cashAmount to set
     */
    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    /**
     * @return the RemainCashAmount
     */
    public double getRemainCashAmount() {
        return RemainCashAmount;
    }

    /**
     * @param RemainCashAmount the RemainCashAmount to set
     */
    public void setRemainCashAmount(double RemainCashAmount) {
        this.RemainCashAmount = RemainCashAmount;
    }

    /**
     * @return the sourceID
     */
    public String getSourceID() {
        return sourceID;
    }

    /**
     * @param sourceID the sourceID to set
     */
    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    /**
     * @return the sourceSignature
     */
    public String getSourceSignature() {
        return sourceSignature;
    }

    /**
     * @param sourceSignature the sourceSignature to set
     */
    public void setSourceSignature(String sourceSignature) {
        this.sourceSignature = sourceSignature;
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
