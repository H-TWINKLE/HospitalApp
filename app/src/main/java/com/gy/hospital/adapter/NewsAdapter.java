package com.gy.hospital.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.post;
import com.gy.hospital.utils.Utils;
import com.loopj.android.image.SmartImageView;

import org.xutils.x;

import java.util.List;

public class NewsAdapter extends BaseQuickAdapter<post, BaseViewHolder> {
    public NewsAdapter(int layoutResId, @Nullable List<post> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, post item) {

            SmartImageView s = helper.getView(R.id.notice_siv_pic);

            x.image().bind(s, item.getAuthor().getHeaderPic(), Utils.INSTANCE.getCirImageOptions);

            helper.setText(R.id.notice_tv_name, TextUtils.isEmpty(item.getAuthor().getNickname()) ? "" : item.getAuthor().getNickname());
            helper.setText(R.id.notice_tv_date, item.getCreatedAt());
            helper.setText(R.id.opinion_tv_title, TextUtils.isEmpty(item.getTitle()) ? "" : item.getTitle());
            helper.setText(R.id.opinion_tv_content, TextUtils.isEmpty(item.getContent()) ? "" : item.getContent());



    }
}
