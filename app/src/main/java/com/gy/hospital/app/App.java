package com.gy.hospital.app;

import android.app.Application;

import com.gy.hospital.R;

import org.xutils.x;

import cn.bmob.v3.Bmob;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(false);
        Bmob.initialize(this, getResources().getString(R.string.BMOB_APP_KEY));


    }
}
