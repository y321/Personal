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
import com.zucc.yxr31501359.DBService.UserService;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.entity.RcBean;

import java.util.Calendar;


public class AddRcFragment extends Fragment {

    private EditText dEditText;
    private int  hour, minute;
    private StringBuffer  time;
    /*private LinearLayout  llTime;*/
    private EditText stEditText,etEditText;
    private Button addbtn;
    private SQLiteDatabase db;



    private EditText titleET,placeET,dataET,starttimeET,endtimeET,repeatET,remandET,contentET;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_rc, container, false);

        time = new StringBuffer();
        stEditText = (EditText) view.findViewById(R.id.starttime);
        etEditText = (EditText) view.findViewById(R.id.endtime);
        dEditText = (EditText) view.findViewById(R.id.Car);

        titleET = (EditText) view.findViewById(R.id.title);
        placeET= (EditText) view.findViewById(R.id.place);
        dataET= (EditText) view.findViewById(R.id.Car);
        starttimeET= (EditText) view.findViewById(R.id.starttime);
        endtimeET= (EditText) view.findViewById(R.id.endtime);
        repeatET= (EditText) view.findViewById(R.id.repeat);
        remandET= (EditText) view.findViewById(R.id.remand);
        contentET= (EditText) view.findViewById(R.id.remand);
        addbtn= (Button) view.findViewById(R.id.addbtn);




        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RcBean rcBean = new RcBean();
                rcBean.setTitle(titleET.getText().toString());
                rcBean.setPlace(placeET.getText().toString());
                rcBean.setRcdata(dataET.getText().toString());
                rcBean.setStartTime(starttimeET.getText().toString());
                rcBean.setEndTime(endtimeET.getText().toString());
                rcBean.setRepeat(repeatET.getText().toString());
                rcBean.setRemindTime("");
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
                AddRcFragment.this.dEditText.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }

    protected void showSTimePickDlg() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                AddRcFragment.this.stEditText.setText(hour + ":" + minute );
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();


    }
    protected void showETimePickDlg() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                AddRcFragment.this.etEditText.setText(hour + ":" + minute );
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();


    }




}
