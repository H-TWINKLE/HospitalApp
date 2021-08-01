package com.gy.hospital.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.gy.hospital.R;
import com.gy.hospital.adapter.FragmentAdapter;
import com.gy.hospital.base.BaseActivity;
import com.gy.hospital.ui.fragment.FunctionFragment;
import com.gy.hospital.ui.fragment.IndexFragment;
import com.gy.hospital.ui.fragment.NewsFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private List<Fragment> fragmentList;

    @ViewInject(R.id.main_vp)
    private ViewPager main_vp;

    @ViewInject(R.id.main_bnv)
    private BottomNavigationView main_bnv;


    @Override
    public void initData() {

        fragmentList = new ArrayList<>();
        fragmentList.add(new IndexFragment());
        fragmentList.add(new NewsFragment());
        fragmentList.add(new FunctionFragment());


    }

    @Override
    public void initView() {

        main_vp.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList));
        main_vp.addOnPageChangeListener(this);
        main_vp.setOffscreenPageLimit(2);
        main_bnv.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                main_bnv.setSelectedItemId(R.id.navigation_hospital);
                break;
            case 1:
                main_bnv.setSelectedItemId(R.id.navigation_news);
                break;
            case 2:
                main_bnv.setSelectedItemId(R.id.navigation_func);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_hospital:
                main_vp.setCurrentItem(0);
                return true;
            case R.id.navigation_news:
                main_vp.setCurrentItem(1);
                return true;
            case R.id.navigation_func:
                main_vp.setCurrentItem(2);
                return true;
        }

        return false;
    }
}