package com.zucc.yxr31501359.Activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
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
import com.zucc.yxr31501359.DBService.LCService;
import com.zucc.yxr31501359.R;

public class LC_home extends Fragment {
    private Spinner qix;
    private String qixstr,typeStr="定期";
    private EditText nlvET,cunk;
    private RadioGroup type;
    private RadioButton hq,dq;
    private Button js;
    private Double year;
    private TextView lx,bx;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lc, container, false);

        lx=(TextView) view.findViewById(R.id.lxtv);
        bx=(TextView) view.findViewById(R.id.bxtv);


        qix=(Spinner)view.findViewById(R.id.qix);
        nlvET=(EditText)view.findViewById(R.id.nlv);
        cunk=(EditText)view.findViewById(R.id.cunk);
        type=(RadioGroup)view.findViewById(R.id.type);
        hq=(RadioButton)view.findViewById(R.id.hq);
        dq=(RadioButton)view.findViewById(R.id.dq);
        js=(Button) view.findViewById(R.id.js);
        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if(checkedId==R.id.hq){
                    typeStr=hq.getText().toString();
                }else {
                    typeStr=dq.getText().toString();
                }
                Log.v("typeStr",typeStr);
            }
        });




        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(MainActivity.context, R.array.qix, android.R.layout.simple_spinner_item);
        // 把定义好的Adapter设定到spinner中
        qix.setAdapter(adapter);
        qix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                qixstr=parent.getItemAtPosition(position).toString();

                if(typeStr.equals("定期")){
                    switch (qixstr){
                        case "3个月":
                            year=(3.0/12);
                            nlvET.setText("1.10");
                            break;
                        case "6个月":
                            year=(6.0/12);
                            nlvET.setText("1.30");
                            break;
                        case "1年":
                            year=1.0;
                            nlvET.setText("1.50");
                            break;
                        case "2年":
                            year=2.0;
                            nlvET.setText("2.10");
                            break;
                        case "3年":
                            year=3.0;
                            nlvET.setText("2.75");
                            break;
                        case "5年":
                            year=5.0;
                            nlvET.setText("2.75");
                            break;
                    }
                }else{
                    nlvET.setText("0.35");
                    switch (qixstr){
                        case "3个月":
                            year=(3.0/12);
                            break;
                        case "6个月":
                            year=(6.0/12);
                            break;
                        case "1年":
                            year=1.0;
                            break;
                        case "2年":
                            year=2.0;
                            break;
                        case "3年":
                            year=3.0;
                            break;
                        case "5年":
                            year=5.0;
                            break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
                //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
            }
        });
        js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LCService lcService = new LCService();
                Double lxd = lcService.getLX(Double.parseDouble(nlvET.getText().toString()),Double.parseDouble(cunk.getText().toString()),year);
                lx.setText(lxd.toString());
                Double bxd = lxd+Double.parseDouble(cunk.getText().toString());
                bx.setText(bxd.toString());
            }});


        return view;
    }

}

