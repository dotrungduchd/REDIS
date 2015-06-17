/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redisson.performance.test;

import MethodTest.AbstractMethodTest;
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
    private static int MIN_THREAD = 2;
    private static int MIN_NUM_REQUEST = 1000;
    private static int MIN_SIZE_ITEM = 1024;
    
    private static int MAX_THREAD = 2;
    private static int MAX_NUM_REQUEST = 50000;
    private static int MAX_SIZE_ITEM = 2048;
    
    private static int UNIT_REQUEST = 1000;
    private static int LOOP = 5;
    
    
    private static String mode = "manual";
    private static String strAddress = "127.0.0.1:8081 127.0.0.1:8082 127.0.0.1:8083";
    private static String nameMethod;
    private static boolean isCluster;
    
            
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

    
    private static void ManualTesting() {
        AbstractMethodTest method = null;

        switch(nameMethod){
            case "Set":
                method = new SetMethodTest(isCluster, strAddress, MIN_THREAD,
                        MIN_NUM_REQUEST, MIN_SIZE_ITEM);
                break;
            default:
                System.out.println("-------Not correct method. Exit");
                return ;
        }
        System.out.println("now");
        method.Test();
        System.out.println("end now");
    }

    private static void ParseArguments(String[] args) {
        mode = args[0];
        isCluster = args[1].equals("true") || args[1].equals("True") || args[1].equals("TRUE");
        strAddress = args[2];
        nameMethod = args[3];

        MIN_THREAD = Integer.parseInt(args[4]);
        MIN_NUM_REQUEST = Integer.parseInt(args[5]);
        MIN_SIZE_ITEM = Integer.parseInt(args[6]);
    }

    private static void AutoTesting() {
        // Init config
        AbstractMethodTest methodTest = new SetMethodTest(true, strAddress, 1, 1000, 1024);
        methodTest.Init(strAddress, "./src/log.properties");

        //            double prevRequestPerSecond = 0;
        //            double prevResponeAverage = 0;
        //
        //            double curRequestPerSecond = 0;
        //            double curResponeAverage = 0;

        for (int i = MIN_THREAD; i <= MAX_THREAD; i*= 2){
            for (int j = MIN_SIZE_ITEM ; j <=  MAX_SIZE_ITEM; j *= 2){
                for (int k = MIN_NUM_REQUEST; k <= MAX_NUM_REQUEST; k +=UNIT_REQUEST){
                    for(int l = 0; l < LOOP;  l++){
                        // Update parameter
                        methodTest.Update(i, k, j);

                        // Execute method
                        methodTest.Run();
                    }
                }
            }

            //                double requestPerSecond = methodTest.getRequestPerSecond();
            //                double responseAverage = methodTest.getResponseAverage();
        }
    }
    
}
