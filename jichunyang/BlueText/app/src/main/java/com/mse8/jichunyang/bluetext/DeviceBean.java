package com.mse8.jichunyang.bluetext;

public class DeviceBean {
    protected String message;
    protected boolean isReceive;

    public DeviceBean(String msg, boolean isReceive) {
        this.message = msg;
        this.isReceive = isReceive;
    }
}
