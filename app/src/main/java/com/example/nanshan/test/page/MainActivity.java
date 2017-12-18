package com.example.nanshan.test.page;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;
import com.example.nanshan.test.R;
import com.example.nanshan.test.util.ViewServer;
import com.example.nanshan.test.widget.dialog.GeneralDialog;

import java.util.Locale;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView mLocation11,mLocation12,
                        mLocation21,mLocation22;

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    int getLayout() {
        //newly add on github
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
        mLocation22.setOnClickListener(this);
        ViewServer.get(this).addWindow(this);
        getLanguageEnv();

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

                break;
            case R.id.tv_location22:

                break;

            default:

                break;
        }
    }

    private void showDialog() {
        GeneralDialog greetDialog = new GeneralDialog();
        greetDialog.show(getFragmentManager(), "GeneralDialog");
    }


}
