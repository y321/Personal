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

public class RcxqFragment extends Fragment {

    private EditText dEditText;
    private int  hour, minute;
    private StringBuffer  time;
    /*private LinearLayout  llTime;*/
    private EditText stEditText,etEditText;
    private Button updatebtn,deletebtn;
    private SQLiteDatabase db;
    private Spinner status,remand;
    private RcBean rcBean;
    private String statustr="",remandstr="";
    private Button picture;

    private EditText titleET,placeET,contentET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.rcxq_fragment, container, false);
        Bundle bundle = getArguments();
        rcBean= (RcBean) bundle.getSerializable("rcBean");


        time = new StringBuffer();


        titleET = (EditText) view.findViewById(R.id.title);
        placeET= (EditText) view.findViewById(R.id.place);
        dEditText= (EditText) view.findViewById(R.id.Car);
        stEditText= (EditText) view.findViewById(R.id.starttime);
        etEditText= (EditText) view.findViewById(R.id.endtime);

        picture = (Button)view.findViewById(R.id.picture);

        status = (Spinner) view.findViewById(R.id.status);
        remand= (Spinner) view.findViewById(R.id.remand);
        contentET= (EditText) view.findViewById(R.id.content);

        updatebtn = (Button) view.findViewById(R.id.updatabtn);
        deletebtn = (Button) view.findViewById(R.id.deletebtn);

        titleET.setText(rcBean.getTitle());
        placeET.setText(rcBean.getPlace());
        dEditText.setText(rcBean.getRcdata());
        stEditText.setText(rcBean.getStartTime());
        etEditText.setText(rcBean.getEndTime());

        contentET.setText(rcBean.getRemarks());


        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RcBean rcBean1 = new RcBean();
                rcBean1.setTitle(titleET.getText().toString());
                rcBean1.setPlace(placeET.getText().toString());
                rcBean1.setRcdata(dEditText.getText().toString());
                rcBean1.setStartTime(stEditText.getText().toString());
                rcBean1.setEndTime(etEditText.getText().toString());
                rcBean1.setStatus(statustr);

                rcBean1.setRemindTime(remandstr);
                rcBean1.setRemarks(contentET.getText().toString());
                rcBean1.setUid(UserService.users_login);
                rcBean1.setRcid(rcBean.getRcid());

                RcService rcService = new RcService(MainActivity.db);
                rcService.updateRC(rcBean1);

                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)  //将当前fragment加入到返回栈中
                        .replace(R.id.content, new Rc_home()).commit();

            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RcService rcService = new RcService(MainActivity.db);
                rcService.deleteRC(rcBean.getRcid());

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
//        Log.v("hahah",Integer.toString(adapter.getPosition(rcBean.getStatus()))+rcBean.getStatus());
        status.setSelection(adapter.getPosition(rcBean.getStatus()));
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                statustr=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                MainActivity.context, android.R.layout.simple_spinner_item,
                getDataremand());
        // 把定义好的Adapter设定到spinner中
        remand.setAdapter(adapter1);

        remand.setSelection(adapter1.getPosition(rcBean.getRemindTime()));
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
                RcxqFragment.this.dEditText.setText(year + "-" + Time.toAddZero(monthOfYear+1)  + "-" + Time.toAddZero(dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }

    protected void showSTimePickDlg() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                RcxqFragment.this.stEditText.setText(Time.toAddZero(hour) + ":" + Time.toAddZero(minute) );
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();


    }
    protected void showETimePickDlg() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                RcxqFragment.this.etEditText.setText(Time.toAddZero(hour) + ":" +Time.toAddZero( minute ));
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
