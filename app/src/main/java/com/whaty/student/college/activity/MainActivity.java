package com.whaty.student.college.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.whaty.student.college.R;
import com.whaty.student.college.base.BaseActivity;
import com.whaty.student.college.fragment.MainFragment;
import com.whaty.student.college.fragment.MenuFragment;
import com.whaty.student.college.slidingmenu.SlidingMenu;


public class MainActivity extends BaseActivity {
    private SlidingMenu slidingMenu;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButterKnife(this);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.layout_main, new MainFragment(), "MainFragment")
                .commit();
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.LEFT);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        slidingMenu.setBehindWidth(width / 5*3);
        // 设置是否淡入淡出
        slidingMenu.setFadeEnabled(true);
        // 设置淡入淡出的值，只在setFadeEnabled设置为true时有效
        slidingMenu.setFadeDegree(0.5f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.menu);
        fragmentManager.beginTransaction()
                .replace(R.id.menu_frame, new MenuFragment(), "MenuFragment")
                .commit();
    }
}
