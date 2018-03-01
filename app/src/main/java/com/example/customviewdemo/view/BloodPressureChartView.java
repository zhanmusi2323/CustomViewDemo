package com.example.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class BloodPressureChartView extends BaseChartView<BloodPressureChartView> {
    private int mBotLineColor = 0xFFCCFF00;      //线条颜色
    private float mBotLineWidth = 4;        //线条宽度

    private float mTopInnerRaceWidth = 8f;        //内环宽度
    private float mTopOuterRaceWidth = 10.5f;        //外环宽度

    private int mBotInnerRaceColor = 0xFF6FD72F;        //内环颜色
    private int mBotOuterRaceColor = 0xFFD2FF02;        //外环颜色

    public BloodPressureChartView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBotLines(canvas);
        drawBotCircle(canvas);
    }

    protected void drawBotLines(Canvas canvas) {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(75, 512));
        points.add(new Point(385, 512));
        points.add(new Point(695, 210));
        points.add(new Point(1005, 210));
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(changeSize(mBotLineWidth));
        paint.setColor(mBotLineColor);
        PathMeasure pathMeasure = measurePath(points);
        Path path = new Path();
        pathMeasure.getSegment(0, pathMeasure.getLength(), path, true);
        canvas.drawPath(path, paint);
    }

    protected void drawBotCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        /**
         * 绘制外圆
         * */
        paint.setColor(mBotOuterRaceColor);
        canvas.drawCircle(385, 512, changeSize(mTopOuterRaceWidth), paint);


        /**
         * 绘制内圆
         * */
        paint.setColor(mBotInnerRaceColor);
        canvas.drawCircle(385, 512, changeSize(mTopInnerRaceWidth), paint);


        /**
         * 绘制内圆
         * */
        paint.setColor(mBotInnerRaceColor);
        canvas.drawCircle(695, 210, changeSize(mTopInnerRaceWidth), paint);
        /**
         * 绘制外圆
         * */
        paint.setColor(mBotOuterRaceColor);
        canvas.drawCircle(695, 210, changeSize(mTopOuterRaceWidth), paint);
    }

    /**
     * 测量曲线路径
     *
     * @param mPointList
     * @return
     */
    private PathMeasure measurePath(ArrayList<Point> mPointList) {
        //保存曲线路径
        Path mPath = new Path();
        //保存辅助线路径
        Path mAssistPath = new Path();
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;
        float previousPointY = Float.NaN;
        float currentPointX = Float.NaN;
        float currentPointY = Float.NaN;
        float nextPointX;
        float nextPointY;
        float lineSmoothness = 0.13f;

        final int lineSize = mPointList.size();
        for (int valueIndex = 0; valueIndex < lineSize; ++valueIndex) {
            if (Float.isNaN(currentPointX)) {
                Point point = mPointList.get(valueIndex);
                currentPointX = point.x;
                currentPointY = point.y;
            }
            if (Float.isNaN(previousPointX)) {
                //是否是第一个点
                if (valueIndex > 0) {
                    Point point = mPointList.get(valueIndex - 1);
                    previousPointX = point.x;
                    previousPointY = point.y;
                } else {
                    //是的话就用当前点表示上一个点
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }

            if (Float.isNaN(prePreviousPointX)) {
                //是否是前两个点
                if (valueIndex > 1) {
                    Point point = mPointList.get(valueIndex - 2);
                    prePreviousPointX = point.x;
                    prePreviousPointY = point.y;
                } else {
                    //是的话就用当前点表示上上个点
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }

            // 判断是不是最后一个点了
            if (valueIndex < lineSize - 1) {
                Point point = mPointList.get(valueIndex + 1);
                nextPointX = point.x;
                nextPointY = point.y;
            } else {
                //是的话就用当前点表示下一个点
                nextPointX = currentPointX;
                nextPointY = currentPointY;
            }

            if (valueIndex == 0) {
                // 将Path移动到开始点
                mPath.moveTo(currentPointX, currentPointY);
                mAssistPath.moveTo(currentPointX, currentPointY);
            } else {
                // 求出控制点坐标
                final float firstDiffX = (currentPointX - prePreviousPointX);
                final float firstDiffY = (currentPointY - prePreviousPointY);
                final float secondDiffX = (nextPointX - previousPointX);
                final float secondDiffY = (nextPointY - previousPointY);
                final float firstControlPointX;
                final float firstControlPointY;
                final float secondControlPointX;
                final float secondControlPointY;
                if (previousPointY == currentPointY) {
                    firstControlPointX = previousPointX;
                    firstControlPointY = previousPointY;
                    secondControlPointX = currentPointX;
                    secondControlPointY = currentPointY;
                } else {
                    firstControlPointX = previousPointX + (lineSmoothness * firstDiffX);
                    firstControlPointY = previousPointY + (lineSmoothness * firstDiffY);
                    secondControlPointX = currentPointX - (lineSmoothness * secondDiffX);
                    secondControlPointY = currentPointY - (lineSmoothness * secondDiffY);
                }
                //画出曲线
                mPath.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                        currentPointX, currentPointY);
                //将控制点保存到辅助路径上
                mAssistPath.lineTo(firstControlPointX, firstControlPointY);
                mAssistPath.lineTo(secondControlPointX, secondControlPointY);
                mAssistPath.lineTo(currentPointX, currentPointY);
            }

            // 更新值,
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
        }
        return new PathMeasure(mPath, false);
    }

}
