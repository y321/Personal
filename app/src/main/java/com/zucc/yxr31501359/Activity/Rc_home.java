package com.zucc.yxr31501359.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.zucc.yxr31501359.Adapter.RCAdapter;
import com.zucc.yxr31501359.Adapter.SZAdapter;
import com.zucc.yxr31501359.DBService.RcService;
import com.zucc.yxr31501359.DBService.Time;
import com.zucc.yxr31501359.DBService.UserService;
import com.zucc.yxr31501359.ICallback.ICallBack;
import com.zucc.yxr31501359.ICallback.Idata;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.Adapter.ContactsInfoAdapter;
import com.zucc.yxr31501359.entity.RcBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by 45773 on 2017-07-07.
 */

public class Rc_home extends Fragment  {
    public static Calendar calendar =Calendar.getInstance();
    private EditText data;
    private CalendarView cv;
    private List<RcBean> rcBeans = new ArrayList<RcBean>();
    public  String cd;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRc();
        View view = inflater.inflate(R.layout.activity_rc, container, false);
        RCAdapter rcAdapter = new RCAdapter(MainActivity.context, R.layout.rc_item_frag, rcBeans);
        ListView listView = (ListView)view.findViewById(R.id.rc_list);

        Calendar c = Calendar.getInstance();
        cd=c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONDAY)+1)+"月"+c.get(Calendar.DAY_OF_MONTH)+"日";

        listView.setAdapter(rcAdapter);
        cv = (CalendarView) view.findViewById(R.id.data);
        cv.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {

            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                cd=year+"年"+(month+1)+"月"+dayOfMonth+"日";

            }//met
        });

        Log.v("aaa",cd);
        Bundle bundle = new Bundle();
        bundle.putString("cd",cd);
        AddRcFragment addRcFragment = new AddRcFragment();
               /* (AddRcFragment) getActivity()
                        .getSupportFragmentManager()
                        .findFragmentByTag("addRcFragment");*/
        addRcFragment.setArguments(bundle);

       /* getFragmentManager().beginTransaction()
                .replace(R.id.container, addRcFragment).addToBackStack(null).commit();*/
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.content,addRcFragment);
        transaction.hide(Rc_home.this);
        transaction.commit();

        return view;
    }

    private void initRc() {
        RcService rcService = new RcService(MainActivity.db);
        rcBeans=rcService.AllRc(UserService.users_login);
        Log.v("nnn",rcBeans.get(0).getTitle());
    }
    public  String longToDate(long lo){
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(date);
    }


}

