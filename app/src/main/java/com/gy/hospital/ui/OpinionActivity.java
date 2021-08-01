package com.gy.hospital.ui;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.gy.hospital.R;
import com.gy.hospital.adapter.OpinionAdapter;
import com.gy.hospital.base.BaseRefreshActivity;
import com.gy.hospital.entity.opinion;
import com.gy.hospital.net.Bmob;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.List;

@ContentView(R.layout.activity_opinion)
public class OpinionActivity extends BaseRefreshActivity<opinion, OpinionAdapter> implements Bmob.bmobOnGetList<List<opinion>> {

    @Event(R.id.opinion_fab)
    private void onClick(View v) {
        Intent i = new Intent(this, FeedbackActivity.class);
        startActivityForResult(i, 1000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                if (data != null && data.getBooleanExtra("flag", false)) {
                    Bmob.INSTANCE.setBmobOnGetList(this);
                    refreshData();
                }
                break;
        }
    }

    @Override
    public void getMethod() {
        Bmob.INSTANCE.getOpinion(pages);
    }

    @Override
    public void initData() {
        setToolBarTitle("意见反馈");
        Bmob.INSTANCE.setBmobOnGetList(this);
        isNeedLoadMore = true;
        adapter = new OpinionAdapter(R.layout.item_opinion, list);
        getMethod();

    }

    @Override
    public void onbmobOnGetListSuccess(List<opinion> opinions) {
        onSuccessGetData(opinions);
    }

    @Override
    public void onbmobOnGetListFailure(String text) {
        onFailGetData(text);
    }
}
