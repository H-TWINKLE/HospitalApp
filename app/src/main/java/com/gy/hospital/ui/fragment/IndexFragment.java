package com.gy.hospital.ui.fragment;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gy.hospital.R;
import com.gy.hospital.adapter.DeptAdapter;
import com.gy.hospital.adapter.IndexAdapter;
import com.gy.hospital.base.BaseRefreshFragment;
import com.gy.hospital.entity.DeptBean;
import com.gy.hospital.entity.department;
import com.gy.hospital.entity.doctor;
import com.gy.hospital.entity.hospital;
import com.gy.hospital.ui.DepartmentActivity;
import com.gy.hospital.ui.DoctorActivity;
import com.gy.hospital.utils.Constants;
import com.gy.hospital.utils.Utils;
import com.loopj.android.image.SmartImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

@ContentView(R.layout.fragment_index)
public class IndexFragment extends BaseRefreshFragment<doctor, IndexAdapter> {


    SmartImageView item_index_hospital_siv_logo;

    TextView item_index_hospital_tv_time, item_index_hospital_tv_name, item_index_hospital_tv_place, item_index_hospital_tv_content;

    RecyclerView header_rv2;

    private DeptAdapter deptAdapter;

    public IndexFragment() {
    }

    @Override
    public void getMethod() {
        getDoctorInfo();
    }

    @Override
    public void initData() {

        adapter = new IndexAdapter(R.layout.item_doctor_plate, list);

        adapter.addHeaderView(initHeaderView1(), 0);
        adapter.addHeaderView(initHeaderView2(), 1);
        adapter.setOnItemClickListener((adapter, view, position) -> {

            Intent i = new Intent(getActivity(), DoctorActivity.class);
            i.putExtra("id", this.adapter.getData().get(position).getObjectId());
            startActivity(i);
        });

        isNeedLoadMore = true;

        getHospitalInfo();
        getDepartmentInfo();

        getMethod();


    }


    private View initHeaderView1() {


        View v = View.inflate(getContext(), R.layout.item_index_hospital, null);

        item_index_hospital_siv_logo = v.findViewById(R.id.item_index_hospital_siv_logo);
        item_index_hospital_tv_name = v.findViewById(R.id.item_index_hospital_tv_name);
        item_index_hospital_tv_time = v.findViewById(R.id.item_index_hospital_tv_time);
        item_index_hospital_tv_place = v.findViewById(R.id.item_index_hospital_tv_place);
        item_index_hospital_tv_content = v.findViewById(R.id.item_index_hospital_tv_content);

        return v;


    }


    private View initHeaderView2() {

        View v = View.inflate(getContext(), R.layout.base_recycler_view, null);

        header_rv2 = v.findViewById(R.id.base_recycler_view_in);
        header_rv2.setLayoutManager(new GridLayoutManager(getContext(), 4));

        deptAdapter = new DeptAdapter(R.layout.item_header_dept, new ArrayList<>());
        deptAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent i = new Intent(getActivity(), DepartmentActivity.class);
            i.putExtra("id", this.deptAdapter.getData().get(position).getObjectId());
            startActivity(i);
        });
        header_rv2.setAdapter(deptAdapter);

        return v;

    }


    private void setHeaderView1(List<hospital> list) {
        if (list == null || list.isEmpty())
            return;
        hospital h = list.get(0);
        x.image().bind(item_index_hospital_siv_logo, h.getPic() == null ? "" : list.get(0).getPic(), Utils.INSTANCE.getCirImageOptions);
        item_index_hospital_tv_name.setText(TextUtils.isEmpty(h.getName()) ? "" : h.getName());
        item_index_hospital_tv_time.setText(TextUtils.isEmpty(h.getName()) ? "" : h.getCreatedAt());
        item_index_hospital_tv_place.setText(TextUtils.isEmpty(h.getName()) ? "" : h.getPlace());
        item_index_hospital_tv_content.setText(TextUtils.isEmpty(h.getName()) ? "" : h.getAuto());
    }

    private void setHeaderView2(List<department> list) {

        int[] i = Constants.DEPARTMENT_ICON;
        int x = 0;

        List<DeptBean> deptBeans = new ArrayList<>();

        for (department d : list) {
            deptBeans.add(new DeptBean(d.getName() == null ? d.getName() : d.getName(), i[x], d.getObjectId()));
            x++;
            if (x > 9)
                x = 0;
        }

        deptAdapter.setNewData(deptBeans);
    }

    private void setList(List<doctor> list) {
        onSuccessGetData(list);
    }

    private void getHospitalInfo() {

        BmobQuery<hospital> h = new BmobQuery<>();
        h.findObjects(new FindListener<hospital>() {
            @Override
            public void done(List<hospital> list, BmobException e) {
                if (e == null) {
                    if (list != null && !list.isEmpty()) {
                        setHeaderView1(list);
                    }
                } else {
                    setToastString(e.getLocalizedMessage());
                }
            }
        });
    }

    private void getDepartmentInfo() {

        BmobQuery<department> d = new BmobQuery<>();
        d.addWhereEqualTo("hospital", Constants.DEFAULT_HOSPITAL_NAME);
        d.findObjects(new FindListener<department>() {
            @Override
            public void done(List<department> list, BmobException e) {
                if (e == null) {
                    if (list != null && !list.isEmpty()) {
                        setHeaderView2(list);
                    }
                } else {
                    setToastString(e.getLocalizedMessage());
                }
            }
        });
    }

    private void getDoctorInfo() {
        BmobQuery<doctor> d = new BmobQuery<>();
        d.addWhereEqualTo("plate", Constants._AS_GOOD_DOCTOR);
        d.findObjects(new FindListener<doctor>() {
            @Override
            public void done(List<doctor> list, BmobException e) {
                if (e == null) {
                    if (list != null && !list.isEmpty()) {
                        setList(list);
                    }
                } else {
                    setToastString(e.getLocalizedMessage());
                }
            }
        });
    }


}
