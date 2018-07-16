package com.zucc.yxr31501359.DBService;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.zucc.yxr31501359.entity.RcBean;
import com.zucc.yxr31501359.entity.SZBean;
import com.zucc.yxr31501359.entity.Users;
import java.util.ArrayList;

public class SZService {

    private SQLiteDatabase sdb;
    public SZService(SQLiteDatabase db){
        this.sdb=db;
    }

    /*添加SZ*/
    public String  addSZ(SZBean szBean){
        String sql="insert into sz(title ,datam ,sz , money ,remarks ,status  ,del,uid ) values(?,?,?,?,?,?,?,?)";
        Object obj[]={szBean.getTitle(),szBean.getDatam(),szBean.getSz(),szBean.getMoney(),szBean.getRemarks(),szBean.getStatus(),"0",szBean.getUid()};
        sdb.execSQL(sql, obj);
        return "添加成功";
    }
    /*删除收支*/
    public String  DeleteRc(RcBean rcBean){
        String sql="";
        Object obj[]={};
        sdb.execSQL(sql, obj);
        return "删除成功";
    }
    /*遍历收支*/
    public ArrayList<SZBean> AllRc(int Uid){
        ArrayList<SZBean> sz = new ArrayList<>();
        String sql="select * from sz where UID=? and del='0'";
        Cursor c=sdb.rawQuery(sql,new String[]{String.valueOf(Uid)});
        int i = 0;
        while (c.moveToNext()) {
            Log.v("hahaha",c.getString(c.getColumnIndex("title")));
            SZBean szBean = new SZBean();
            szBean.setSzid(c.getInt(c.getColumnIndex("szid")));
            szBean.setTitle(c.getString(c.getColumnIndex("title")));
            szBean.setDatam(c.getString(c.getColumnIndex("datam")));
            szBean.setSz(c.getString(c.getColumnIndex("sz")));
            szBean.setMoney(c.getDouble(c.getColumnIndex("money")));
            szBean.setRemarks(c.getString(c.getColumnIndex("remarks")));
            szBean.setStatus(c.getString(c.getColumnIndex("status")));
            sz.add(i,szBean);
            i++;
        }

        c.close();
        return sz;
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
