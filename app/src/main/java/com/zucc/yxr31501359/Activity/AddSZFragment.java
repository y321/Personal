package com.zucc.yxr31501359.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import com.zucc.yxr31501359.DBService.RcService;
import com.zucc.yxr31501359.DBService.SZService;
import com.zucc.yxr31501359.DBService.UserService;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.entity.RcBean;
import com.zucc.yxr31501359.entity.SZBean;

import java.util.Calendar;

public class AddSZFragment extends Fragment {

    private EditText dEditText;
    private int  hour, minute;
    private StringBuffer  time;
    /*private LinearLayout  llTime;*/
    private EditText stEditText,etEditText;
    private Button addbtn;
    private SQLiteDatabase db;



    private EditText titleET,datamET,szET,moneyET,statusET,contentET;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_sz, container, false);

        time = new StringBuffer();
        datamET = (EditText) view.findViewById(R.id.starttime);
        etEditText = (EditText) view.findViewById(R.id.endtime);
        dEditText = (EditText) view.findViewById(R.id.Car);

        titleET = (EditText) view.findViewById(R.id.title);
        datamET= (EditText) view.findViewById(R.id.datam);
        szET= (EditText) view.findViewById(R.id.sz);
        moneyET= (EditText) view.findViewById(R.id.money);
        statusET= (EditText) view.findViewById(R.id.status);
        contentET= (EditText) view.findViewById(R.id.content);
        addbtn= (Button) view.findViewById(R.id.addbtn);




        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SZBean szBean = new SZBean();
                szBean.setTitle(titleET.getText().toString());
                szBean.setDatam(datamET.getText().toString());
                szBean.setSz(szET.getText().toString());
                szBean.setMoney(Double.parseDouble(moneyET.getText().toString()));
                szBean.setStatus(statusET.getText().toString());

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
                AddSZFragment.this.datamET.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }

    protected void showSTimePickDlg() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                AddSZFragment.this.stEditText.setText(hour + ":" + minute );
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();


    }
    protected void showETimePickDlg() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                AddSZFragment.this.etEditText.setText(hour + ":" + minute );
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();


    }




}
