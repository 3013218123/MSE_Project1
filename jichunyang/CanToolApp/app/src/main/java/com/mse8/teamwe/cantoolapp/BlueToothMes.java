package com.mse8.teamwe.cantoolapp;

/**
 * Created by JIChunYang on 2017/10/17.
 */

public class BlueToothMes {

    protected String _message;

    protected boolean _isSucceed;

    public BlueToothMes(String message, boolean isSucceed) {
        if (message == null || message == "")
            return;

        this._message = message;
        this._isSucceed = isSucceed;
    }

}
