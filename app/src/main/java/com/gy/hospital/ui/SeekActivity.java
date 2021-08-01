package com.gy.hospital.ui;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;

import com.gy.hospital.R;
import com.gy.hospital.adapter.SeekAdapter;
import com.gy.hospital.base.BaseRefreshActivity;
import com.gy.hospital.entity.seek;
import com.gy.hospital.net.Bmob;

import org.xutils.view.annotation.ContentView;

import java.util.List;

@ContentView(R.layout.base_toolbar_with_refresh_layout)
public class SeekActivity extends BaseRefreshActivity<seek, SeekAdapter> implements Bmob.bmobOnGetList<List<seek>> {


    @Override
    public void getMethod() {
        Bmob.INSTANCE.getSeek(pages);
    }

    @Override
    public void initData() {

        setToolBarTitle("就诊情况");
        Bmob.INSTANCE.setBmobOnGetList(this);
        isNeedLoadMore = true;
        adapter = new SeekAdapter(R.layout.item_seek, list);

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.seek_fab_replay: {
                    createDialog(this.adapter.getData().get(position));
                }
                break;
            }
        });


        getMethod();


    }


    private void createDialog(seek s) {

        final EditText e = new EditText(this);
        e.setText(TextUtils.isEmpty(s.getEval()) ? "" : s.getEval());

        new AlertDialog.Builder(this).setView(e).setTitle("请填写评价")
                .setPositiveButton("取消", null)
                .setNegativeButton("评价", (dialogInterface, i) -> {

                    s.setEval(e.getText().toString());
                    s.update();
                    setToastString("操作成功");
                    refreshData();

                }).show();


    }


    @Override
    public void onbmobOnGetListSuccess(List<seek> seeks) {
        onSuccessGetData(seeks);
    }

    @Override
    public void onbmobOnGetListFailure(String text) {
        onFailGetData(text);
    }
}
