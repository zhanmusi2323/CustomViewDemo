package com.example.customviewdemo.view;

import android.content.Context;
import android.support.annotation.NonNull;

public class BaseChartView<V extends BaseChartView> extends AbsChartView {
    public BaseChartView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(changeSize(mTemplateHeight), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public V setBacgroundColor(int color) {
        mBackgroundColor = color;
        return (V) this;
    }
}
