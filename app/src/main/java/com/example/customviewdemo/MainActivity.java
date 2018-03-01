package com.example.customviewdemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.example.customviewdemo.fragment.CustomViewFragment;

public class MainActivity extends BaseActivity {
    private LinearLayout vContainer;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private CustomViewFragment fragment;

    @Override
    public int setLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        vContainer = findViewById(R.id.lin_container);
        fragment = new CustomViewFragment();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.lin_container, fragment);
        transaction.commit();
    }
}
