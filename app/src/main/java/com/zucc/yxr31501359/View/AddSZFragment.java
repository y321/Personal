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
import android.widget.ArrayAdapter;
import com.zucc.yxr31501359.DBService.SZService;
import com.zucc.yxr31501359.DBService.Time;
import com.zucc.yxr31501359.DBService.UserService;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.entity.SZBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddSZFragment extends Fragment {

    private EditText dEditText;
    private int  hour, minute;
    private StringBuffer  time;
    /*private LinearLayout  llTime;*/
    private EditText stEditText,etEditText;
    private Button addbtn;
    private SQLiteDatabase db;
    private Spinner szET,statusET;
    private String szstr ,statustr;
    private List<String> listdata=new ArrayList<String>();


    private EditText titleET,datamET,moneyET,contentET;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listdata.add("类型");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_sz, container, false);

        time = new StringBuffer();
        datamET = (EditText) view.findViewById(R.id.starttime);
        etEditText = (EditText) view.findViewById(R.id.endtime);
        dEditText = (EditText) view.findViewById(R.id.Car);

        titleET = (EditText) view.findViewById(R.id.title);
        datamET= (EditText) view.findViewById(R.id.datam);
        szET= (Spinner) view.findViewById(R.id.sz);
        moneyET= (EditText) view.findViewById(R.id.money);
        statusET= (Spinner) view.findViewById(R.id.status);
        contentET= (EditText) view.findViewById(R.id.content);
        addbtn= (Button) view.findViewById(R.id.addbtn);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(MainActivity.context, R.array.sz, android.R.layout.simple_spinner_item);
        //设置spinner中每个条目的样式，同样是引用android提供的布局文件
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        szET.setAdapter(adapter);
        szET.setPrompt("收支");
        szET.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                szstr=parent.getItemAtPosition(position).toString();
                listdata=getData(szstr);



                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                        MainActivity.context, android.R.layout.simple_spinner_item,
                        listdata);
                // 把定义好的Adapter设定到spinner中
                statusET.setAdapter(adapter1);
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









        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SZBean szBean = new SZBean();
                szBean.setTitle(titleET.getText().toString());
                szBean.setDatam(datamET.getText().toString());
                szBean.setSz(szstr);
                szBean.setMoney(Double.parseDouble(moneyET.getText().toString()));
                szBean.setStatus(statustr);

                szBean.setRemarks(contentET.getText().toString());
                szBean.setUid(UserService.users_login);
                SZService szService = new SZService(MainActivity.db);
                szService.addSZ(szBean);
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)  //将当前fragment加入到返回栈中
                        .replace(R.id.content, new SZ_home()).commit();

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
                AddSZFragment.this.datamET.setText(year + "-" + Time.toAddZero(monthOfYear+1));
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
