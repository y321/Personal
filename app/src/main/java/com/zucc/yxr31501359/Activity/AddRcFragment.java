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
import android.widget.*;
import com.zucc.yxr31501359.DBService.RcService;
import com.zucc.yxr31501359.DBService.Time;
import com.zucc.yxr31501359.DBService.UserService;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.entity.RcBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddRcFragment extends Fragment {

    private EditText dEditText;
    private int  hour, minute;
    private StringBuffer  time;
    /*private LinearLayout  llTime;*/
    private EditText stEditText,etEditText;
    private Button addbtn;
    private SQLiteDatabase db;
    private Spinner status,remand;
    private String statustr="",remandstr="";


    private EditText titleET,placeET,dataET,starttimeET,endtimeET,repeatET,contentET;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_rc, container, false);




        time = new StringBuffer();


        titleET = (EditText) view.findViewById(R.id.title);
        placeET= (EditText) view.findViewById(R.id.place);
        dEditText= (EditText) view.findViewById(R.id.Car);
        stEditText= (EditText) view.findViewById(R.id.starttime);
        etEditText= (EditText) view.findViewById(R.id.endtime);

        status = (Spinner) view.findViewById(R.id.status);
        remand= (Spinner) view.findViewById(R.id.remand);
        contentET= (EditText) view.findViewById(R.id.content);
        addbtn= (Button) view.findViewById(R.id.addbtn);

        Bundle bundle = getArguments();
        String string = bundle.getString("cd");
        dEditText.setText(string);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RcBean rcBean = new RcBean();
                rcBean.setTitle(titleET.getText().toString());
                rcBean.setPlace(placeET.getText().toString());
                rcBean.setRcdata(dEditText.getText().toString());
                rcBean.setStartTime(stEditText.getText().toString());
                rcBean.setEndTime(etEditText.getText().toString());
                rcBean.setStatus(statustr);
                rcBean.setRemindTime(remandstr);
                rcBean.setRemarks(contentET.getText().toString());
                rcBean.setUid(UserService.users_login);
                Log.v("123",rcBean.getStartTime());
                RcService rcService = new RcService(MainActivity.db);
                rcService.addRc(rcBean);

                    getFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)  //将当前fragment加入到返回栈中
                            .replace(R.id.content, new Rc_home()).commit();

            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.context, android.R.layout.simple_spinner_item,
                getData());
        // 把定义好的Adapter设定到spinner中
        status.setAdapter(adapter);
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                MainActivity.context, android.R.layout.simple_spinner_item,
                getDataremand());
        // 把定义好的Adapter设定到spinner中
        remand.setAdapter(adapter1);

        remand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                remandstr=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
                //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
            }
        });




        /*点击EditText显示时间日期控件*/
        dEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }

                return false;
            }
            });
        stEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showSTimePickDlg();
                    return true;
                }

                return false;
            }
        });
        etEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showETimePickDlg();
                    return true;
                }

                return false;
            }
        });


        dEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickDlg();
                }

            }
        });
        stEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showSTimePickDlg();
                }

            }
        });
        etEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showSTimePickDlg();
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
                AddRcFragment.this.dEditText.setText(year + "-" + Time.toAddZero(monthOfYear+1)  + "-" + Time.toAddZero(dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }

    protected void showSTimePickDlg() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                AddRcFragment.this.stEditText.setText(Time.toAddZero(hour) + ":" + Time.toAddZero(minute) );
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();


    }
    protected void showETimePickDlg() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                AddRcFragment.this.etEditText.setText(Time.toAddZero(hour) + ":" +Time.toAddZero( minute ));
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();


    }
    private List<String> getData() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
            dataList.clear();
            dataList.add("重要");
            dataList.add("非常重要");
            dataList.add("不重要");
            dataList.add("非常不重要");

        return dataList;
    }
    private List<String> getDataremand() {
        // 数据源
        List<String> dataList = new ArrayList<String>();
        dataList.clear();
        dataList.add("不提醒");
        dataList.add("到时提醒");
        dataList.add("提前5分钟");
        dataList.add("提前15分钟");
        dataList.add("提前30分钟");
        dataList.add("提前一小时");
        return dataList;
    }





}
