package entity;

import java.util.ArrayList;
import java.util.Set;

import parse.Parse;

/**
 * Created by wenhao on 2017/10/18.
 */

public class ParseData {
    private Message BO_Mse;
    private ArrayList<Signal> Signals;
    private double [] phyArray;
    private boolean isRight=true;



    public ParseData(){
        this.isRight=false;

    }

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
    public boolean getSsRight() {
        return isRight;
    }
}


