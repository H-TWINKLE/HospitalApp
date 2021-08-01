package com.gy.hospital.base;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gy.hospital.R;

import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Objects;

public abstract class BaseRefreshFragment<T, V extends BaseQuickAdapter<T, BaseViewHolder>> extends BaseFragment {

    public abstract void getMethod();

    @ViewInject(R.id.base_swipe_refresh_layout)
    private SwipeRefreshLayout base_swipe_refresh_layout;

    @ViewInject(R.id.base_recycler_view)
    private RecyclerView base_recycler_view;

    public List<T> list;

    public V adapter;

    public int pages = 0;

    public boolean isRefresh = false;

    public boolean isNeedLoadMore;

    @Override
    public void initView() {
        setDefaultView();
    }

    public SwipeRefreshLayout getBase_swipe_refresh_layout() {
        return base_swipe_refresh_layout;
    }

    public RecyclerView getBase_recycler_view() {
        return base_recycler_view;
    }


    public void setDefaultView() {
        base_swipe_refresh_layout.setColorSchemeColors(Color.rgb(47, 223, 189));
        base_swipe_refresh_layout.setOnRefreshListener(this::refreshData);

        base_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        base_recycler_view.setAdapter(adapter);

        if (isRefresh) {
            base_swipe_refresh_layout.setRefreshing(true);
        }

        if (isNeedLoadMore) {
            adapter.setOnLoadMoreListener(this::loadMoreData, base_recycler_view);
            adapter.setLoadMoreView(new BaseLoadMoreView());
            adapter.setEnableLoadMore(true);
        }


    }


    public void refreshData() {
        pages = 0;
        isRefresh = true;
        enableRefreshData(true);
        getMethod();

    }

    public void loadMoreData() {

        enableRefreshData(true);
        getMethod();


    }

    public void enableLoadMore(boolean flag) {
        base_swipe_refresh_layout.setRefreshing(!flag);
        adapter.setEnableLoadMore(flag);
    }

    public void enableRefreshData(boolean flag) {
        base_swipe_refresh_layout.setRefreshing(flag);
        adapter.setEnableLoadMore(!flag);
    }

    public void onSuccessGetData(List<T> data) {
        if (list == null) {
            adapter.setNewData(data);
            list = data;
        } else {
            if (isRefresh) {
                adapter.replaceData(data);
                isRefresh = false;
            } else {
                adapter.addData(data);
            }
        }

        enableLoadMore(true);
        pages++;

        if (data == null || data.isEmpty() || data.size() < 10) {
            adapter.loadMoreComplete();
            adapter.setEnableLoadMore(false);
        } else {
            adapter.setEnableLoadMore(true);
        }


    }


    public void onFailGetData(String text) {
        base_swipe_refresh_layout.setRefreshing(false);
        adapter.loadMoreFail();
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public void setToastString(String text) {
        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        });
    }


}
