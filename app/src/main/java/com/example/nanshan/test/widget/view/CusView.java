package com.example.nanshan.test.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.nanshan.test.R;

/**
 * Created by NANSHAN on 12/12/17.
 */

public class CusView extends View {

    private int width;//设置高
    private int height;//设置高
    private Paint mPaint,mPaintTemp;
    //设置一个Bitmap
    private Bitmap bitmap;
    //创建该Bitmap的画布
    private Canvas bitmapCanvas;

    private RectF dstRect, srcRect;
    private Bitmap dstBmp, srcBmp;
    private int centerX;
    private int centerY;

    public CusView(Context context,  AttributeSet attrs) {
        super(context, attrs);

        dstBmp = BitmapFactory.decodeResource(getResources(), R.drawable.dst1);
        srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.up);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaintTemp = new Paint();
        mPaintTemp.setAntiAlias(true);
        mPaintTemp.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(dstBmp, null, dstRect, new Paint());

        int sc = canvas.saveLayer(dstRect,mPaint,Canvas.ALL_SAVE_FLAG);

        mPaint.setColor(Color.WHITE);
        mPaint.setAlpha(0x80);
        canvas.drawRect(dstRect,mPaint);

        canvas.drawRect(centerX - width / 3, centerY - height / 6 + 100,
                centerX + width / 3, centerY + height / 6 + 100, mPaintTemp);

        mPaintTemp.setXfermode(null);
        canvas.restoreToCount(sc);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        centerX = width /2;
        centerY = width /2;
        setMeasuredDimension(width, height);//设置宽和高

        //自己创建一个Bitmap(在bitmap的canvas上画画)
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);//该画布为bitmap的


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = w <= h ? w : h;
        int centerX = w/2;
        int centerY = h/2;
        int quarterWidth = width /4;

        dstRect = new RectF(centerX-dstBmp.getWidth()/2, centerY-dstBmp.getHeight()/2,
                centerX+dstBmp.getWidth()/2, centerY+dstBmp.getHeight()/2);

        srcRect = new RectF(centerX-quarterWidth, centerY-quarterWidth, centerX+quarterWidth, centerY+quarterWidth);
    }


}
