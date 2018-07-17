package com.zucc.yxr31501359.Activity;



import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.zucc.yxr31501359.DBService.SZService;
import com.zucc.yxr31501359.DBService.Time;
import com.zucc.yxr31501359.DBService.UserService;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.Adapter.SZAdapter;
import com.zucc.yxr31501359.entity.SZBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SZ_home extends Fragment {

    private List<SZBean> szBeans = new ArrayList<SZBean>();
    private List<SZBean> AllDataBeans = new ArrayList<SZBean>();

    private android.support.design.widget.FloatingActionButton addbtn;
    private TextView sr,zc,Data_m;
    private SZAdapter szAdapter;
    private ListView listView;
    private  ImageButton search;
    private String datestr="";
    private SZService szService = new SZService(MainActivity.db);



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initSZ();
        View view = inflater.inflate(R.layout.fragment_sz, container, false);
        szAdapter = new SZAdapter(MainActivity.context, R.layout.sz_item_frag, szBeans);
        listView = (ListView)view.findViewById(R.id.sz_list);
        listView.setAdapter(szAdapter);

        search = (ImageButton) view.findViewById(R.id.search);

        sr = (TextView) view.findViewById(R.id.Income_m);
        zc = (TextView) view.findViewById(R.id.Ouput_m);
        Data_m= (TextView) view.findViewById(R.id.Data_m);
        sr.setText(szService.srSum(UserService.users_login,datestr));
        zc.setText(szService.zcSum(UserService.users_login,datestr));

        addbtn=(android.support.design.widget.FloatingActionButton) view.findViewById(R.id.add);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)  //将当前fragment加入到返回栈中
                        .replace(R.id.content, new AddSZFragment()).commit();


            }});


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSZ();
                szAdapter = new SZAdapter(MainActivity.context, R.layout.sz_item_frag, szBeans);
                listView.setAdapter(szAdapter);
                sr.setText(szService.srSum(UserService.users_login,datestr));
                zc.setText(szService.zcSum(UserService.users_login,datestr));


            }});

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SZBean szBean=(SZBean)szBeans.get(position);

                Bundle bundle = new Bundle();

                bundle.putSerializable("szBean",szBean);

                SzxqFragment szxqFragment = new SzxqFragment();
                szxqFragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                transaction.addToBackStack(null);
                transaction.add(R.id.content,szxqFragment);
                transaction.hide(SZ_home.this);
                transaction.commit();

            }
        });

        Data_m.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }

                return false;
            }
        });

        Data_m.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickDlg();
                }

            }
        });


        return view;
    }
    private void initSZ() {
        szBeans.clear();
        SZService szService = new SZService(MainActivity.db);
        szBeans=szService.AllSZ(UserService.users_login,datestr);
    }
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SZ_home.this.Data_m.setText(year + "-" + Time.toAddZero(monthOfYear+1) );
                datestr=year + "-" + Time.toAddZero(monthOfYear+1);

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }

}








