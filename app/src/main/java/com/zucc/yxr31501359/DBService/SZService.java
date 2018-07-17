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
    public String  DeleteSz(int szid){
        String sql="update sz set del='1' where szid = ?";
        Object obj[]={szid};
        sdb.execSQL(sql, obj);
        return "删除成功";
    }
    /*删除收支*/
    public String  updateSz(SZBean szBean){
        String sql="update sz set title=? ,datam=? ,sz=? , money=? ,remarks=? ,status=? where szid = ?";
        Object obj[]={szBean.getTitle(),szBean.getDatam(),szBean.getSz(),szBean.getMoney(),szBean.getRemarks(),szBean.getStatus(),szBean.getSzid()};
        sdb.execSQL(sql, obj);
        return "删除成功";
    }
    /*遍历收支*/
    public ArrayList<SZBean> AllSZ(int Uid ,String date){
        ArrayList<SZBean> sz = new ArrayList<>();
        String sql="select * from sz where UID=? and datam like ? and del='0'";
        Cursor c=sdb.rawQuery(sql,new String[]{String.valueOf(Uid),date+"%"});
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
    /*收入总和*/
    public String srSum(int uid,String date){
        String sql="select sum(money) from sz where UID=? and sz ='收入' and datam like ? and del='0'";
        Object obj[]={uid,date+"%"};
        String money="0.00";
        Cursor c=sdb.rawQuery(sql,new String[]{String.valueOf(uid),date+"%"});
        while (c.moveToNext()) {
            money=c.getString(0);
        }
        return money;
    }
    /*支出总和*/
    public String zcSum(int uid,String date){
        String sql="select sum(money) from sz where UID=? and sz ='支出'and datam like ? and del='0'";
        Object obj[]={uid,date+"%"};
        String money="0.00";
        Cursor c=sdb.rawQuery(sql,new String[]{String.valueOf(uid),date+"%"});
        while (c.moveToNext()) {
            money=c.getString(0);
        }
        return money;
    }
    /*本月总和*/




}
