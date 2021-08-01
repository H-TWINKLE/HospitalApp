package com.gy.hospital.ui;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gy.hospital.R;
import com.gy.hospital.base.BaseActivity;
import com.gy.hospital.entity.User;
import com.gy.hospital.net.Bmob;
import com.mob.MobSDK;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Objects;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity implements Bmob.bmobOnGetList<User> {

    private boolean isVail = false;

    @ViewInject(R.id.te_name)
    private TextInputEditText te_name;

    @ViewInject(R.id.te_pass)
    private TextInputEditText te_pass;

    @ViewInject(R.id.te_re_pass)
    private TextInputEditText te_re_pass;

    @ViewInject(R.id.te_idcard)
    private TextInputEditText te_idcard;

    @ViewInject(R.id.te_tel)
    private TextInputEditText te_tel;

    @ViewInject(R.id.et_register_code)
    private EditText et_register_code;

    @ViewInject(R.id.bt_register_vail_code)
    private Button bt_register_vail_code;

    @ViewInject(R.id.bt_register_ok)
    private Button bt_register_ok;

    @ViewInject(R.id.ll_register_tel)
    private LinearLayout ll_register_tel;

    @Event(R.id.bt_register_vail_code)
    private void onVailCodeClick(View v) {

        String tel = Objects.requireNonNull(te_tel.getText()).toString().trim();

        if (vailT_Is_Not_Null(tel)) {
            SMSSDK.getVerificationCode("86", tel);
        }

    }

    @Event(R.id.bt_register_ok)
    private void onbuttonRegisterClick(View v) {

        String tel = Objects.requireNonNull(te_tel.getText()).toString().trim();

        String code = Objects.requireNonNull(et_register_code.getText()).toString().trim();

        String name = Objects.requireNonNull(te_name.getText()).toString().trim();

        String repass = Objects.requireNonNull(te_re_pass.getText()).toString().trim();

        String pass = Objects.requireNonNull(te_pass.getText()).toString().trim();

        String idcard = Objects.requireNonNull(te_idcard.getText()).toString().trim();

        if (tel.length() != 11) {
            te_tel.setError("手机号码格式不正确");
            te_tel.setFocusable(true);
            return;
        }

        if (code.length() != 4) {
            et_register_code.setError("验证码格式不正确");
            et_register_code.setFocusable(true);
            return;
        }

        if (TextUtils.isEmpty(name)) {
            te_name.setError("请输入姓名");
            te_name.setFocusable(true);
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            te_pass.setError("请输入密码");
            te_pass.setFocusable(true);
            return;
        }

        if (TextUtils.isEmpty(idcard) || idcard.length() != 18) {
            te_idcard.setError("身份证格式不正确");
            te_idcard.setFocusable(true);
            return;
        }

        if (!pass.equals(repass)) {
            te_re_pass.setError("两次密码不同，请重新输入");
            te_re_pass.setFocusable(true);
            return;
        }

        if (!isVail) {
            if (vailT_Is_Not_Null(tel)) {
                SMSSDK.submitVerificationCode("86", tel, code);
            }
        } else {

            User u = new User();
            u.setUsername(tel);
            u.setPassword(pass);
            u.setPass(pass);
            u.setNickname(name);
            u.setIdentity(idcard);

            Bmob.INSTANCE.toRegister(u);
        }


    }


    @Override
    public void initData() {

        setToolBarTitle("注册");
        Bmob.INSTANCE.setBmobOnGetList(this);

    }

    @Override
    public void initView() {

        ll_register_tel.setVisibility(View.GONE);
        te_tel.addTextChangedListener(textWatcher);
        bt_register_vail_code.setText("发送验证码");

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            ll_register_tel.setVisibility(View.GONE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() == 0) {
                ll_register_tel.setVisibility(View.GONE);
            } else {
                ll_register_tel.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();   //用完回调要注销掉，否则可能会出现内存泄露
    }

    @Override
    protected void onStart() {
        super.onStart();
        MobSDK.init(this);
        SMSSDK.registerEventHandler(eventHandler);
    }

    private EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), msg1 -> {
                int event1 = msg1.arg1;
                int result1 = msg1.arg2;
                Object data1 = msg1.obj;
                if (event1 == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    if (result1 == SMSSDK.RESULT_COMPLETE) {
                        // TODO 处理成功得到验证码的结果
                        // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        setToastString("验证码发送成功！");

                    } else {
                        // TODO 处理错误的结果
                        setToastString(((Throwable) data1).getLocalizedMessage());
                    }
                } else if (event1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    if (result1 == SMSSDK.RESULT_COMPLETE) {
                        // TODO 处理验证码验证通过的结果
                        setToastString("验证码验证成功，请点击 >>注册<< 按钮注册吧！");
                        runOnUiThread(() -> bt_register_ok.setText("注册"));
                        isVail = true;
                    } else {
                        // TODO 处理错误的结果
                        ((Throwable) data1).printStackTrace();

                        setToastString(((Throwable) data1).getLocalizedMessage());

                    }
                }
                // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                return false;
            }).sendMessage(msg);
        }
    };


    @Override
    public void onbmobOnGetListSuccess(User user) {
        setToastString("注册成功");
        finish();
        this.setResult(LoginActivity.INTENT_REGISTER);
        startActivity(MainActivity.class);
    }

    @Override
    public void onbmobOnGetListFailure(String text) {
        setToastString(text);
    }
}
