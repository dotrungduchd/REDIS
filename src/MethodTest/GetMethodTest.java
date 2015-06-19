/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MethodTest;

import Entity.AnyObject;
import org.redisson.core.RBucket;

/**
 *
 * @author dotrungduchd
 */
public class GetMethodTest extends AbstractMethodTest{
     public GetMethodTest(){
         nameMethod = "Get";
     }
    
    public GetMethodTest(boolean isCluster, String strAddress, int numThread, long numItem) {
        super(isCluster, strAddress, numThread, numItem, 0);
        nameMethod = "Get";
    }
    
    @Override
    public long ExecMethod(long index) {
        RBucket<AnyObject> bucket = null;
        bucket = redisson.getBucket("key" + index);
        
        // getAnyObject().setId(index);
        
         // Start time
        long start = System.nanoTime();        

        AnyObject any = bucket.get();

        // End time
        long end = System.nanoTime();

        // Time execute method
        return end - start;
        
    }   
}
