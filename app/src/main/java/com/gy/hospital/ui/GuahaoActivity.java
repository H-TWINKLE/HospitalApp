package com.gy.hospital.ui;

import com.gy.hospital.R;
import com.gy.hospital.adapter.GuaHaoAdapter;
import com.gy.hospital.base.BaseRefreshActivity;
import com.gy.hospital.entity.register;
import com.gy.hospital.net.Bmob;

import org.xutils.view.annotation.ContentView;

import java.util.List;

@ContentView(R.layout.activity_guahao)
public class GuahaoActivity extends BaseRefreshActivity<register, GuaHaoAdapter> implements Bmob.bmobOnGetList<List<register>> {


    @Override
    public void getMethod() {
        Bmob.INSTANCE.getGuahao(pages);
    }

    @Override
    public void initData() {

        setToolBarTitle("我的挂号");

        Bmob.INSTANCE.setBmobOnGetList(this);

        isNeedLoadMore = true;

        adapter = new GuaHaoAdapter(R.layout.item_guahao, list);

        getMethod();
    }

    @Override
    public void onbmobOnGetListSuccess(List<register> registers) {
        onSuccessGetData(registers);
    }

    @Override
    public void onbmobOnGetListFailure(String text) {
        onFailGetData(text);
    }
}
