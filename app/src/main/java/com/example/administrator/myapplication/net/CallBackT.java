package com.example.administrator.myapplication.net;

/**
 * Created by Administrator on 2018/4/18.
 */

public interface CallBackT<T> {
    public  void  onResponse(T response);
    public void onFailed(Exception e);
}
