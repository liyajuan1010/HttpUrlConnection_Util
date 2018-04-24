package com.example.administrator.myapplication.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUtil {
    private static final String TAG="HttpUtil";
    private static Handler mHandler;
    //工具类中放静态方法static
    public  static void requestGet(final CallBack callBack){
        //泛型 （final CallBackT<String>  callback）
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection=null;
                try {
                    URL url=new URL("http://10.0.2.2/get.php");
                    urlConnection=(HttpURLConnection)url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    if(urlConnection.getResponseCode()==200){
                        InputStream is=urlConnection.getInputStream();
                        String strRet=Stream2String(is);

                        callBack.onResponse(strRet);
                        Log.i(TAG,strRet);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            }
        }).start();

    }

    public static String Stream2String(InputStream is){
        StringBuilder sBuilder=new StringBuilder();
        byte[] buffer=new byte[520];
        int hasRead=-1;
        try {
            while ((hasRead=is.read(buffer))!=-1){
                sBuilder.append(new String(buffer,0,hasRead));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sBuilder.toString();
        }
    }

    public static void requestPost(final CallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection=null;
                try {
                    URL url=new URL("http://10.0.2.2/post.php");
                    String postBody=String.format("%s=%s","key","post");
                    urlConnection=(HttpURLConnection)url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setUseCaches(false);
                    urlConnection.connect();
                    OutputStream os=urlConnection.getOutputStream();//获取输出流
                    os.write(postBody.getBytes());
                    os.flush();
                    os.close();//post方法只是多了发的过程，get方法是自己发的
                    if (urlConnection.getResponseCode()==200){
                        InputStream is=urlConnection.getInputStream();
                        String strRet=Stream2String(is);
                        callBack.onResponse(strRet);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            }
        }).start();

    }

    public static void downloadFile(final CallBackT<Bitmap> callBackT){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection=null;
                try {
                    URL url=new URL("http://10.0.2.2/1.jpeg");
                    urlConnection= (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    if (urlConnection.getResponseCode()==200){
                        InputStream is=urlConnection.getInputStream();
                        Bitmap bitmap= BitmapFactory.decodeStream(is);
                        callBackT.onResponse(bitmap);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            }
        }).start();

    }
}
