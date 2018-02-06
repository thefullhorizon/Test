package com.example.nanshan.test.net.HttpUrlCon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;


/**
 * Created by yang_zhao on 2017/12/27.
 */

@SuppressLint("NewApi")
public class HttpHandleCore extends AsyncTask<String, Void, String> {

    private static final String TAG = HttpHandleCore.class.getSimpleName();
    private ProgressDialog dialog;
    private Activity mActivity = null;
    private HttpRequestListener mIHttpCallback = null;
    private HttpManager.HTTP_TYPE mType = HttpManager.HTTP_TYPE.GET;
    private HttpManager.PROTOCOL_TYPE mProtocolType = HttpManager.PROTOCOL_TYPE.HTTP;
    private final int CONNECTION_TIMEOUT = 5000; //建立连接超时时间 5s
    private final int READ_TIMEOUT = 5000; //数据传输超时时间 5s
    private String mParams = "";

    private static TrustManager[] xtmArray = new MytmArray[] { new MytmArray() };
    private static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     *
     * @param activity
     * @param callback
     * @param type request type e,g get
     * @param protocolType
     * @param params
     */
    public HttpHandleCore(Activity activity, HttpRequestListener callback, HttpManager.HTTP_TYPE type, HttpManager.PROTOCOL_TYPE protocolType, String params) {
        super();
        mActivity = activity;
        mIHttpCallback = callback;
        mType = type;
        mParams = params;
        mProtocolType = protocolType;
    }

    /**
     * 信任所有主机-对于任何证书都不做检查
     */
    private static void trustAllHosts() {
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, xtmArray, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mActivity != null) {
            dialog = ProgressDialog.show(mActivity, "提示", "操作请求正在发送，请稍等", true, false);
        }

    }

    @Override
    protected String doInBackground(String... urls) {

        if (urls == null || urls.length == 0) {
            return null;
        }

        String result = "";
        HttpURLConnection httpUrlCon = null;

        try {
            URL httpUrl = new URL(urls[0]);
            switch (mProtocolType) {
                case HTTP:
                    httpUrlCon = (HttpURLConnection) httpUrl.openConnection();
                    break;
                case HTTPS:
                    trustAllHosts();
                    httpUrlCon = (HttpsURLConnection) httpUrl.openConnection();
                    ((HttpsURLConnection) httpUrlCon).setHostnameVerifier(DO_NOT_VERIFY);// 不进行主机名确认
                    break;
                default:
                    break;
            }

            // set  http  configure
            httpUrlCon.setConnectTimeout(CONNECTION_TIMEOUT);// 建立连接超时时间
            httpUrlCon.setReadTimeout(READ_TIMEOUT);//数据传输超时时间，很重要，必须设置。
            httpUrlCon.setDoInput(true); // 向连接中写入数据
            httpUrlCon.setDoOutput(true); // 从连接中读取数据
            httpUrlCon.setUseCaches(false); // 禁止缓存
            httpUrlCon.setInstanceFollowRedirects(true);
            httpUrlCon.setRequestProperty("Charset", "UTF-8");
            httpUrlCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 设置请求类型
            switch (mType) {
                case GET:
                    httpUrlCon.setRequestMethod("GET");
                    break;
                case POST:
                    httpUrlCon.setRequestMethod("POST");
                    DataOutputStream out = new DataOutputStream(httpUrlCon.getOutputStream());
                    out.write(mParams.getBytes("utf-8"));// 将要传递的数据写入数据输出流,不要使用out.writeBytes(param); 否则中文时会出错
                    out.flush();
                    out.close();
                    break;
                default:
                    break;

            }

            httpUrlCon.connect();

            //check the result of connection
            if (httpUrlCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader in = new InputStreamReader(httpUrlCon.getInputStream());
                BufferedReader buffer = new BufferedReader(in);
                String inputLine = "";
                while ((inputLine = buffer.readLine()) != null) {
                    result += inputLine + "\n";
                }
                in.close();
            }else{
                Log.i(TAG, "responseCode: " + httpUrlCon.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //如果需要处理超时，可以在这里写
        } finally {
            if (httpUrlCon != null) {
                httpUrlCon.disconnect();
            }
        }
        Log.i(TAG, "result_str: " + result);
        return result;

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (dialog != null && mActivity != null) {
            dialog.dismiss();
        }
        mIHttpCallback.onResponse(result);
    }

}
