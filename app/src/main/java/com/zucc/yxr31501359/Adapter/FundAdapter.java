package com.zucc.yxr31501359.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.entity.Fundbean;

import java.util.List;


public class FundAdapter extends ArrayAdapter{
    private final int resourceId;
    ImageView zy;

    public FundAdapter(Context context, int textViewResourceId, List<Fundbean> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fundbean fundbean = (Fundbean) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView code = (TextView) view.findViewById(R.id.code);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView netincome = (TextView) view.findViewById(R.id.netincome);
        TextView assincome = (TextView) view.findViewById(R.id.assincome);
        TextView netgrowrate = (TextView) view.findViewById(R.id.netgrowrate);
        TextView tonetgrora = (TextView) view.findViewById(R.id.tonetgrora);



        code.setText(fundbean.getCode());
        name.setText(fundbean.getName());
        netincome.setText(fundbean.getNetincome());
        assincome.setText(fundbean.getAssincome());
        netgrowrate.setText(fundbean.getNetgrowrate());
        tonetgrora.setText(fundbean.getTonetgrora());
        return view;
    }
}