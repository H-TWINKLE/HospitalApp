package com.gy.hospital.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.alibaba.fastjson.JSONObject;
import com.gy.hospital.R;
import com.gy.hospital.entity.User;

import org.xutils.DbManager;
import org.xutils.image.ImageOptions;

public enum Utils {

    INSTANCE;

    public static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("hospital.db")
            //设置数据库路径，默认存储在app的私有目录
            .setDbVersion(1)
            .setAllowTransaction(true)    //设置是否允许事务，默认true
            //设置数据库打开的监听
            .setDbOpenListener(db -> db.getDatabase().enableWriteAheadLogging())
            //设置数据库更新的监听
            .setDbUpgradeListener((db, oldVersion, newVersion) -> {
            })
            //设置表创建的监听
            .setTableCreateListener((db, table) -> {
            });

    public void rememberUserInfo(User u, Context c) {
        SharedPreferences s = c.getSharedPreferences(Constants._USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        editor.putString("username", u.getUsername());
        editor.putString("pass", u.getPass());
        editor.apply();
    }

    public User getUserFromSharedPreferences(Context c) {
        SharedPreferences s = c.getSharedPreferences(Constants._USER, Context.MODE_PRIVATE);
        User u = new User();
        u.setUsername(s.getString("username", ""));
        u.setPass(s.getString("pass", ""));
        return u;
    }

    public ImageOptions getCirImageOptions = new ImageOptions.Builder()
            .setLoadingDrawableId(R.drawable.logo)
            .setFailureDrawableId(R.drawable.logo)
            .setCircular(true)
            .build();

    public ImageOptions getCirImageOptionsAsRobot = new ImageOptions.Builder()
            .setLoadingDrawableId(R.drawable.robot)
            .setFailureDrawableId(R.drawable.robot)
            .setCircular(true)
            .build();

    public ImageOptions getCirImageOptionsAsUser = new ImageOptions.Builder()
            .setLoadingDrawableId(R.drawable.doctor)
            .setFailureDrawableId(R.drawable.doctor)
            .setCircular(true)
            .build();

    public String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI,
                new String[]{MediaStore.Images.ImageColumns.DATA},//
                null, null, null);
        if (cursor == null) result = contentURI.getPath();
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    public boolean verificationBmobMsgIsOk(String result) {

        if (result == null) return false;

        JSONObject js = JSONObject.parseObject(result);
        String msg = js.getString("msg");

        return msg != null && !"".equals(msg) && "ok".equals(msg);
    }


}
