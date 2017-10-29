package com.example.administrator.canol.convert;

import com.example.administrator.canol.entity.Message;
import com.example.administrator.canol.entity.Signal;

import java.util.ArrayList;

/**
 * Created by wenhao on 2017/10/29.
 */

public class BEAN {
    private String boString;
    private ArrayList<String> sgStringArraylist;
    public BEAN(String boString,ArrayList<String> sgStringArraylist){
        this.boString=boString;
        this.sgStringArraylist=sgStringArraylist;
    }

    public void setBoString(String boString) {
        this.boString = boString;
    }

    public void setSgStringArraylist(ArrayList<String> sgStringArraylist) {
        this.sgStringArraylist = sgStringArraylist;
    }

    public String getBoString() {
        return boString;
    }

    public ArrayList<String> getSgStringArraylist() {
        return sgStringArraylist;
    }
}
