package com.gy.hospital.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.seek;
import com.gy.hospital.utils.Utils;
import com.loopj.android.image.SmartImageView;

import org.xutils.x;

import java.util.List;

public class DoctorSeekAdapter extends BaseQuickAdapter<seek, BaseViewHolder> {
    public DoctorSeekAdapter(int layoutResId, @Nullable List<seek> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, seek item) {

            SmartImageView s = helper.getView(R.id.item_doctor_eval_siv_logo);

            x.image().bind(s, item.getUser().getHeaderPic(), Utils.INSTANCE.getCirImageOptions);

            helper.setText(R.id.item_doctor_eval_tv_name, item.getUser().getNickname() == null ? "" : item.getUser().getNickname());

            helper.setText(R.id.item_doctor_eval_tv_content, item.getEval() == null ? "" : item.getEval());



    }
}
