package com.gy.hospital.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.DeptBean;

import java.util.List;

public class DeptAdapter extends BaseQuickAdapter<DeptBean, BaseViewHolder> {
    public DeptAdapter(int layoutResId, @Nullable List<DeptBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeptBean item) {
        helper.setText(R.id.index_header_tv_dept_name, item.getTitle());
        helper.setImageResource(R.id.index_header_siv_dept, item.getIcon());
    }
}
