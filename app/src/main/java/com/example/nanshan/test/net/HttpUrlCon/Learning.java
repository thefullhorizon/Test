package com.example.nanshan.test.net.HttpUrlCon;

import android.app.Activity;

import com.example.nanshan.test.page.MainActivity;
import com.example.nanshan.test.util.ToastUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yang_zhao on 2017/12/28.
 */

public class Learning {

    /**
     * HttpUrlConnection基础使用
     *
     *
     *
     *
     */


    private Activity activity;
    public Learning(Activity activity){
        this.activity = activity;
    }

    /**
     *
     * 测试HttpUrlConnection Get
     */
    public void testHttpUrlConGet() {

//        if (progressBar == null){
//            progressBar = new ProgressBar(MainActivity.this);
//            progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
//            progressBar.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
//        }
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://www.weather.com.cn/data/cityinfo/101010100.html");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            final String result = new String(readStream(in),"utf-8");
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(activity,"Complete request");
                }
            });

        }catch (UnsupportedEncodingException e) {
            System.out.print(e.toString());
        }catch (MalformedURLException e){
            System.out.print(e.toString());
        }catch (IOException e){
            System.out.print(e.toString());
        }catch (Exception e){
            System.out.print(e.toString());
        }finally {
            urlConnection.disconnect();
        }
    }

    public byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = null;
        try {
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            return outSteam.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outSteam.close();
            inStream.close();
        }
        return null;
    }

    /**
     * 测试HttpUrlConnection Post
     */
    private void testHttpUrlConPost() {

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://www.weather.com.cn/data/cityinfo/101010100.html");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
//            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
//            writeStream(out);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);
        }catch (UnsupportedEncodingException e) {
            System.out.print(e.toString());
        }catch (MalformedURLException e){
            System.out.print(e.toString());
        }catch (IOException e){
            System.out.print(e.toString());
        }catch (Exception e){
            System.out.print(e.toString());
        }finally {
            urlConnection.disconnect();
        }
    }


}
