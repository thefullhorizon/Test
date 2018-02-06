package com.example.nanshan.test.page;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nanshan.test.R;
import com.example.nanshan.test.net.HttpUrlCon.Learning;
import com.example.nanshan.test.net.HttpUrlCon.TestCaseHttpUrlCon;
import com.example.nanshan.test.util.ToastUtil;
import com.example.nanshan.test.util.ViewServer;
import com.example.nanshan.test.widget.dialog.GeneralDialog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.zip.Inflater;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView mLocation11,mLocation12,
                        mLocation21,mLocation22;
    private TextView requestResult;
    private ProgressBar progressBar;

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    int getLayout() {
        //newly add on github
        //newly add on github 01
        //newly add on github 02
        return R.layout.activity_main;
    }

    @Override
    void initializeView() {

        mLocation11 = findViewById(R.id.tv_location11);
        mLocation11.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mLocation11.getPaint().setAntiAlias(true);//抗锯齿
        mLocation11.setOnClickListener(this);
        mLocation12 = findViewById(R.id.tv_location12);
        mLocation12.setOnClickListener(this);
        mLocation21 = findViewById(R.id.tv_location21);
        mLocation21.setOnClickListener(this);
        mLocation22 = findViewById(R.id.tv_location22);

        Button mButton = findViewById(R.id.btn_purchase_point);

        String str = "支付宝\n快捷支付";
        SpannableStringBuilder ss1 = new SpannableStringBuilder("支付宝\n快捷支付");
        //第一个参数是设置字体大小 true代表用dip单位，接下来是下标，最后是输入时的字符是否使用同样的样式，这里是都不需要。根本没输入
        ss1.setSpan(new AbsoluteSizeSpan(14,true), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new AbsoluteSizeSpan(10,true), str.indexOf("\n"), str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mButton.setAllCaps(false);//
        mButton.setText(ss1);

        mLocation22.setOnClickListener(this);
        ViewServer.get(this).addWindow(this);
        getLanguageEnv();

        requestResult = findViewById(R.id.tv_request_result);
        requestResult.setText(
                Environment.getDataDirectory().getAbsolutePath()+"\n"// /data

        + Environment.getExternalStorageDirectory().getPath()+"\n"// /storage/emulated/0(4.0+)
        + this.getExternalFilesDir(null).getPath()+"\n"///mnt/sdcard (Android < 4.0)  /storage/emulated/0/package name/files (Android 4.0+)
        + this.getFilesDir().getPath()//6.0+返回data/user/x(0)/package name/files 6.0-返回/data/data/package name/files
        );

    }

    private boolean isEnglishAndChineseZone() {
        String language = Locale.getDefault().getLanguage();
        if ("zh".equals(language) || "en".equals(language) ) {
            return true;
        }
        return false;
    }


    private String getLanguageEnv() {
        Locale l = Locale.getDefault();
        String language = l.getLanguage();
        String country = l.getCountry().toLowerCase();
        if ("zh".equals(language)) {
            if ("cn".equals(country)) {
                language = "zh_cn";
            } else if ("tw".equals(country)) {
                language = "zh_tw";
            } else if ("hk".equals(country)) {
                language = "zh_hk";
            }else{
                language = "zh";
            }
        } else if ("en".equals(language)) {
            if ("us".equals(country)) {
                language = "en_us";
            } else if ("gb".equals(country)) {
                language = "pt_gb";
            }
        }
        return language;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_location11:

                showDialog();

                break;
            case R.id.tv_location12:

                //customized View

                break;
            case R.id.tv_location21:
                //HttpClient elementary practice


                //HttpURLConnection elementary practice
//                 ToastUtil.show(MainActivity.this,"Start request");
//                new Thread(){
//                    @Override
//                    public void run() {
//                        Learning learning = new Learning(MainActivity.this);
//                        learning.testHttpUrlConGet();
//                    }
//                }.start();

                //HttpURLConnection encapsulation
                TestCaseHttpUrlCon testCaseHttpUrlCon = new TestCaseHttpUrlCon(MainActivity.this);
                testCaseHttpUrlCon.testCase();

                break;
            case R.id.tv_location22:

                break;

            default:

                break;
        }
    }

    /**
     * 测试对话框（DialogFragment方式实现）
     */
    private void showDialog() {
//        GeneralDialog greetDialog = new GeneralDialog("KL");
//        greetDialog.show(getFragmentManager(), "GeneralDialog");

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.pnl_saveprogressbar, null);
        builder.setView(view);
        Dialog progressDialog = builder.create();
        progressDialog.show();
//        Dialog dia = new AlertDialo(MainActivity.this);


    }

//    保存到应用的目录下
    private void saveImageToCache(){

    }

    private void showImageFromCache(){


    }


}
