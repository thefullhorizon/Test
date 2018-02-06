package com.example.nanshan.test.net.HttpUrlCon;

import android.app.Activity;
import android.util.Log;

/**
 * Created by yang_zhao on 2017/12/27.
 */

public class TestCaseHttpUrlCon {

    private static final String TAG = TestCaseHttpUrlCon.class.getSimpleName();
    private Activity activity;
    public TestCaseHttpUrlCon(Activity activity){
        this.activity = activity;
    }

    public void testCase() {

//        String urlParams = "name=Bob&age=18&weight=60";
        String urlParams = "";
        String url = "http://www.weather.com.cn/data/cityinfo/101010100.html";

        HttpManager h = HttpManager.getHttpUtil(url, urlParams, activity, new HttpRequestListener(){

            public void onResponse(String result) {
                Log.d(TAG, "string from server: " + result);
            }

        });
        h.httpGet();

    }

}
