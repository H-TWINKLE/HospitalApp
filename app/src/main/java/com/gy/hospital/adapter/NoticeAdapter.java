package com.gy.hospital.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.notice;
import com.gy.hospital.utils.Utils;
import com.loopj.android.image.SmartImageView;

import org.xutils.x;

import java.util.List;

public class NoticeAdapter extends BaseQuickAdapter<notice, BaseViewHolder> {
    public NoticeAdapter(int layoutResId, @Nullable List<notice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, notice item) {

        if (!TextUtils.isEmpty(item.getContent())) {
            SmartImageView s = helper.getView(R.id.notice_siv_pic);
            x.image().bind(s, TextUtils.isEmpty(item.getAuthor().getHeaderPic()) ? "" : item.getAuthor().getHeaderPic(), Utils.INSTANCE.getCirImageOptions);

            helper.setText(R.id.notice_tv_name, TextUtils.isEmpty(item.getAuthor().getNickname()) ? "" : item.getAuthor().getNickname());
            helper.setText(R.id.notice_tv_date, item.getCreatedAt());
            helper.setText(R.id.opinion_tv_title, TextUtils.isEmpty(item.getTitle()) ? "" : item.getTitle());
            helper.setText(R.id.opinion_tv_content, TextUtils.isEmpty(item.getContent()) ? "" : item.getContent());
        }


    }
}
