package com.gy.hospital.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.Info;

import java.util.List;

public class InfoAdapter extends BaseQuickAdapter<Info, BaseViewHolder> {
    public InfoAdapter(int layoutResId, List<Info> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Info item) {

        helper.setText(R.id.info_tv_title, item.getTitle());
        helper.setText(R.id.info_tv_value, item.getVal());

    }
}
