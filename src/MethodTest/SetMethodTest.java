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
public class SetMethodTest extends AbstractMethodTest{

    public SetMethodTest(boolean isCluster, String strAddress, int numThread, long numItem, int sizeItem) {
        super(isCluster, strAddress, numThread, numItem, sizeItem);
    }
    
    @Override
    public long ExecMethod(long index) {
        RBucket<AnyObject> bucket = null;
        bucket = redisson.getBucket("key" + index);
        
        getAnyObject().setId(index);
        
         // Start time
        long start = System.nanoTime();
        

        bucket.set(getAnyObject());

        // End time
        long end = System.nanoTime();

        // Time execute method
        return end - start;
        
    }    
}
