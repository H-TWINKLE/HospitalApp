package com.gy.hospital.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.gy.hospital.R;

import org.xutils.x;

public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = "BaseActivity";

    public abstract void initData();

    public abstract void initView();

    protected Toolbar baseToolBar;

    private String toolBarTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initData();

        setBaseToolBar();

        initView();


    }

    protected void startActivity(Class c) {
        startActivity(new Intent(this, c));
    }

    protected void setToastString(String text) {
        runOnUiThread(() -> Toast.makeText(this, text, Toast.LENGTH_SHORT).show());
    }

    protected <T> boolean vailT_Is_Not_Null(T t) {
        return t != null;
    }

    public String getToolBarTitle() {
        return toolBarTitle;
    }

    public void setToolBarTitle(String toolBarTitle) {
        this.toolBarTitle = toolBarTitle;
    }

    private void setBaseToolBar() {

        if (TextUtils.isEmpty(getToolBarTitle()))
            return;

        baseToolBar = findViewById(R.id.base_tool_bar);
        baseToolBar.setTitle(getToolBarTitle());
        baseToolBar.setNavigationOnClickListener((e) -> {
            this.finish();
        });


    }

    public void onFailDefaultToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
