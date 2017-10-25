package com.example.administrator.canol.entity;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by wenhao on 2017/10/18.
 */

public class ParseData {
    private Message BO_Mse;
    private ArrayList<Signal> Signals;
    //解析出来的物理值数组
    private double [] phyArray;
    public ParseData(Message BO_Mse,ArrayList<Signal>Signals,double[] phyArray){//构造函数
        this.BO_Mse=BO_Mse;
        this.Signals=Signals;
        this.phyArray=phyArray;
    }

    public Message getBO_Mse() {
        return BO_Mse;
    }

    public ArrayList<Signal> getSignals() {
        return Signals;
    }

    public double[] getPhyArray() {
        return phyArray;
    }
}


