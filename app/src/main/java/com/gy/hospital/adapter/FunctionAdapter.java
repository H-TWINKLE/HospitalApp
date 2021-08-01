package com.gy.hospital.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.Function;

import java.util.List;

public class FunctionAdapter extends BaseQuickAdapter<Function, BaseViewHolder> {


    public FunctionAdapter(int layoutResId, @Nullable List<Function> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Function item) {

        helper.setText(R.id.item_func_tv, item.getName());
        helper.setImageResource(R.id.item_func_iv, item.getIcon());
    }


}
