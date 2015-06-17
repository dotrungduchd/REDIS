/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MethodTest;

/**
 *
 * @author dotrungduchd
 */
public class RedisThread extends Thread{
    private long totalTime = 0;
    private long numRequest = 0;

    public void increamentRequest(){
        numRequest++;
    }
    
    
    public void addTotalTime(long duration){
        totalTime += duration;
    }
    
    
    /**
     * @return the totalTime
     */
    public long getTotalTime() {
        return totalTime;
    }

    /**
     * @param totalTime the totalTime to set
     */
    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * @return the numRequest
     */
    public long getNumRequest() {
        return numRequest;
    }

    /**
     * @param numRequest the numRequest to set
     */
    public void setNumRequest(long numRequest) {
        this.numRequest = numRequest;
    }
}
