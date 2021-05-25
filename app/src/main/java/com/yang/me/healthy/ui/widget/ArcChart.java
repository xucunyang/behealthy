package com.yang.me.healthy.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class ArcChart extends View {
    //外部传入的比例参数
    private final float arrPer;
    //RGB颜色数组 为渐变准备为数组，起始颜色值和终止颜色值
    private final int[] mMinColors = {Color.parseColor("#FF8359"), Color.parseColor("#FFDF40")};

    public ArcChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        arrPer = 50f;
    }

    public void onDraw(Canvas canvas) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;//这里是控件view的宽度，设置为了屏幕宽度
        float mMinOvalR = screenWidth / 6f; //圆环直径
        float mMinWidth = 90; //圆环宽度
        float startAngle = 90;//圆弧起始角度
        float degreeCircle = 180;//圆头位置
        float matrixStart = 90;//渐变旋转角度

        //创建画笔：
        Paint mMinPaint = new Paint();
        mMinPaint.setColor(Color.parseColor("#F9F9F9"));
        mMinPaint.setAntiAlias(true); //启用抗锯齿
        mMinPaint.setDither(true); //启用抗颜色抖动（可以让渐变更平缓）
        mMinPaint.setStyle(Paint.Style.STROKE);//圆弧
        mMinPaint.setStrokeWidth(mMinWidth);//圆环宽度


        //外接矩形
        //算出矩形顶点坐标。
        RectF rect = new RectF(canvas.getWidth() / 2 - mMinOvalR,
                canvas.getHeight() / 2 - mMinOvalR,
                canvas.getWidth() - (canvas.getWidth() / 2 - mMinOvalR),
                canvas.getHeight() - (canvas.getHeight() / 2 - mMinOvalR));
        canvas.drawArc(rect, 0, 360, false, mMinPaint);//先画一圈背景灰色圆环
        float degree = 360 * (arrPer / 100); //圆弧扫过的角度
        matrixStart += degree;//渐变旋转角度要开始计算
        //创建渐变
        SweepGradient mSweepGradient = new SweepGradient(canvas.getWidth() / 2,
                canvas.getHeight() / 2,
                mMinColors,
                new float[]{0f, degree});
        Matrix matrix = new Matrix();//将渐变旋转
        matrix.setRotate(matrixStart, canvas.getWidth() / 2, canvas.getHeight() / 2);
        mSweepGradient.setLocalMatrix(matrix);
        //把渐变设置到笔刷
        mMinPaint.setShader(mSweepGradient);
        //画渐变的弧
        canvas.drawArc(rect, startAngle, degree, false, mMinPaint);
        //记录下位置
        float[] arrayInt = {0, arrPer};
        //获取渐变弧结束点的颜色值和渐变的开始值
        int[] newColor = {Color.rgb(255,
                (int) (223 + (-0.92 * arrPer)), (int) (64 + (0.25 * arrPer))), mMinColors[1]};
        //画圆弧开始和结束的圆点
        for (int i = 0; i < 2; i++) {
            Paint mMinCirclePaint = new Paint();//定义头部画笔
            mMinCirclePaint.setColor(newColor[i]);//取颜色值
            mMinCirclePaint.setAntiAlias(true);//启用抗锯齿
            degreeCircle += 360 * (arrayInt[i] / 100); //圆位置等于圆弧长度
            float mDegreeCircle = degreeCircle - 90;//抵消屏幕坐标系差异
            mDegreeCircle = (float) (Math.PI / 180f * mDegreeCircle); //换成弧度
            canvas.drawCircle((float) (rect.left + rect.width() / 2f + mMinOvalR * Math.cos(mDegreeCircle)), //圆心x
                    (float) (rect.top + rect.height() / 2f + mMinOvalR * Math.sin(mDegreeCircle)), //圆心y
                    mMinWidth / 2f,  //半径
                    mMinCirclePaint);
        }
    }
}