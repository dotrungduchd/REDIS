/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redisson.performance.test;

import Entity.AnyObject;
import MethodTest.AbstractMethodTest;
import MethodTest.SetMethodTest;
import java.io.UnsupportedEncodingException;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RBucket;
import org.redisson.core.RSet;

/**
 *
 * @author dotrungduchd
 */
public class RedissonPerformanceTest {
    private static final int MIN_THREAD = 2;
    private static final int MIN_NUM_REQUEST = 1000;
    private static final int MIN_SIZE_ITEM = 1024;
    
    private static final int MAX_THREAD = 2;
    private static final int MAX_NUM_REQUEST = 50000;
    private static final int MAX_SIZE_ITEM = 2048;
    
    private static final int UNIT_REQUEST = 1000;
    private static final int LOOP = 5;
    
    
    private static String mode = "manual";
    private static String strAddress = "127.0.0.1:8081 127.0.0.1:8082 127.0.0.1:8083";
    private static String nameMethod;
    private static boolean isCluster;
    private static int numThread = MIN_THREAD;
    private static int numRequest = MIN_NUM_REQUEST;
    private static int sizeItem = MIN_SIZE_ITEM;
            
    /**
     * @param args the command line arguments
     */    
    public static void main(String[] args) {
        // TODO code application logic here
        
        // Test
        //new SetMethodTest(true, strAddress, 4, 1000, 1024).Test();
        //System.out.println(args[0]);
        
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
            return;
        }
        else {
            
            //<editor-fold defaultstate="collapsed" desc="Parse Arguments">
            mode = args[0];
            isCluster = args[1].equals("true") || args[1].equals("True") || args[1].equals("TRUE");
            strAddress = args[2];
            nameMethod = args[3];
            
            numThread = Integer.parseInt(args[4]);
            numRequest = Integer.parseInt(args[5]);
            sizeItem = Integer.parseInt(args[6]);
//</editor-fold>
            
            if(mode.equals("manual")){
                //<editor-fold defaultstate="collapsed" desc="MANUAL TEST">
                
                
                AbstractMethodTest method = null;
                
                switch(nameMethod){
                    case "Set":
                        method = new SetMethodTest(isCluster, strAddress, numThread, numRequest, sizeItem);
                        
                        break;
                    default:
                        System.out.println("-------Not correct method. Exit");
                        return ;
                }
                System.out.println("now");
                method.Test();
                System.out.println("end now");
//</editor-fold>
            }
            else if (mode.equals("auto")){        
                //<editor-fold defaultstate="collapsed" desc="AUTO TEST">
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
//</editor-fold>
            }
        
        }
        
    }
    
}
