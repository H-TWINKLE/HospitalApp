package com.gy.hospital.ui;

import android.Manifest;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.Button;

import com.gy.hospital.R;
import com.gy.hospital.base.BaseActivity;
import com.gy.hospital.entity.WelPic;
import com.gy.hospital.net.Bmob;
import com.gy.hospital.net.Bmob.bmobOnGetList;
import com.loopj.android.image.SmartImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Locale;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;

@ContentView(R.layout.activity_wel)
public class WelActivity extends BaseActivity implements bmobOnGetList<List<WelPic>> {

    @ViewInject(R.id.wel_cl)
    private CoordinatorLayout wel_cl;

    @ViewInject(R.id.wel_siv_background)
    private SmartImageView wel_siv_background;

    @ViewInject(R.id.wel_button)
    private Button wel_button;

    private final Handler handler = new Handler();

    @Event(R.id.wel_button)
    private void onButtonClick(View v) {
        c.onFinish();
    }

    private CountDownTimer c = new CountDownTimer(5000, 1000) {
        @Override
        public void onTick(long l) {
            setButtonText((int) l);
        }

        @Override
        public void onFinish() {
            c.cancel();
            startActivity(LoginActivity.class);
            finish();
        }
    };

    private void setButtonText(int s) {
        runOnUiThread(() -> wel_button.setText(String.format(Locale.CHINA, "跳过  %ds", s / 1000)));
    }


    @Override
    public void initData() {
        Bmob.INSTANCE.setBmobOnGetList(WelActivity.this);
        Bmob.INSTANCE.getWelcomePic(0);
    }

    @Override
    public void initView() {
        c.start();
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        handler.postDelayed(() -> wel_cl.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION), 500);

    }

    @Override
    public void onbmobOnGetListSuccess(List<WelPic> welPics) {

    }

    @Override
    public void onbmobOnGetListFailure(String text) {

    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onRun() {
    }


    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onRunShow(final PermissionRequest request) {
    }


}
