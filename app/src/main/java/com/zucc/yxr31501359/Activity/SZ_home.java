package com.zucc.yxr31501359.Activity;



import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.zucc.yxr31501359.DBService.SZService;
import com.zucc.yxr31501359.DBService.UserService;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.Adapter.SZAdapter;
import com.zucc.yxr31501359.entity.SZBean;

import java.util.ArrayList;
import java.util.List;


public class SZ_home extends Fragment {

    private List<SZBean> szBeans = new ArrayList<SZBean>();






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initSZ();
        View view = inflater.inflate(R.layout.fragment_sz, container, false);
        SZAdapter szAdapter = new SZAdapter(MainActivity.context, R.layout.sz_item_frag, szBeans);
        ListView listView = (ListView)view.findViewById(R.id.sz_list);
        listView.setAdapter(szAdapter);


        return view;
    }
    private void initSZ() {
        SZService szService = new SZService(MainActivity.db);
        szBeans=szService.AllRc(UserService.users_login);
    }
}








