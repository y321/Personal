package com.zucc.yxr31501359.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.entity.RcBean;
import com.zucc.yxr31501359.entity.SZBean;

import java.util.List;

/**
 * Created by lw on 2017/4/14.
 */

public class RCAdapter extends ArrayAdapter{
    private final int resourceId;

    public RCAdapter(Context context, int textViewResourceId, List<RcBean> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RcBean rcBean = (RcBean) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView rcTV = (TextView) view.findViewById(R.id.rc_title);
        rcTV.setText(rcBean.getTitle());
        return view;
    }
}
