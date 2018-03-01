package com.example.customviewdemo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.customviewdemo.R;
import com.example.customviewdemo.view.AbsChartView;
import com.example.customviewdemo.view.BloodPressureChartView;

public class CustomViewFragment extends Fragment {
    private LinearLayout vRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ScrollView scrollView = new ScrollView(getActivity());
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        scrollView.setVerticalScrollBarEnabled(false);
        vRootView = new LinearLayout(getActivity());
        vRootView.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(vRootView);
        initView();
        return scrollView;
    }

    private void initView() {
        try {
            Log.e("tag", "22222222222");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 3; i++) {
            BloodPressureChartView chartView = new BloodPressureChartView(getActivity());
            chartView.setBacgroundColor(AbsChartView.GREED);
            addView(chartView);
        }
    }

    private void addView(View child) {
        ViewParent parent = child.getParent();
        if (parent != null) {
            Toast.makeText(getActivity(), "要报错了", Toast.LENGTH_LONG).show();
            return;
        } else {

        }
        if (vRootView.getChildCount() > 0) {
            View space = new View(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10);
            layoutParams.setMargins(0, 10, 0, 10);
            space.setLayoutParams(layoutParams);
            space.setBackgroundColor(getResources().getColor(R.color.gray_background));
            vRootView.addView(space);
        }
        vRootView.addView(child);
    }

}
