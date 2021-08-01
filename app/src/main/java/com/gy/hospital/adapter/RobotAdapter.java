package com.gy.hospital.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.Message;
import com.gy.hospital.utils.Utils;
import com.loopj.android.image.SmartImageView;

import org.xutils.x;

import java.util.List;

public class RobotAdapter extends BaseMultiItemQuickAdapter<Message, BaseViewHolder> {

    public RobotAdapter(List<Message> data) {
        super(data);
        addItemType(Message.AS_ROBOT, R.layout.item_robot_left_answer);
        addItemType(Message.AS_USER, R.layout.item_robot_right_user);
    }


    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        switch (helper.getItemViewType()) {
            case Message.AS_ROBOT: {
                SmartImageView s = helper.getView(R.id.item_left_siv);
                x.image().bind(s, item.getHead(), Utils.INSTANCE.getCirImageOptionsAsRobot);
                helper.setText(R.id.item_left_content, item.getContent() + "");
            }
            break;
            case Message.AS_USER: {
                SmartImageView s = helper.getView(R.id.item_right_siv);
                x.image().bind(s, item.getHead(), Utils.INSTANCE.getCirImageOptions);
                helper.setText(R.id.item_right_content, item.getContent() + "");
            }
            break;

        }
    }
}
