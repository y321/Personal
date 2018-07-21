package com.zucc.yxr31501359.ICallback;

import com.zucc.yxr31501359.entity.Fundbean;

import java.util.List;

public interface ICallBack {
    public void onFinished(String result);
    public void onFinishedFund(List<Fundbean> fundbeans);

}
