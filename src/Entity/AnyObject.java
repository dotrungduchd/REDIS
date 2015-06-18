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
public class AnyObject {
    private long id = 0;
    private byte[] datas = null;
    
    public AnyObject(){
    }
    
    public AnyObject(int size){        
        datas = new byte[size];
        for(int i = 0; i < size; i++)
            datas[i] = 68;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the datas
     */
    public byte[] getDatas() {
        return datas;
    }

    /**
     * @param datas the datas to set
     */
    public void setDatas(byte[] datas) {
        this.datas = datas;
    }
}
