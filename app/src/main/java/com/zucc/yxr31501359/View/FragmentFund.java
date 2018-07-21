package com.zucc.yxr31501359.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.zucc.yxr31501359.Adapter.FundAdapter;
import com.zucc.yxr31501359.ICallback.ICallBack;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.Task.FundTask;
import com.zucc.yxr31501359.entity.Fundbean;

import java.util.ArrayList;
import java.util.List;

public class FragmentFund extends Fragment implements ICallBack {

    private TextView a;
    private ListView listView ;
    private FundAdapter fundAdapter;
    private List<Fundbean> fundbeans = new ArrayList<Fundbean>();
    public  String cd;
    private Fundbean fundbean;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fund, container, false);

        FundTask task = new FundTask(FragmentFund.this);
        task.execute("http://web.juhe.cn:8080/fund/findata/main?key=344430a9c830f33ed6dc533cf5d90083");
        listView = (ListView)view.findViewById(R.id.fund_list);

        return view;

    }


    @Override
    public void onFinished(String result) {

    }

    @Override
    public void onFinishedFund(List<Fundbean> fundbeans) {
        Log.v("ssss",fundbeans.get(0).getName());
        fundAdapter = new FundAdapter(MainActivity.context, R.layout.item_fund,fundbeans);
        listView.setAdapter(fundAdapter);
    }
}
