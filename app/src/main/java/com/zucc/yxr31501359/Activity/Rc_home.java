package com.zucc.yxr31501359.Activity;

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
import com.zucc.yxr31501359.DBService.RcService;
import com.zucc.yxr31501359.DBService.UserService;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.entity.RcBean;

import java.text.SimpleDateFormat;
import java.util.*;




public class Rc_home extends Fragment  {
    public static Calendar calendar =Calendar.getInstance();
    private EditText data;
    private CalendarView cv;
    private List<RcBean> rcBeans = new ArrayList<RcBean>();
    public  String cd;
    private android.support.design.widget.FloatingActionButton addbtn;

    private RCAdapter rcAdapter;
    private ListView listView ;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRc();
        View view = inflater.inflate(R.layout.activity_rc, container, false);
        rcAdapter = new RCAdapter(MainActivity.context, R.layout.rc_item_frag, rcBeans);
        listView = (ListView)view.findViewById(R.id.rc_list);
        addbtn=(android.support.design.widget.FloatingActionButton) view.findViewById(R.id.add);


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
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("cd",cd);
                AddRcFragment addRcFragment = new AddRcFragment();
                addRcFragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.content,addRcFragment);
                transaction.hide(Rc_home.this);
                transaction.commit();
            }});

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RcBean rcBean=(RcBean)rcBeans.get(position);

                Bundle bundle = new Bundle();

                bundle.putSerializable("rcBean",rcBean);

                RcxqFragment rcxqFragment = new RcxqFragment();
                rcxqFragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                transaction.addToBackStack(null);
                transaction.add(R.id.content,rcxqFragment);
                transaction.hide(Rc_home.this);
                transaction.commit();

            }
        });


        return view;
    }

    private void initRc() {
        RcService rcService = new RcService(MainActivity.db);
        rcBeans=rcService.AllRc(UserService.users_login);


    }
    public  String longToDate(long lo){
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(date);
    }





}

