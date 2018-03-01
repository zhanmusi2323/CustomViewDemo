package com.example.customviewdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

public abstract class BaseActivity extends FragmentActivity {
    /**
     *
     * */
    private LinearLayout vRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        vRootView = new LinearLayout(this);
        vRootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        vRootView.setOrientation(LinearLayout.VERTICAL);
        setContentView(vRootView);
        View titleView = getLayoutInflater().inflate(R.layout.title_layout, null);
        vRootView.addView(titleView);
        getLayoutInflater().inflate(setLayoutResource(), vRootView);
        initView();
    }

    public abstract int setLayoutResource();

    public abstract void initView();
}
