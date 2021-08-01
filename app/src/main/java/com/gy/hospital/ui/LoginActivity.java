package com.gy.hospital.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.gy.hospital.R;
import com.gy.hospital.base.BaseActivity;
import com.gy.hospital.entity.User;
import com.gy.hospital.net.Bmob;
import com.gy.hospital.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.bmob.v3.BmobUser;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements Bmob.bmobOnGetList<User> {

    public static final int INTENT_REGISTER = 10010;

    public static final int INTENT_FORGET_PASS = 10011;

    private User user;

    @ViewInject(R.id.et_login_account)
    private EditText et_login_account;

    @ViewInject(R.id.et_login_password)
    private EditText et_login_password;

    @Event((R.id.bt_login_toLogin))
    private void toLogin(View v) {
        if (vailUserNameAndPass()) {
            toLogin();
        } else {
            setToastString("请输入用户名或者密码");
        }

    }

    private void toLogin() {
        String username = et_login_account.getText().toString().trim().toLowerCase();
        String password = et_login_password.getText().toString().trim();

        Bmob.INSTANCE.toLogin(username, password);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_REGISTER) {
            this.finish();
        } else if (requestCode == INTENT_FORGET_PASS) {
            this.finish();
        }
    }

    @Event(R.id.tv_login_forget_ass)
    private void toForgetPass(View v) {

        //Intent i = new Intent(LoginActivity.this, ForgetPassActivity.class);
        //startActivityForResult(i, INTENT_FORGET_PASS);

        startActivity(ForgetPassActivity.class);

    }

    @Event(R.id.tv_login_register)
    private void toRegister(View v) {

        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(i, INTENT_REGISTER);

    }


    @Override
    public void initData() {

        if (BmobUser.getCurrentUser(User.class) != null) {
            startActivity(MainActivity.class);
            this.finish();
        }

        user = Utils.INSTANCE.getUserFromSharedPreferences(this);

        Bmob.INSTANCE.setBmobOnGetList(this);
    }

    @Override
    public void initView() {

        et_login_account.setText(String.format("%s", user.getUsername()));
        et_login_password.setText(String.format("%s", user.getPass()));

    }


    private boolean vailUserNameAndPass() {
        return !TextUtils.isEmpty(et_login_password.getText()) && !TextUtils.isEmpty(et_login_account.getText());
    }

    @Override
    public void onbmobOnGetListSuccess(User user) {

        if (vailT_Is_Not_Null(user)) {
            Utils.INSTANCE.rememberUserInfo(user, this);
            startActivity(WelActivity.class);
            this.finish();
        } else {
            setToastString("用户名或者密码错误！");
        }

    }

    @Override
    public void onbmobOnGetListFailure(String text) {
        setToastString(text);
    }
}
