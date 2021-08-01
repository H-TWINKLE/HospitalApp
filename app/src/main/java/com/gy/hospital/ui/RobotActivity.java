package com.gy.hospital.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.gy.hospital.R;
import com.gy.hospital.adapter.RobotAdapter;
import com.gy.hospital.base.BaseActivity;
import com.gy.hospital.entity.Message;
import com.gy.hospital.entity.User;
import com.gy.hospital.utils.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.bmob.v3.BmobUser;

@ContentView(R.layout.activity_robot)
public class RobotActivity extends BaseActivity implements Callback.CommonCallback<String> {


    @ViewInject(R.id.robot_rv)
    RecyclerView robot_rv;

    @ViewInject(R.id.robot_et)
    EditText robot_et;

    @Event(R.id.robot_send)
    private void onClick(View v) {

        String text = robot_et.getText().toString();

        if (TextUtils.isEmpty(text)) {
            setToastString("请输入信息");
            return;
        }

        Message s = new Message(BmobUser.getCurrentUser(User.class).getHeaderPic(), Message.AS_USER, text);

        adapter.addData(s);

        sendMessageToRobot(text);

    }

    RobotAdapter adapter;

    private List<Message> list;

    @Override
    public void initData() {
        setToolBarTitle("智能回复");

        adapter = new RobotAdapter(list);

        robot_rv.setLayoutManager(new LinearLayoutManager(this));
        robot_rv.setAdapter(adapter);

        Message s = new Message(null, Message.AS_ROBOT, "你好，请问有什么可以帮助您的？");

        adapter.addData(s);


    }

    @Override
    public void initView() {

    }

    public void sendMessageToRobot(String message) {


        RequestParams params = new RequestParams(Constants.ROBOT_URL);
        params.addParameter("info", message);
        x.http().get(params, this);

    }


    @Override
    public void onSuccess(String result) {
        JSONObject json = JSONObject.parseObject(result);

        Message s = new Message(null, Message.AS_ROBOT, json.getString("text"));
        adapter.addData(s);

        robot_et.getText().clear();

        robot_rv.scrollToPosition(adapter.getData().size());

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        setToastString(ex.getLocalizedMessage());
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
