package com.zucc.yxr31501359.Util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "PersonalAdb.db", null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql="create table users(Uid integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2),del varchar(2),remarks varchar(2))";
        db.execSQL(sql);

        sql="create table rc(rcid integer primary key autoincrement,title varchar(50),place varchar(100),startTime data,endTime data,repeat varchar(50),remindTime Data,remarks varchar(2),status varchar(10) ,del varchar(10),uid integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub


    }



}
