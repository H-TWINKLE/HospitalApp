package com.gy.hospital.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.opinion;

import java.util.List;

public class OpinionAdapter extends BaseQuickAdapter<opinion, BaseViewHolder> {
    public OpinionAdapter(int layoutResId, @Nullable List<opinion> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, opinion item) {

        helper.setText(R.id.opinion_tv_replay, TextUtils.isEmpty(item.getReplay()) ? "" : item.getReplay());

        helper.setText(R.id.opinion_tv_time, TextUtils.isEmpty(item.getCreatedAt()) ? "" : item.getCreatedAt());

        helper.setText(R.id.opinion_tv_content, TextUtils.isEmpty(item.getContent()) ? "" : item.getContent());

    }
}
