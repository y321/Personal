package com.zucc.yxr31501359.DBService;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.zucc.yxr31501359.entity.Users;

public class UserService {

    public Users users_login;
    private SQLiteDatabase sdb;
    public UserService(SQLiteDatabase db){
        this.sdb=db;
    }


    //登录用
    public String login(Users users){

        String sql="select * from users where username=? and password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{users.getUsername(),users.getPassword()});
        if(cursor.moveToFirst()==true){
            while (cursor.moveToNext()) {
                users_login.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
                users_login.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            }
            cursor.close();
            return "login successful";
        }
        return "login fail";
    }
    //注册用
    public String register(Users users){
        String sql="select * from users where username=? ";
        Cursor c=sdb.rawQuery(sql, new String[]{users.getUsername()});
        if(c.getCount()!=0){
            return "用户名已存在！";
        }


         sql="insert into users(username,password,del) values(?,?,?)";
        Object obj[]={users.getUsername(),users.getPassword(),"0"};
        sdb.execSQL(sql, obj);

         /*sql="select * from users where username=? ";
        Cursor c=sdb.rawQuery(sql, new String[]{"e"});
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex("username"));
            String b=null;
            Log.v(b,  name);
        }
        c.close();*/

//        String tag=null;
//        Log.v(tag,"aaaa");
        return "register successful！";
    }

}
