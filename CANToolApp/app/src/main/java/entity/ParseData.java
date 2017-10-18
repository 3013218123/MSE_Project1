package entity;

import java.util.Set;

/**
 * Created by wenhao on 2017/10/18.
 */

public class ParseData {
    private Message BO_Mse;
    private Set<Signal> Signals;
    private double [] phyArray;
    public ParseData(Message BO_Mse,Set<Signal>Signals,double[] phyArray){
        this.BO_Mse=BO_Mse;
        this.Signals=Signals;
        this.phyArray=phyArray;
    }

    public Message getBO_Mse() {
        return BO_Mse;
    }

    public Set<Signal> getSignals() {
        return Signals;
    }

    public double[] getPhyArray() {
        return phyArray;
    }
}


