package com.gy.hospital.ui;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.gy.hospital.R;
import com.gy.hospital.base.BaseActivity;
import com.gy.hospital.entity.User;
import com.gy.hospital.net.Bmob;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_modofy_pass)
public class ModifyPassActivity extends BaseActivity implements Bmob.ModifyPassWithoutLoginListener {

    private User user;

    @ViewInject(R.id.modify_pass_et_pass)
    EditText modify_pass_et_pass;

    @ViewInject(R.id.modify_pass_et_re_pass)
    EditText modify_pass_et_re_pass;


    @Event(R.id.modify_pass_bt_ok)
    private void onClick(View view) {

        String pass = modify_pass_et_pass.getText().toString();

        String repass = modify_pass_et_re_pass.getText().toString();

        if (!vailPass(
                pass, repass))
            return;

        Bmob.INSTANCE.setModifyPassWithoutLoginListener(this);

        User u = new User();
        u.setObjectId(user.getObjectId());
        u.setPass(user.getPass());

        Bmob.INSTANCE.BmobModifyPassWithoutLogin(u, pass, ModifyPassActivity.this);


    }


    @Override
    public void initData() {

        setToolBarTitle("修改密码");

        user = (User) getIntent().getSerializableExtra("user");

        if (user == null) {
            this.finish();
        }


    }


    private boolean vailPass(String pass, String repass) {

        if (TextUtils.isEmpty(pass)) {
            modify_pass_et_pass.setError("请输入密码");
            modify_pass_et_pass.setFocusable(true);
            return false;
        }

        if (TextUtils.isEmpty(repass)) {
            modify_pass_et_re_pass.setError("请再次输入密码");
            modify_pass_et_re_pass.setFocusable(true);
            return false;
        }

        if (pass.length() < 6) {
            modify_pass_et_pass.setError("密码最低6位数");
            modify_pass_et_pass.setFocusable(true);
            return false;
        }

        if (!pass.equals(repass)) {
            modify_pass_et_pass.setError("两次密码不同，请重新输入");
            modify_pass_et_pass.setFocusable(true);
            return false;
        }

        return true;

    }


    @Override
    public void initView() {

    }

    @Override
    public void ModifyPassWithoutLoginSuccess() {
        setToastString("操作成功，请登录！");
        this.finish();
    }

    @Override
    public void ModifyPassWithoutLoginFailure(String text) {
        onFailDefaultToast(text);
    }
}
