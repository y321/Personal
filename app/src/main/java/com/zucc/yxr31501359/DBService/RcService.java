package com.zucc.yxr31501359.DBService;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.zucc.yxr31501359.entity.RcBean;
import com.zucc.yxr31501359.entity.Users;

import java.util.ArrayList;

public class RcService {

    private SQLiteDatabase sdb;
    public RcService(SQLiteDatabase db){
        this.sdb=db;
    }

    /*添加日程*/
    public String  addRc(RcBean rcBean){
        String sql="insert into rc(title,place,rcdata,startTime,endTime,repeat,remindTime,remarks,status,del,uid) values(?,?,?,?,?,?,?,?,?,?,?)";
        Object obj[]={rcBean.getTitle(),rcBean.getPlace(),rcBean.getRcdata(),rcBean.getStartTime(),rcBean.getEndTime(),rcBean.getRepeat(),rcBean.getRemindTime(),rcBean.getRemarks(),rcBean.getStatus(),"0",rcBean.getUid()};
        sdb.execSQL(sql, obj);
        return "添加成功";
    }
    /*删除日程*/
    public String  DeleteRc(RcBean rcBean){
        String sql="";
        Object obj[]={};
        sdb.execSQL(sql, obj);
        return "添加成功";
    }
    /*遍历日程*/
    public ArrayList<RcBean> AllRc(int Uid){
        ArrayList<RcBean> rc = new ArrayList<>();
        ArrayList<Integer> a = new ArrayList<>();

        String sql="select * from rc where UID=? ";
        Cursor c=sdb.rawQuery(sql,new String[]{String.valueOf(Uid)});
        int i = 0;
        while (c.moveToNext()) {
            RcBean rcBean = new RcBean();
            rcBean.setRcid(c.getInt(c.getColumnIndex("rcid")));
            rcBean.setTitle(c.getString(c.getColumnIndex("title")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("startTime")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("endTime")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("repeat")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("remindTime")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("remarks")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("status")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("del")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("uid")));
            rc.add(i,rcBean);
            a.add(i,i);

            /*Log.d("aa", rc.get(i).getTitle()+" "+a.get(i));*/
            i++;
        }

        c.close();
        return rc;
    }
    /*根据时间查询*/
    public ArrayList<RcBean> AllRcByST(int Uid,String startTime){
        ArrayList<RcBean> rc = new ArrayList<>();
        String sql="select * from rc where UID=? and startTime between '? 00:00:00' and '?' 23:59:59";
        Cursor c=sdb.rawQuery(sql,new String[]{String.valueOf(Uid)});
        while (c.moveToNext()) {
            RcBean rcBean = new RcBean();
            rcBean.setRcid(c.getInt(c.getColumnIndex("rcid")));
            rcBean.setTitle(c.getString(c.getColumnIndex("title")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("startTime")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("endTime")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("repeat")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("remindTime")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("remarks")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("status")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("del")));
            rcBean.setStartTime(c.getString(c.getColumnIndex("uid")));
            rc.add(rcBean);
        }
        c.close();
        return rc;
    }

    /*修改日程*/

}
