package com.zucc.yxr31501359.DBService;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LCService {
    /*private SQLiteDatabase sdb;
    public lcService(SQLiteDatabase db){
        this.sdb=db;
    }*/

    public Double getLX(Double lv,Double bj,Double year){
        Double lx = lv*bj*0.01*year;

        Log.v("re",lx.toString());
        return lx;


    }
}
