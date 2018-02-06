package com.example.nanshan.test.widget.dialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.nanshan.test.R;
import com.example.nanshan.test.widget.view.DialogGeneralStyleView;

/**
 * Created by yang_zhao on 2017/12/5.
 */

public class GeneralDialog extends DialogFragment{

    public static final String DATA = "data";

    private static final String TAG = GeneralDialog.class.getSimpleName();
    private String data;

    public GeneralDialog(){
        super();
        Log.i(TAG,"construct GeneralDialog()");
    }

    public GeneralDialog(String data){
        Log.i(TAG,"construct GeneralDialog(data)");

        Bundle bundle = new Bundle();
        bundle.putString(DATA,"data");
        setArguments(bundle);//处理屏幕切换是保存数据

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView");
        configDialog();

        View rootView = getRootView(inflater, container,savedInstanceState);

        return rootView;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        data = args.getString("content");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("content",data);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    private View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_container_layout, container);
        DialogGeneralStyleView dialog_container_root = (DialogGeneralStyleView)view.findViewById(R.id.dg_root_in_container);
        dialog_container_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        if (savedInstanceState != null){
            dialog_container_root.setContentString(savedInstanceState.getString(DATA));
        }
        return view;
    }

    private void configDialog() {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
    }

}
