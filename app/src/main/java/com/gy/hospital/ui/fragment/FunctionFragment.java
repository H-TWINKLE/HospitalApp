package com.gy.hospital.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gy.hospital.R;
import com.gy.hospital.adapter.FunctionAdapter;
import com.gy.hospital.base.BaseRefreshFragment;
import com.gy.hospital.entity.Function;
import com.gy.hospital.entity.User;
import com.gy.hospital.net.Bmob;
import com.gy.hospital.ui.GuahaoActivity;
import com.gy.hospital.ui.InfoActivity;
import com.gy.hospital.ui.NoticeActivity;
import com.gy.hospital.ui.OpinionActivity;
import com.gy.hospital.ui.RobotActivity;
import com.gy.hospital.ui.SeekActivity;
import com.gy.hospital.utils.Constants;
import com.gy.hospital.utils.Utils;
import com.gy.hospital.view.MyDialog;
import com.loopj.android.image.SmartImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import cn.bmob.v3.BmobUser;

@ContentView(R.layout.fragment_function)
public class FunctionFragment extends BaseRefreshFragment<Function, FunctionAdapter> implements Bmob.bmobOnGetList<User> {

    private SmartImageView s;

    private TextView t;

    private TextView auto;

    public static final String UPDATE = "action.update";

    private InfoReceiver info = new InfoReceiver();

    private MyDialog myDialog;

    //调用系统相册-选择图片
    private static final int IMAGE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE);
        Objects.requireNonNull(getActivity()).registerReceiver(info, filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(getActivity()).unregisterReceiver(info);
    }

    public FunctionFragment() {
    }


    @Override
    public void getMethod() {
        Bmob.INSTANCE.setBmobOnGetList(this);
    }

    @Override
    public void initData() {

        Bmob.INSTANCE.setBmobOnGetList(this);

        list = new ArrayList<>();

        Function f;

        for (int x = 0; x < Constants.FUNCTION.length; x++) {
            f = new Function();
            f.setName(Constants.FUNCTION[x]);
            f.setIcon(Constants.FUNCTION_ICON[x]);
            list.add(f);
        }

        isNeedLoadMore = false;

        adapter = new FunctionAdapter(R.layout.item_function, list);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                    startActivity(InfoActivity.class);
                    break;
                case 1:
                    startActivity(GuahaoActivity.class);
                    break;
                case 2:
                    startActivity(OpinionActivity.class);
                    break;
                case 3:
                    startActivity(NoticeActivity.class);
                    break;
                case 4:
                    startActivity(SeekActivity.class);
                    break;
                case 5:
                    startActivity(RobotActivity.class);
                    break;
            }
        });

    }

    @Override
    public void initView() {
        super.initView();

        myDialog = new MyDialog(Objects.requireNonNull(getActivity()));

        adapter.setHeaderView(setHeaderView());
        getBase_swipe_refresh_layout().setEnabled(false);
    }

    private View setHeaderView() {

        View view = View.inflate(getActivity(), R.layout.item_header_hospital, null);
        s = view.findViewById(R.id.index_header_siv_logo);
        s.setOnClickListener(view1 -> {
            //调用相册
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE);
        });

        t = view.findViewById(R.id.index_header_tv_h_name);
        auto = view.findViewById(R.id.index_header_tv_h_auto);
        setVal();

        return view;

    }

    private void setVal() {

        User user = BmobUser.getCurrentUser(User.class);
        x.image().bind(s, user.getHeaderPic() == null ? "" : user.getHeaderPic(), Utils.INSTANCE.getCirImageOptions);
        t.setText(user.getNickname() == null ? user.getUsername() : user.getNickname());
        auto.setText(user.getAuto() == null ? "我还没有个性签名" : user.getAuto());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {

            Uri selectedImage = data.getData();
            String url = Utils.INSTANCE.getRealPathFromURI(Objects.requireNonNull(getActivity()), selectedImage);

            Bmob.INSTANCE.setBmobOnGetList(this);
            Bmob.INSTANCE.BmobUploadPic(new File(url));
            myDialog.show();


        }
    }


    @Override
    public void onbmobOnGetListSuccess(User u) {

        if (u != null) {
            Bmob.INSTANCE.BmobFetchUserInfo(getContext());
            x.image().bind(s, u.getHeaderPic() == null ? "" : u.getHeaderPic(), Utils.INSTANCE.getCirImageOptions);
        }

        myDialog.dismiss();

    }

    @Override
    public void onbmobOnGetListFailure(String text) {

        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

        myDialog.dismiss();
    }


    class InfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setVal();
        }

    }
}
