package com.gy.hospital.ui;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gy.hospital.R;
import com.gy.hospital.adapter.IndexAdapter;
import com.gy.hospital.base.BaseRefreshActivity;
import com.gy.hospital.entity.department;
import com.gy.hospital.entity.doctor;

import org.xutils.view.annotation.ContentView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

@ContentView(R.layout.activity_department)
public class DepartmentActivity extends BaseRefreshActivity<doctor, IndexAdapter> {

    private String departmentId;

    TextView item_dept_name, item_dept_place, item_dept_auto;

  /*  @Event(R.id.dept_fab)
    private void fabClick(View view) {

        register r = new register();
        r.setDepartment(new department(departmentId));
        r.setProgress("开始挂号");
        r.setUser(BmobUser.getCurrentUser(User.class));
        r.save();

        setToastString("挂号成功！");

    }*/

    private View initHeaderView1() {

        View view = View.inflate(DepartmentActivity.this, R.layout.item_department_info, null);
        item_dept_name = view.findViewById(R.id.item_dept_name);
        item_dept_place = view.findViewById(R.id.item_dept_place);
        item_dept_auto = view.findViewById(R.id.item_dept_auto);
        return view;

    }

    private void setHeaderView1(department d) {

        if (d == null)
            return;

        item_dept_name.setText(TextUtils.isEmpty(d.getName()) ? "" : d.getName());
        item_dept_place.setText(TextUtils.isEmpty(d.getPlace()) ? "" : d.getPlace());
        item_dept_auto.setText(TextUtils.isEmpty(d.getAuto()) ? "" : d.getAuto());

    }


    @Override
    public void initData() {
        setToolBarTitle("所属部门");

        //layoutManager = new GridLayoutManager(this, 3);

        adapter = new IndexAdapter(R.layout.item_doctor_plate, list);
        adapter.setHeaderView(initHeaderView1(), 0);
        adapter.setOnItemClickListener((adapter, view, position) -> {

            Intent i = new Intent(this, DoctorActivity.class);
            i.putExtra("id", this.adapter.getData().get(position).getObjectId());
            startActivity(i);

        });

        isNeedLoadMore = true;

        departmentId = getIntent().getStringExtra("id");

        if (!TextUtils.isEmpty(departmentId)) {
            getDepartment();
        } else {
            this.finish();
        }

        getMethod();


    }

    private void getUserAsDoctorInDepartment() {

        BmobQuery<doctor> q = new BmobQuery<>();
        q.addWhereEqualTo("department", new department(departmentId));
        q.order("-createdAt");
        q.setLimit(10);
        q.setSkip(pages * 10);
        q.include("user,department");
        q.findObjects(new FindListener<doctor>() {
            @Override
            public void done(List<doctor> list, BmobException e) {
                if (e == null) {
                    onSuccessGetData(list);
                } else {
                    onFailGetData(e.getLocalizedMessage());
                }
            }
        });
    }

    private void getDepartment() {

        BmobQuery<department> q = new BmobQuery<>();
        q.addWhereEqualTo("objectId", departmentId);
        q.include("department,hospital");
        q.findObjects(new FindListener<department>() {
            @Override
            public void done(List<department> list, BmobException e) {
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
        getUserAsDoctorInDepartment();
    }


}
