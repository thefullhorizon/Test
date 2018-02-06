package com.example.nanshan.test.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yang_zhao on 2017/12/26.
 */

public class ToastUtil {

    public static void show(Context context, String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
