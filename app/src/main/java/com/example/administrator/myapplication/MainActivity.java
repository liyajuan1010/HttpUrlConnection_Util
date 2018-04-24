package com.example.administrator.myapplication;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.net.CallBack;
import com.example.administrator.myapplication.net.CallBackT;
import com.example.administrator.myapplication.net.HttpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnGet,btnPost,btnDownloadFile;
    private TextView tvShowMessage;
    private static final String TAG="MainActivity";
    private Handler mHandler;
    private ImageView ivImage;
    private static final  int GET=0;
    private static final int POST=1;
    private static final int DOWNLOAD_FILE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }

    private void initView() {
        btnGet=(Button)findViewById(R.id.btn_get);
        btnPost=(Button)findViewById(R.id.btn_post);
        btnDownloadFile=(Button)findViewById(R.id.btn_download_file);
        tvShowMessage=(TextView)findViewById(R.id.tv_show_message);
        ivImage=(ImageView)findViewById(R.id.iv_image);
    }

    private void init() {
        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnDownloadFile.setOnClickListener(this);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case GET:
//                Bundle bundle=msg.getData();
//                String strRet=bundle.getString("key");
                        String strRet=(String)msg.obj;
                        tvShowMessage.setText("GET方法获取:"+strRet);
                        break;
                    case POST:
                        String str=(String)msg.obj;
                        tvShowMessage.setText("POST方法获取："+str);
                        break;
                    case  DOWNLOAD_FILE:
                        ivImage.setImageBitmap((Bitmap)msg.obj);
                       break;
                }
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get:
                getData();
                break;
            case R.id.btn_post:
                postData();
                break;
            case R.id.btn_download_file:
                downloadFile();
                break;
        }
    }

    private void getData() {
       HttpUtil.requestGet(new CallBack() {
           @Override
           public void onResponse(String response) {
               Message msg=mHandler.obtainMessage();
               msg.what=GET;
               msg.obj=response;
               mHandler.sendMessage(msg);
           }

           @Override
           public void onFailed(Exception e) {

           }
       });
    }

    private void postData(){
        HttpUtil.requestPost(new CallBack() {
            @Override
            public void onResponse(String response) {
                Message msg=mHandler.obtainMessage();
                msg.what=POST;
                msg.obj=response;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    private void downloadFile(){
        HttpUtil.downloadFile(new CallBackT<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Message msg=mHandler.obtainMessage();
                msg.what=DOWNLOAD_FILE;
                msg.obj=response;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}
