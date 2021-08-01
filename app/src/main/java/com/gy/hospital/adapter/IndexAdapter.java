package com.gy.hospital.adapter;


import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.doctor;
import com.gy.hospital.utils.Constants;
import com.gy.hospital.utils.Utils;
import com.loopj.android.image.SmartImageView;

import org.xutils.x;

import java.util.List;

public class IndexAdapter extends BaseQuickAdapter<doctor, BaseViewHolder> {


    public IndexAdapter(int layoutResId, List<doctor> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, doctor item) {

        SmartImageView s = helper.getView(R.id.siv_icon);

        x.image().bind(s, item.getHeaderPic(), Utils.INSTANCE.getCirImageOptionsAsUser);

        helper.setText(R.id.tv_doctor_name, TextUtils.isEmpty(item.getNickname()) ? "" : item.getNickname());

        helper.setText(R.id.tv_doctor_auto, TextUtils.isEmpty(item.getAuto()) ? "这个人很懒，什么都没有留下。" : item.getAuto());

        TextView textView = helper.getView(R.id.tv_doctor_good);

        if (item.getPlate() == Constants._AS_GOOD_DOCTOR) {
            textView.setVisibility(View.VISIBLE);
        }


    }

}
