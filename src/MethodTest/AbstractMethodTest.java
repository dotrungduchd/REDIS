/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MethodTest;

import Entity.AnyObject;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Dictionary;
import java.util.logging.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.redisson.Config;
import org.redisson.Redisson;

/**
 *
 * @author dotrungduchd
 */
public abstract class AbstractMethodTest {
    private static final String SEPARATOR = "\t";
    // Input
    private boolean isCluster = true;
    private String strAddress = "";
    private int numThread = 1;
    private long numRequest = 1000;
    private int sizeItem = 1024;
    private AnyObject anyObject = null; 
    // Output
    private double requestPerSecond = 0;
    private double responseAverage = 0;
    private long totalTime = 0;
    private long totalRequest = 0;
    // Instance
    public Redisson redisson = null;
    private static Logger logger = null;

    //<editor-fold defaultstate="collapsed" desc="encapsulated get/set field">
    
    /**
     * @return the isCluster
     */
    public boolean isIsCluster() {
        return isCluster;
    }

    /**
     * @param isCluster the isCluster to set
     */
    public void setIsCluster(boolean isCluster) {
        this.isCluster = isCluster;
    }
    
    /**
     * @return the strAddress
     */
    public String getStrAddress() {
        return strAddress;
    }

    /**
     * @param strAddress the strAddress to set
     */
    public void setStrAddress(String strAddress) {
        this.strAddress = strAddress;
    }

    
    /**
     * @return the numThread
     */
    public int getNumThread() {
        return numThread;
    }

    /**
     * @param numThread the numThread to set
     */
    public void setNumThread(int numThread) {
        this.numThread = numThread;
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
    
    /**
     * @return the sizeItem
     */
    public int getSizeItem() {
        return sizeItem;
    }

    /**
     * @param sizeItem the sizeItem to set
     */
    public void setSizeItem(int sizeItem) {
        this.sizeItem = sizeItem;
    }        
    
    /**
     * @return the anyObject
     */
    public AnyObject getAnyObject() {
        return anyObject;
    }

    /**
     * @param anyObject the anyObject to set
     */
    public void setAnyObject(AnyObject anyObject) {
        this.anyObject = anyObject;
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
     * @return the totalRequest
     */
    public long getTotalRequest() {
        return totalRequest;
    }

    /**
     * @param totalRequest the totalRequest to set
     */
    public void setTotalRequest(long totalRequest) {
        this.totalRequest = totalRequest;
    }
    
    /**
     * @return the requestPerSecond
     */
    public double getRequestPerSecond() {
        return requestPerSecond;
    }

    /**
     * @param requestPerSecond the requestPerSecond to set
     */
    public void setRequestPerSecond(double requestPerSecond) {
        this.requestPerSecond = requestPerSecond;
    }

    /**
     * @return the responseAverage
     */
    public double getResponseAverage() {
        return responseAverage;
    }

    /**
     * @param responseAverage the responseAverage to set
     */
    public void setResponseAverage(double responseAverage) {
        this.responseAverage = responseAverage;
    }
    
    
    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * @param aLogger the logger to set
     */
    public static void setLogger(Logger aLogger) {
        logger = aLogger;
    }
//</editor-fold>
    
    /**
     * Contructor with parameter
     * @param isCluster is mode cluster or single
     * @param strAddress is address cluster
     * @param numThread is number Thread run asynchronize
     * @param numRequest is number request executed in a Thread
     * @param sizeItem is size an Item by Byte
     */
    public AbstractMethodTest(boolean isCluster, String strAddress, int numThread, long numRequest, int sizeItem){
        this.isCluster = isCluster;
        this.strAddress = strAddress;
        this.numThread = numThread;
        this.numRequest = numRequest;
        this.sizeItem = sizeItem;
    }
    
    /**
     * Config Redis Cluster with string Address
     */
    public void ConfigRedis(){
        String[] a = strAddress.split(" ");
        Config config = new Config();
        if(isCluster)
             // sets cluster state scan interval
            config.useClusterServers().setScanInterval(2000);
        else
            config.useSingleServer();
        config.useClusterServers().setMasterConnectionPoolSize(512);
        System.out.println(config.useClusterServers().getMasterConnectionPoolSize());
        
        for (String address : a) {
            config.useClusterServers().addNodeAddress(address);
        }
        
        redisson = Redisson.create(config);
        //redisson.flushdb(); 
    }
    
    /**
     * Config Log4j to write log
     * @param log is logger name
     * @param className is name of a class to set logger name
     * @param pathFileConfig is path to file Log4j Config
     */
    private void ConfigLog4j(String loggerName, Class className, String pathFileConfig){
        if(loggerName != null)
            setLogger(LogManager.getLogger(loggerName));
        else
            setLogger(LogManager.getLogger(className));
        
        PropertyConfigurator.configure(pathFileConfig);
    }
    
    /**
     * Create data to execute with sizeData
     */
    private void CreateDataItem(){
        setAnyObject(new AnyObject(sizeItem));
    }
    
    /**
     * Create array numThread Thread and execute method test
     */
    private void CreateThreadAndRun(){
        // Create numThread Thread
        RedisThread[] threads = new RedisThread[numThread];
        boolean[] threadsFinish = new boolean[numThread];
        for(int j = 0; j < numThread; j++){        
            threadsFinish[j] = false;
            threads[j] = new RedisThread(){
                @Override
                public void run(){                    
                    for(int i = 0; i < numRequest; i++){                        
                        // Execute method i-th
                        long duration = ExecMethod(i);     
                        addTotalTime(duration);                        
                        increamentRequest();
                    }                    
                };
            };
        } 
        
        // Start all thread
        for (int l = 0; l < numThread; l++)
            threads[l].start();
        
        // Check all thread finish
        int numThreadFinish = 0;        
        boolean isFinish = false;
        while (!isFinish){
            
            for (int k = 0; k < numThread; k++){
                if(!threadsFinish[k] && threads[k].getNumRequest() >= numRequest){
                    threadsFinish[k] = true;
                    setTotalTime(getTotalTime() + threads[k].getTotalTime());
                    setTotalRequest(getTotalRequest() + threads[k].getNumRequest());
                    numThreadFinish++;
                }
            }
            
            System.out.println(getTotalTime() + " " + getNumRequest());
            System.out.println(numThreadFinish + " Thread Done" + "");
            if (numThreadFinish >= numThread)
                isFinish = true;
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(AbstractMethodTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
       
    /**
     * Executed Method. It'll be override
     * @param index is index of item
     * @return duration method execute
     */
    public abstract long ExecMethod(long index);
    
    /**
     * Calculate result: request per second and response average
     */
    private void CalcResult(){
        // request/second 
        requestPerSecond = (double)getTotalRequest() / ((double)getTotalTime() / 1000000000);
        
        // response (miliseconds)
        responseAverage = ((double)getTotalTime() / 1000000) / (double)getTotalRequest();
    }
    
    /**
     * Write log to file with format: numThread numRequest sizeItem requestPerSecond
     *  ResponseAverage
     */
    private void WriteLog(){
        getLogger().info(SEPARATOR + numThread + SEPARATOR + numRequest + SEPARATOR
                + sizeItem + SEPARATOR + requestPerSecond + SEPARATOR + responseAverage);
        System.out.println(getTotalTime() + " " + getTotalRequest());
    }
    
    /**
     * Testing
     *
     */
    public void Test(){
        
        // Config Redis Cluster
        ConfigRedis();
        
        // Config Log4j
        ConfigLog4j(null, AbstractMethodError.class, "src/log.properties");
                
        // Create Data Item By Size
        CreateDataItem();
        
        // Create Thread()
        CreateThreadAndRun();
        
        // Calc Time
        CalcResult();
        
        // Write Log
        WriteLog();
        
    }
    
    /**
     * Initialization data and Config
     * @param strAddress is address Redis server
     * @param strLog4jConfig is path to file Config
     */
    public void Init(String strAddress, String strLog4jConfig){
        this.strAddress = strAddress;
        
        // Config Redis Cluster
        ConfigRedis();
        
        // Config Log4j
        ConfigLog4j(null, AbstractMethodError.class, strLog4jConfig);
                
    }
    
    /**
     * Update attribute
     * @param strAddress is address of cluster
     * @param numThread is number of Thread
     * @param numRequest is number method of a Thread
     * @param sizeItem is size of an Item
     */
    public void Update(int numThread, int numRequest, int sizeItem){
        this.numThread = numThread;
        this.numRequest = numRequest;
        this.sizeItem = sizeItem;
    }
    
    /**
     * Run test first time or after update
     * Create data, create thread, run, calculate time, write log
     */
    public void Run(){
        // Create Data Item By Size
        CreateDataItem();
        
        // Create Thread()
        CreateThreadAndRun();
        
        // Calc Time
        CalcResult();
        
        // Write Log
        WriteLog();
    }
    
    /**
     * main function
     * @param args 
     */
    public static void main(String[] args){        
        
    }



    
}
