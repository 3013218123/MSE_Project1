package entity;

/**
 * Created by wenhao on 2017/10/17.
 */

public class Signal {
    private String SG_;
    private String SignalName;
    private String seporator;
    private int startBit;
    private int dataLength;
    private String arrangeType;
    private double A;
    private double B;
    private double C;
    private double D;
    private String unit;
    private String NodeName;

    Signal(String SignalName,int startBit,int dataLength,String arrangeType,double A,double B,double C,double D,String unit,String NodeName){
        SG_="SG_";
        this.SignalName=SignalName;
        seporator=":";
        this.startBit=startBit;
        this.dataLength=dataLength;
        this.arrangeType=arrangeType;
        this.A=A;
        this.B=B;
        this.C=C;
        this.D=D;
        this.unit=unit;
        this.NodeName=NodeName;
    }

    public String getSG_() {
        return SG_;
    }

    public String getSignalName() {
        return SignalName;
    }

    public String getSeporator() {
        return seporator;
    }

    public int getStartBit() {
        return startBit;
    }

    public int getDataLength() {
        return dataLength;
    }

    public String getArrangeType() {
        return arrangeType;
    }

    public double getA() {
        return A;
    }

    public double getB() {
        return B;
    }

    public double getC() {
        return C;
    }

    public double getD() {
        return D;
    }

    public String getUnit() {
        return unit;
    }

    public String getNodeName() {
        return NodeName;
    }
}
