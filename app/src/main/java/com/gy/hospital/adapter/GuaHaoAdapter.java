package com.gy.hospital.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.register;
import com.gy.hospital.utils.Utils;
import com.loopj.android.image.SmartImageView;

import org.xutils.x;

import java.util.List;

public class GuaHaoAdapter extends BaseQuickAdapter<register, BaseViewHolder> {
    public GuaHaoAdapter(int layoutResId, @Nullable List<register> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, register item) {

        SmartImageView s = helper.getView(R.id.item_guahao_siv);

        x.image().bind(s,  item.getUser().getHeaderPic(), Utils.INSTANCE.getCirImageOptions);

        helper.setText(R.id.item_huahao_tv_progress, TextUtils.isEmpty(item.getProgress()) ? "正在进行" : item.getProgress());

        helper.setText(R.id.item_huahao_tv_doctor_name, TextUtils.isEmpty(item.getDepartment().getName()) ? "默认部门" : item.getDepartment().getName());


    }
}
