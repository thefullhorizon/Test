package com.example.nanshan.test.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yang_zhao on 2017/12/11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    abstract int getLayout();
    abstract void initializeView();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initializeView();
    }


}
