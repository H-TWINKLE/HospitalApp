package com.gy.hospital.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.seek;
import com.gy.hospital.utils.Utils;
import com.loopj.android.image.SmartImageView;

import org.xutils.x;

import java.util.List;

public class SeekAdapter extends BaseQuickAdapter<seek, BaseViewHolder> {
    public SeekAdapter(int layoutResId, @Nullable List<seek> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, seek item) {

            SmartImageView s = helper.getView(R.id.seek_siv_logo);
            x.image().bind(s, item.getDoctor().getHeaderPic(), Utils.INSTANCE.getCirImageOptionsAsUser);

            helper.setText(R.id.seek_tv_name, TextUtils.isEmpty(item.getDoctor().getNickname()) ? "" : item.getDoctor().getNickname());
            helper.setText(R.id.seek_tv_time, item.getCreatedAt());
            helper.setText(R.id.seek_tv_progress, TextUtils.isEmpty(item.getProgress()) ? "" : item.getProgress());
            helper.setText(R.id.seek_tv_content, TextUtils.isEmpty(item.getContent()) ? "" : item.getContent());
            helper.setText(R.id.seek_tv_eval, TextUtils.isEmpty(item.getEval()) ? "" : item.getEval());

            helper.addOnClickListener(R.id.seek_fab_replay);

    }
}
