package com.gy.hospital.ui.fragment;

import com.gy.hospital.R;
import com.gy.hospital.adapter.NewsAdapter;
import com.gy.hospital.base.BaseRefreshFragment;
import com.gy.hospital.entity.post;

import org.xutils.view.annotation.ContentView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

@ContentView(R.layout.fragment_news)
public class NewsFragment extends BaseRefreshFragment<post, NewsAdapter> {
    public NewsFragment() {
    }

    @Override
    public void getMethod() {

        getPost(pages);
    }

    @Override
    public void initData() {

        adapter = new NewsAdapter(R.layout.item_notice, list);
        isNeedLoadMore = true;

        getMethod();

    }

    private void getPost(int pages) {

        BmobQuery<post> query = new BmobQuery<>();
        query.order("-createdAt");
        query.setLimit(10);
        query.setSkip(pages * 10);
        query.include("author");

        query.findObjects(new FindListener<post>() {
            @Override
            public void done(List<post> list, BmobException e) {
                if (e == null) {
                    onSuccessGetData(list);
                } else {
                    onFailGetData(e.getLocalizedMessage());
                }
            }
        });

    }

}
