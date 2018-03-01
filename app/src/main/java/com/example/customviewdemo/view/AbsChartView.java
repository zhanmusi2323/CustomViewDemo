package com.example.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import static android.graphics.Color.BLUE;

/**
 * 基本view
 */
public class AbsChartView extends TemplateFrameLayout {

    public static final int GREED = 0xFF84CB86;

    protected int mWidth;
    protected int mHeight;
    protected int mBackgroundColor = BLUE;         //背景的颜色
    protected int mBackgroundAngle = 10;            //背景圆角度
    public int mMargin = 30;

    public AbsChartView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBacground(canvas);
    }

    protected void drawBacground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(mBackgroundColor);
        paint.setAntiAlias(true);
        RectF rect = new RectF(changeSize(mMargin), changeSize(mMargin), mWidth - changeSize(mMargin), mHeight - changeSize(mMargin));
        canvas.drawRoundRect(rect, changeSize(mBackgroundAngle), changeSize(mBackgroundAngle), paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
