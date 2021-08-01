package com.gy.hospital.ui;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gy.hospital.R;
import com.gy.hospital.adapter.InfoAdapter;
import com.gy.hospital.base.BaseRefreshActivity;
import com.gy.hospital.entity.Info;
import com.gy.hospital.entity.User;
import com.gy.hospital.ui.fragment.FunctionFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

@ContentView(R.layout.activity_information)
public class InfoActivity extends BaseRefreshActivity<Info, InfoAdapter> implements com.gy.hospital.net.Bmob.bmobOnGetList<User> {

    @Override
    public void getMethod() {

    }

    @Event(R.id.tv_info_logout)
    private void onLogOutClick(View v) {

        quit();

    }

    @Override
    public void initData() {
        list = new ArrayList<>();


        com.gy.hospital.net.Bmob.INSTANCE.setBmobOnGetList(this);

        setToolBarTitle("个人信息");

        isNeedLoadMore = false;

        adapter = new InfoAdapter(R.layout.item_info, list);
        adapter.setOnItemClickListener((adapter, view, position) -> {

            if (position == 0) {
                return;
            }
            if (position == 3) {
                updateSex();
            } else {
                updateVal(position);
            }
        });

        addValue();


    }

    private void updateVal(int i) {

        EditText e = new EditText(this);
        e.setText(list.get(i).getVal());

        new AlertDialog
                .Builder(this)
                .setTitle(list.get(i).getTitle())
                .setView(e)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (dialogInterface, i1) -> {
                    String val = e.getText().toString().trim();
                    User u = new User();
                    switch (i) {
                        case 1:
                            if (val.length() != 18) {
                                Toast.makeText(this, "请填写18位身份证号码！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            u.setIdentity(e.getText().toString().trim());
                            break;
                        case 2:
                            if (TextUtils.isEmpty(val)) {
                                Toast.makeText(this, "请填写昵称！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            u.setNickname(e.getText().toString().trim());
                            break;
                        case 4:
                            u.setAuto(e.getText().toString().trim());
                            break;
                    }

                    com.gy.hospital.net.Bmob.INSTANCE.BmobModifyUserInfo(u);

                }).create().show();
    }

    private void updateSex() {

        AlertDialog a = new AlertDialog
                .Builder(this)
                .setTitle(list.get(3).getTitle())
                .setSingleChoiceItems(new String[]{"男", "女"}, 0, (dialogInterface, i1) -> {
                    User u = new User();
                    u.setSex(i1);
                    com.gy.hospital.net.Bmob.INSTANCE.BmobModifyUserInfo(u);
                })
                .create();
        a.show();
    }


    private void addValue() {

        User user = BmobUser.getCurrentUser(User.class);

       /* if (doctor.getPlate() == Constants._AS_DOCTOR) {
            list.add(new Info("部门", doctor.getDepartment().getName()));
            list.add(new Info("热门程度", doctor.getHot() == null ? "0" : String.valueOf(doctor.getHot())));
            list.add(new Info("简介", doctor.getAuto() == null ? "这个医生很懒，什么都没有留下" : doctor.getAuto()));
            list.add(new Info("评价", doctor.getEvalList() == null ? "还没有人评价过这个医生呢" : String.valueOf(doctor.getEvalList().size())));
        }*/

        if (!list.isEmpty()) {
            list.clear();
        }

        list.add(new Info("账号", user.getUsername()));
        list.add(new Info("身份证号码", user.getIdentity() + ""));
        list.add(new Info("昵称", user.getNickname() + ""));
        list.add(new Info("性别", user.getSex() == null ? "未知" : (user.getSex() == 0 ? "男" : "女")));
        list.add(new Info("简介", user.getAuto() == null ? "这个人很懒，什么都没有留下" : user.getAuto()));

        adapter.notifyDataSetChanged();

    }


    @Override
    public void onbmobOnGetListSuccess(User user) {
        com.gy.hospital.net.Bmob.INSTANCE.BmobFetchUserInfo(this);
        addValue();
        sendVal();

    }

    @Override
    public void onbmobOnGetListFailure(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void sendVal() {

        Intent intent = new Intent();
        intent.setAction(FunctionFragment.UPDATE);
        sendBroadcast(intent);

    }

    private void quit() {
        new AlertDialog
                .Builder(this)
                .setTitle("确定注销账户")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (dialogInterface, i1) -> {
                    BmobUser.logOut();
                    System.exit(0);
                }).show();
    }

}