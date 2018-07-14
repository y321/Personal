package com.zucc.yxr31501359.calendar;


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

    public SZAdapter(Context context, int textViewResourceId, List<SZBean> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SZBean szBean = (SZBean) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView fruitName = (TextView) view.findViewById(R.id.sz_title);
        fruitName.setText(szBean.getTitle());
        return view;
    }
}
