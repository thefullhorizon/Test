package com.example.nanshan.test.widget.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nanshan.test.R;

/**
 * Created by yang_zhao on 2017/12/11.
 */

public class DialogGeneralStyleView extends LinearLayout {

    public DialogGeneralStyleView(Context context) {
        this(context, null);
    }
    public DialogGeneralStyleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public DialogGeneralStyleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.dialog_general_style_view,this,true);

        TextView content = findViewById(R.id.tv_content);
        content.setText("Display");
    }


}
