package com.gy.hospital.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gy.hospital.R;
import com.gy.hospital.adapter.DoctorSeekAdapter;
import com.gy.hospital.base.BaseRefreshActivity;
import com.gy.hospital.entity.User;
import com.gy.hospital.entity.seek;
import com.gy.hospital.utils.Utils;
import com.loopj.android.image.SmartImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

@ContentView(R.layout.activity_doctor)
public class DoctorActivity extends BaseRefreshActivity<seek, DoctorSeekAdapter> {

    SmartImageView item_doctor_siv_logo;

    TextView item_doctor_tv_name, item_doctor_dept, item_doctor_auto;

    @Event(R.id.doctor_fab)
    private void fabClick(View v) {

        seek s = new seek();

        s.setUser(BmobUser.getCurrentUser(User.class));
        s.setDoctor(new User(objectId));
        s.setProgress("注册就诊");
        s.setContent("");
        s.setEval("");
        s.save();

        setToastString("就诊成功！");
    }

    @Event(R.id.doctor_eval)
    private void evalClick(View view){
        setToastString("查看评价");
        getMethod();
    }




    private String objectId;

    @Override
    public void initData() {

        setToolBarTitle("医生详情");

        adapter = new DoctorSeekAdapter(R.layout.item_doctor_eval, list);
        adapter.addHeaderView(initHeaderView1(), 0);

        isNeedLoadMore = true;

        objectId = getIntent().getStringExtra("id");

        if (!TextUtils.isEmpty(objectId)) {
            getDoctor();
        } else {
            this.finish();
        }

        //getMethod();


    }

    private View initHeaderView1() {

        View view = View.inflate(DoctorActivity.this, R.layout.item_doctor_info, null);

        item_doctor_siv_logo = view.findViewById(R.id.item_doctor_siv_logo);
        item_doctor_tv_name = view.findViewById(R.id.item_doctor_tv_name);
        item_doctor_dept = view.findViewById(R.id.item_doctor_dept);
        item_doctor_auto = view.findViewById(R.id.item_doctor_auto);
        return view;

    }


    private void setHeaderView1(User user) {

        if (user == null)
            return;

        x.image().bind(item_doctor_siv_logo, user.getHeaderPic() == null ? "" : user.getHeaderPic(), Utils.INSTANCE.getCirImageOptionsAsUser);
        item_doctor_tv_name.setText(TextUtils.isEmpty(user.getNickname()) ? "" : user.getNickname());
        item_doctor_dept.setText(TextUtils.isEmpty(user.getDepartment().getName()) ? "" : user.getDepartment().getName());
        item_doctor_auto.setText(TextUtils.isEmpty(user.getAuto()) ? "" : user.getAuto());

    }


    public void getEval() {

        BmobQuery<seek> q = new BmobQuery<>();
        q.addWhereEqualTo("doctor", new User(objectId));
        q.order("-createdAt");
        q.setLimit(10);
        q.setSkip(pages * 10);
        q.include("user,doctor");
        q.addWhereNotEqualTo("eval", "");
        q.findObjects(new FindListener<seek>() {
            @Override
            public void done(List<seek> list, BmobException e) {
                if (e == null) {


                   /* Iterator<seek> iterator = list.iterator();

                    while (iterator.hasNext()){
                        seek s = iterator.next();
                        if(TextUtils.isEmpty(s.getEval())){
                            iterator.remove();
                        }
                    }*/

                    onSuccessGetData(list);
                } else {
                    onFailGetData(e.getLocalizedMessage());
                }
            }
        });

    }

    private void getDoctor() {

        BmobQuery<User> q = new BmobQuery<>();
        q.addWhereEqualTo("objectId", objectId);
        q.include("department,hospital");
        q.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    setHeaderView1(list.get(0));
                } else {
                    setToastString(e.getLocalizedMessage());
                }
            }
        });

    }


    @Override
    public void getMethod() {
        getEval();
    }
}
