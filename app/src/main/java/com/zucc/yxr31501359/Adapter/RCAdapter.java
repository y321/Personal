package com.zucc.yxr31501359.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.entity.RcBean;
import com.zucc.yxr31501359.entity.SZBean;

import java.util.List;



public class RCAdapter extends ArrayAdapter{
    private final int resourceId;
    ImageView zy;

    public RCAdapter(Context context, int textViewResourceId, List<RcBean> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RcBean rcBean = (RcBean) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView rcTV = (TextView) view.findViewById(R.id.rc_title);
        TextView rc_date = (TextView) view.findViewById(R.id.rc_date);
        TextView rc_stime = (TextView) view.findViewById(R.id.rc_stime);
        TextView rc_etime = (TextView) view.findViewById(R.id.rc_etime);

        zy = (ImageView) view.findViewById(R.id.zy);

        rcTV.setText(rcBean.getTitle());
        rc_date.setText(rcBean.getRcdata());
        rc_stime.setText(rcBean.getStartTime());
        rc_etime.setText(rcBean.getEndTime());
        if (rcBean.getStatus()!=null) {
            if (rcBean.getStatus().equals("重要")) {
                zy.setImageResource(R.drawable.y);
            } else if (rcBean.getStatus().equals("非常重要")) {
                zy.setImageResource(R.drawable.r);
            } else if (rcBean.getStatus().equals("不重要")) {
                zy.setImageResource(R.drawable.g);
            } else {
                zy.setImageResource(R.drawable.b);
            }
        }

        return view;
    }
}
