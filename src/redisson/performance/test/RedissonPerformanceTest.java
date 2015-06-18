/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redisson.performance.test;

import MethodTest.AbstractMethodTest;
import MethodTest.GetMethodTest;
import MethodTest.MethodTestFactory;
import MethodTest.SetMethodTest;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author dotrungduchd
 */
public class RedissonPerformanceTest {
    //<editor-fold defaultstate="collapsed" desc="define variable">
    private static int MIN_THREAD = 2;
    private static int MIN_NUM_REQUEST = 1000;
    private static int MIN_SIZE_ITEM = 1024;
    
    private static int MAX_THREAD = 2;
    private static int MAX_NUM_REQUEST = 50000;
    private static int MAX_SIZE_ITEM = 2048;
    
    private static int UNIT_REQUEST = 1000;
    private static int LOOP = 5;
    private static String[] LIST_METHOD = null;
    
    
    private static String mode = "manual";
    private static String strAddress = "127.0.0.1:8081 127.0.0.1:8082 127.0.0.1:8083";
    private static String nameMethod;
    private static boolean isCluster;
//</editor-fold>
    
    /**
     * Load Config from file
     */      
    private static void LoadConfig(){
        
        Properties prop = new Properties();
        try {
            InputStream input = new FileInputStream("./src/config.properties");
            input = new FileInputStream("./src/config.properties");
            
            // load a properties file
            prop.load(input);
            
            // get the property value and print it out
            mode = prop.getProperty("mode");
            strAddress = prop.getProperty("strAddress");
            isCluster = prop.getProperty("isCluster").equals("true")!=true?true:false;
            nameMethod = prop.getProperty("nameMethod");
            
            MIN_THREAD = Integer.valueOf(prop.getProperty("MIN_THREAD"));
            MIN_NUM_REQUEST = Integer.valueOf(prop.getProperty("MIN_NUM_REQUEST"));
            MIN_SIZE_ITEM = Integer.valueOf(prop.getProperty("MIN_SIZE_ITEM"));
            
            MAX_THREAD = Integer.valueOf(prop.getProperty("MAX_THREAD"));
            MAX_NUM_REQUEST = Integer.valueOf(prop.getProperty("MAX_NUM_REQUEST"));
            MAX_SIZE_ITEM = Integer.valueOf(prop.getProperty("MAX_SIZE_ITEM"));
            
            UNIT_REQUEST = Integer.valueOf(prop.getProperty("UNIT_REQUEST"));
            LOOP = Integer.valueOf(prop.getProperty("LOOP"));
                    
            String listMethod = prop.getProperty("LIST_METHOD");
            LIST_METHOD = listMethod.split(",");
            int a;
        } catch (IOException ex) {
            Logger.getLogger(RedissonPerformanceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    /**
     * @param args the command line arguments
     */    
    public static void main(String[] args) {
        // TODO code application logic here
        
        // Load config from file
        LoadConfig();
        System.out.println(strAddress);        
        
        
        // Run auto with default config
        if (args.length == 1){
            if (args[0].equals("auto")){
                // Test with default Config in file
                AutoTesting();
            }
            return;
        }
        
        // Full arguments
        if (args.length != 7){
            //<editor-fold defaultstate="collapsed" desc="Print Error Arguments">
            System.out.println("------- Not correct number argument (1 or 7)");
            System.out.println("======= ARGS MODE: manual");
            System.out.println("------- Mode*: (auto/manual) isCluster*: (true/false) "
                    + "address*: (host:port)  NameMethod*: (set) "
                    + "numThread: (32/64/..) numRequest: (1 milion/..) "
                    + "sizeItem: (1024/..)");
            System.out.println("------- If all argument (not *) equal 0, run by default");
            System.out.println("======= ARGS MODE: auto");
            System.out.println("------- Mode*: (auto/manual) isCluster*: (true/false) "
                    + "address*: (host:port)  NameMethod*: (set) "
                    + "minNumThread: (32/64/..) minNumRequest: (1 milion/..) "
                    + "minSizeItem: (1024/..)");
//</editor-fold>
        }
        else {
            
            // Parse arguments
            ParseArguments(args);
            
            if(mode.equals("manual")){
                // Test with arguments 
                ManualTesting();
            }
            else if (mode.equals("auto")){ 
                // Arguments is MIN variable, run to MAX in file Config
                AutoTesting();
            }
        
        }
    }

    /**
     * Manual testing with user parameter
     */
    private static void ManualTesting() {
        AbstractMethodTest method = null;

        method = MethodTestFactory.createMethodTest(nameMethod, isCluster, strAddress, 
                MIN_THREAD, MIN_NUM_REQUEST, MIN_SIZE_ITEM);
                
        System.out.println("========= test start");
        
        if (method == null){
            System.out.println("ERROR: Can not create method");
            return;
        }
        
        method.Test();
        
        System.out.println("========= test end");
    }

    /**
     * Parse parameter from arguments
     * @param args is array string arguments
     */
    private static void ParseArguments(String[] args) {
        mode = args[0];
        isCluster = args[1].equals("true") || args[1].equals("True") || args[1].equals("TRUE");
        strAddress = args[2];
        nameMethod = args[3];

        MIN_THREAD = Integer.parseInt(args[4]);
        MIN_NUM_REQUEST = Integer.parseInt(args[5]);
        MIN_SIZE_ITEM = Integer.parseInt(args[6]);
    }

    /**
     * Auto testing with Config from file
     */
    private static void AutoTesting() {
        // Create list method and run
        AbstractMethodTest[] methods = new AbstractMethodTest[LIST_METHOD.length];
        for (int t = 0; t < LIST_METHOD.length; t++){
            System.out.println("---- Start run method " + LIST_METHOD[t]);
            
            methods[t] = MethodTestFactory.createMethodTest(LIST_METHOD[t]);
            
            methods[t].Init(strAddress, "./src/log.properties");
            
            RunMethodTest(methods[t]);
            
            System.out.println("---- End run method " + LIST_METHOD[t]);
        }
    }
    
    /**
     * Run method test instance
     * @param methodTest 
     */
    private static void RunMethodTest(AbstractMethodTest methodTest){
        double prevMaxRPS = 0;
        double curMaxRPS = 0;
        
        double prevRPSAverage = 0;
        double curRPSAverage = 0;
        
        double tempRPS = 0;
        double sumRPSAverage = 0;
        for (int i = MIN_THREAD; i <= MAX_THREAD; i*= 2){
            curMaxRPS = 0;
            sumRPSAverage = 0;
            for (int j = MIN_SIZE_ITEM ; j <=  MAX_SIZE_ITEM; j *= 2){
                for (int k = MIN_NUM_REQUEST; k <= MAX_NUM_REQUEST; k += UNIT_REQUEST){
                    for(int l = 0; l < LOOP;  l++){
                        // Reset result
                        methodTest.ResetResult();
                        
                        // Update parameter
                        methodTest.Update(i, k, j);

                        // Execute method
                        methodTest.Run();
                        
                        tempRPS = methodTest.getRequestPerSecond();
                        sumRPSAverage += tempRPS;
//                        if(tempRPS > curMaxRPS){
//                            curMaxRPS = tempRPS;
//                        }                        
                    }
                }
            }
            
            // Average
            long sumRequest = ((MAX_NUM_REQUEST - MIN_NUM_REQUEST) / UNIT_REQUEST) * 
                    (int)(Math.log((double)(MAX_SIZE_ITEM / MIN_NUM_REQUEST)) / Math.log(2.0) + 1);
            curRPSAverage = sumRPSAverage / sumRequest;
            System.out.println("===== Average of thread " + i + " ::: " + curRPSAverage);
            if (prevRPSAverage <= curRPSAverage) {
                prevRPSAverage = curRPSAverage;
            } 
            else {
                System.out.println("===== Average RequestPerSecond was not increase");
                return;
            }
            
//            // Max
//            if (prevMaxRPS <= curMaxRPS){
//                prevMaxRPS = curMaxRPS;
//            } 
//            else {
//                System.out.println("===== Max RequestPerSecond was not increase");
//                return;
//            }
        }
    }
}
