package com.zucc.yxr31501359.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.entity.SZBean;

import java.util.List;

/**
 * Created by lw on 2017/4/14.
 */

public class SZAdapter extends ArrayAdapter{
    private final int resourceId;
    TextView sz,money,title,datam,status;
    ImageView iv;

    public SZAdapter(Context context, int textViewResourceId, List<SZBean> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SZBean szBean = (SZBean) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        sz= (TextView) view.findViewById(R.id.sz);
        money  = (TextView) view.findViewById(R.id.money);
        title  = (TextView) view.findViewById(R.id.title);
        datam  = (TextView) view.findViewById(R.id.datam);
        status = (TextView) view.findViewById(R.id.status);
        iv = (ImageView) view.findViewById(R.id.Isz);

        sz.setText(szBean.getSz());
        money.setText(Double.toString(szBean.getMoney()));
        title.setText(szBean.getTitle());
        datam.setText(szBean.getDatam());
        status.setText(szBean.getStatus());
        if(szBean.getSz().equals("收入")){
            iv.setImageResource(R.drawable.sr_icon);
        }else{
            iv.setImageResource(R.drawable.zc_icon);
        }

        return view;
    }
}
