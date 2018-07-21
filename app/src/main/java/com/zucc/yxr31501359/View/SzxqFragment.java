package com.zucc.yxr31501359.View;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.zucc.yxr31501359.DBService.SZService;
import com.zucc.yxr31501359.DBService.UserService;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.entity.SZBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SzxqFragment extends Fragment {

    private EditText dEditText;
    private int  hour, minute;
    private StringBuffer  time;
    /*private LinearLayout  llTime;*/
    private EditText stEditText,etEditText;
    private SQLiteDatabase db;
    private SZBean szBean;
    private Spinner szET,statusET,s;
    private String szstr ,statustr;
    private Button updatebtn,deletebtn;

    private EditText titleET,datamET,moneyET,contentET;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.szxq_fragment, container, false);

        Bundle bundle = getArguments();
        szBean= (SZBean) bundle.getSerializable("szBean");



        time = new StringBuffer();

        titleET = (EditText) view.findViewById(R.id.title);
        datamET= (EditText) view.findViewById(R.id.datam);
        szET= (Spinner) view.findViewById(R.id.sz);
        moneyET= (EditText) view.findViewById(R.id.money);
        statusET= (Spinner) view.findViewById(R.id.status);
        contentET= (EditText) view.findViewById(R.id.content);

        updatebtn = (Button) view.findViewById(R.id.updatabtn);
        deletebtn = (Button) view.findViewById(R.id.deletebtn);

        titleET.setText(szBean.getTitle());
        datamET.setText(szBean.getDatam());

        moneyET.setText(Double.toString(szBean.getMoney()));
        contentET.setText(szBean.getRemarks());

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(MainActivity.context, R.array.sz, android.R.layout.simple_spinner_item);
        //设置spinner中每个条目的样式，同样是引用android提供的布局文件
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        szET.setAdapter(adapter);
        szET.setPrompt("收支");
        szET.setSelection(adapter.getPosition(szBean.getSz()));

        szET.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                szstr=parent.getItemAtPosition(position).toString();

                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                        MainActivity.context, android.R.layout.simple_spinner_item,
                        getData(szstr));
                // 把定义好的Adapter设定到spinner中
                statusET.setAdapter(adapter1);
                statusET.setSelection(adapter1.getPosition(szBean.getStatus()));
                statusET.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {

                        statustr=parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // 这个一直没有触发，我也不知道什么时候被触发。
                        //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
                //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
            }
        });





        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SZBean szBean1 = new SZBean();
                szBean1.setTitle(titleET.getText().toString());
                szBean1.setDatam(datamET.getText().toString());
                szBean1.setSz(szstr);
                szBean1.setMoney(Double.parseDouble(moneyET.getText().toString()));
                szBean1.setStatus(statustr);

                szBean1.setRemarks(contentET.getText().toString());
                szBean1.setUid(UserService.users_login);
                szBean1.setSzid(szBean.getSzid());
                SZService szService = new SZService(MainActivity.db);
                szService.updateSz(szBean1);
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)  //将当前fragment加入到返回栈中
                        .replace(R.id.content, new SZ_home()).commit();

            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SZService szService = new SZService(MainActivity.db);
                szService.DeleteSz(szBean.getSzid());

                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)  //将当前fragment加入到返回栈中
                        .replace(R.id.content, new Rc_home()).commit();

            }
        });




        /*点击EditText显示时间日期控件*/
        datamET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }

                return false;
            }
        });



        datamET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickDlg();
                }

            }
        });

        return view;
    }


    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SzxqFragment.this.datamET.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }
    private List<String> getData(String szstr) {
        // 数据源
        Log.v("sr",szstr);
        List<String> dataList = new ArrayList<String>();
        if(szstr.equals("收入")){
            dataList.clear();
            dataList.add("工资");
            dataList.add("投资");
            dataList.add("奖金");
            dataList.add("兼职");
            dataList.add("红包");
            dataList.add("其他");
        }
        else {
            dataList.clear();
            dataList.add("吃喝");
            dataList.add("交通");
            dataList.add("孩子");
            dataList.add("红包");
            dataList.add("医疗");
            dataList.add("娱乐");
            dataList.add("日用品");
            dataList.add("服饰鞋包");
            dataList.add("化妆护肤");
            dataList.add("游戏设备");
            dataList.add("其他");
        }


        return dataList;
    }





}