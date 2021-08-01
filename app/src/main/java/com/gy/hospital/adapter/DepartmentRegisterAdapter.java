package com.gy.hospital.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;
import com.gy.hospital.entity.User;
import com.gy.hospital.entity.register;
import com.gy.hospital.utils.Utils;
import com.loopj.android.image.SmartImageView;

import org.xutils.x;

import java.util.List;

@Deprecated
public class DepartmentRegisterAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public DepartmentRegisterAdapter(int layoutResId, @Nullable List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {

      /*  SmartImageView s = helper.getView(R.id.siv_icon);

        x.image().bind(s, item.getUser().getHeaderPic() , Utils.INSTANCE.getCirImageOptions);

        helper.setText(R.id.tv_doctor_name, TextUtils.isEmpty(item.getUser().getNickname()) ? "" : item.getUser().getNickname());

        helper.setText(R.id.tv_doctor_auto, TextUtils.isEmpty(item.getProgress()) ? "" : item.getProgress());*/
    }
}
