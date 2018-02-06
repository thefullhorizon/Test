package com.example.nanshan.test.net.HttpUrlCon;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yang_zhao on 2017/12/27.
 */

public class HttpManager {

    private static final String TAG = HttpManager.class.getSimpleName();
    public Activity mActivity = null;
    public HttpRequestListener mCallback = null;
    private String mUrl = null;
    private String mStringParams = null;
    public static HttpManager mHttpUtil = null;

    public enum HTTP_TYPE {
        GET, POST
    }

    public enum PROTOCOL_TYPE {
        HTTP, HTTPS
    }

    private static HTTP_TYPE mType = HTTP_TYPE.GET;
    private static PROTOCOL_TYPE mProtocolType = PROTOCOL_TYPE.HTTP;

    public static HttpManager getInstance() {
        if (mHttpUtil != null) {
            return mHttpUtil;
        }
        Log.d(TAG, "please new HttpUtil first!");
        return null;
    }

    public static void deleteHttpUtil() {
        if (mHttpUtil != null) {
            mHttpUtil = null;
        }
    }

    /**
     *
     * @param url 请求地址
     * @param stringParams get方式下的请求参数
     * @param activity 请求所在的Activity
     * @param callback 请求的返回结果
     * @return
     */
    public static HttpManager getHttpUtil(
            final String url, final String stringParams,Activity activity, HttpRequestListener callback) {

        mHttpUtil = new HttpManager(url, stringParams, activity, callback);
        return mHttpUtil;
    }

    private HttpManager(final String url, final String stringParams, Activity activity, HttpRequestListener callback) {

        mActivity = activity;
        mUrl = url;
        mCallback = callback;
        mStringParams = stringParams;

        // verify request type
        try {
            URL httpUrl = new URL(mUrl);
            if (httpUrl.getProtocol().toLowerCase().equals("https")) {
                mProtocolType = PROTOCOL_TYPE.HTTPS;
            } else if (httpUrl.getProtocol().toLowerCase().equals("http")) {
                mProtocolType = PROTOCOL_TYPE.HTTP;
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }


        if (mActivity == null) {
            Log.e(TAG, "activity is null");
            return;
        }

        if (callback == null) {
            Log.e(TAG, "callback is null");
            return;
        }

    }

    public void httpGet() {
        mType = HTTP_TYPE.GET;
        if (!TextUtils.isEmpty(mStringParams)){
            if (!mUrl.contains("?")) {
                mUrl = mUrl + "?" + mStringParams;
            } else if (mUrl.substring(mUrl.length() - 1).equals("?")) {
                mUrl = mUrl + mStringParams;
            }
        }
        httpAccess();
    }

    public void httpPost() {
        mType = HTTP_TYPE.POST;
        httpAccess();
    }

    private void httpAccess() {

        try {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new HttpHandleCore(mActivity, mCallback, mType, mProtocolType, mStringParams)
                            .execute(new String[] { mUrl });
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
