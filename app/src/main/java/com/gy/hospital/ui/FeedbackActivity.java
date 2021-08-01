package com.gy.hospital.ui;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gy.hospital.R;
import com.gy.hospital.base.BaseActivity;
import com.gy.hospital.entity.User;
import com.gy.hospital.entity.opinion;
import com.gy.hospital.net.Bmob;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.bmob.v3.BmobUser;

@ContentView(R.layout.activity_feedback)
public class FeedbackActivity extends BaseActivity implements Bmob.bmobOnGetList<String> {


    @ViewInject(R.id.btn_fps_ok)
    private Button btn_fps_ok;

    @ViewInject(R.id.et_main_text)
    private EditText et_main_text;

    @Event((R.id.btn_fps_ok))
    private void onClick(View view) {

        String text = et_main_text.getText().toString();

        if (TextUtils.isEmpty(text)) {
            setToastString("请填写内容");
            return;
        }

        opinion o = new opinion();
        o.setUser(BmobUser.getCurrentUser(User.class));
        o.setContent(text);

        Bmob.INSTANCE.setBmobOnGetList(this);

        Bmob.INSTANCE.toSave(o);

    }


    @Override
    public void initData() {
        setToolBarTitle("意见反馈");
    }

    @Override
    public void initView() {

    }


    @Override
    public void onbmobOnGetListSuccess(String s) {

        setToastString("操作成功");

        Intent i = new Intent();
        i.putExtra("flag", true);
        FeedbackActivity.this.setResult(1000, i);
        FeedbackActivity.this.finish();
    }

    @Override
    public void onbmobOnGetListFailure(String text) {
        onFailDefaultToast(text);
    }
}
