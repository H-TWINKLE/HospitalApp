package com.gy.hospital.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gy.hospital.R;
import com.gy.hospital.base.BaseActivity;
import com.gy.hospital.entity.User;
import com.gy.hospital.net.Bmob;
import com.mob.MobSDK;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@ContentView(R.layout.activity_forget_pass)
public class ForgetPassActivity extends BaseActivity implements Bmob.bmobOnGetList<User> {

    private String tel;

    private int time = 0;

    @ViewInject(R.id.acvw_fps_num)
    private EditText acvw_fps_num;

    @ViewInject(R.id.acvw_fps_code)
    private EditText acvw_fps_code;

    @ViewInject(R.id.btn_fps_ok)
    private Button btn_fps_ok;

    @Event(R.id.btn_fps_ok)
    private void onForgetPassClick(View v) {

        String tel = acvw_fps_num.getText().toString();

        String code = acvw_fps_code.getText().toString();

        if (TextUtils.isEmpty(tel) || tel.length() != 11) {
            acvw_fps_num.setError("请输入正确手机号码！");
            acvw_fps_num.setFocusable(true);
            return;
        }

        if (TextUtils.isEmpty(this.tel)) {
            SMSSDK.getVerificationCode("86", tel);
        } else {
            if (TextUtils.isEmpty(code)) {
                acvw_fps_code.setError("请输入验证码！");
                acvw_fps_code.setFocusable(true);
                return;
            }
            SMSSDK.submitVerificationCode("86", tel, code);
        }


    }

    @Event(R.id.tvw_fps_retunmain)
    private void onReturnClick(View v) {
        finish();
    }


    @Override
    public void initData() {
        setToolBarTitle("忘记密码");

        Bmob.INSTANCE.setBmobOnGetList(this);
    }

    @Override
    public void initView() {

    }

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

                        runOnUiThread(() ->
                        {
                            acvw_fps_code.setVisibility(View.VISIBLE);
                            btn_fps_ok.setText("验证");
                        });

                        tel = acvw_fps_num.getText().toString();

                    } else {
                        // TODO 处理错误的结果
                        setToastString(((Throwable) data1).getLocalizedMessage());
                    }
                } else if (event1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    if (result1 == SMSSDK.RESULT_COMPLETE) {
                        // TODO 处理验证码验证通过的结果
                        setToastString("验证成功！");

                        Bmob.INSTANCE.findUserByTel(tel);

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

        if (user == null) {
            setToastString("该用户尚未注册！");

        } else {
            Intent i = new Intent(ForgetPassActivity.this, ModifyPassActivity.class);
            i.putExtra("user", user);
            startActivity(i);
            this.finish();
        }

    }

    @Override
    public void onbmobOnGetListFailure(String text) {
        setToastString("未找到用户");
    }
}
