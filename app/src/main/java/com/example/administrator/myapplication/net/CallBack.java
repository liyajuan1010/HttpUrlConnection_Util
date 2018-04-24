package com.example.administrator.myapplication.net;

/**
 * Created by Administrator on 2018/4/18.
 */

public interface CallBack {
    public  void  onResponse(String response);
    public void onFailed(Exception e);
}
