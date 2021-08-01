package com.gy.hospital.ui;

import com.gy.hospital.R;
import com.gy.hospital.adapter.NoticeAdapter;
import com.gy.hospital.base.BaseRefreshActivity;
import com.gy.hospital.entity.notice;
import com.gy.hospital.net.Bmob;

import org.xutils.view.annotation.ContentView;

import java.util.List;

@ContentView(R.layout.base_toolbar_with_refresh_layout)
public class NoticeActivity extends BaseRefreshActivity<notice, NoticeAdapter> implements Bmob.bmobOnGetList<List<notice>> {

    @Override
    public void getMethod() {
        Bmob.INSTANCE.getNotice(pages);
    }

    @Override
    public void initData() {

        setToolBarTitle("查看公告");
        Bmob.INSTANCE.setBmobOnGetList(this);
        isNeedLoadMore = true;
        adapter = new NoticeAdapter(R.layout.item_notice, list);
        getMethod();
    }

    @Override
    public void onbmobOnGetListSuccess(List<notice> notices) {
        onSuccessGetData(notices);
    }

    @Override
    public void onbmobOnGetListFailure(String text) {
        onFailGetData(text);
    }
}
