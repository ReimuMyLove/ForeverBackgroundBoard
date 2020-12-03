package com.example.shoujiedemo.util;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TrancircleImageView extends View {
    //画笔
    private Paint mPaint;
    //检测当前手机屏幕大小
    private int height; //高
    private int width;  //宽
    public TrancircleImageView(@NonNull Context context) {
        super(context);
    }

    public TrancircleImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        mPaint = new Paint();
        //画圆形，指定好坐标，半径，画笔
        mPaint.setColor(0xfff4f3f4);
        mPaint.setTextSize(100);
        Path path = new Path();
        path.moveTo(width/5,width*6/5);// 此点为多边形的起点
        path.lineTo(width,width*6/5);
        path.lineTo(width, width/5);
        path.lineTo(width/5,width*6/5);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, mPaint);
        //canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
    }
}
