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
public class MethodTestFactory {
    public static AbstractMethodTest createMethodTest(String nameMethod, boolean isCluster, 
            String strAddress, int numThread, long numItem, int sizeItem){
        AbstractMethodTest method = null;
        switch(nameMethod){
            case "Set":
                method = new SetMethodTest(isCluster, strAddress, numThread,
                        numItem, sizeItem);
                break;
            case "Get":
                method = new GetMethodTest(isCluster, strAddress, numThread,
                        numItem);
                break;
            default:
                System.out.println("-------Not correct method. Exit");
                break;
        }
        return method;
    }
    
    public static AbstractMethodTest createMethodTest(String nameMethod){
        AbstractMethodTest method = null;
        switch(nameMethod){
            case "Set":
                method = new SetMethodTest();
                break;
            case "Get":
                method = new GetMethodTest();
                break;
            default:
                System.out.println("-------Not correct method. Exit");
                break;
        }
        return method;
    }
}
