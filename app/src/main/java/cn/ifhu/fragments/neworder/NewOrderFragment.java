package cn.ifhu.fragments.neworder;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.R;
import cn.ifhu.activity.MainActivity;
import cn.ifhu.activity.login.LoginActivity;
import cn.ifhu.adapter.NewOrdersAdapter;
import cn.ifhu.base.BaseFragment;
import cn.ifhu.utils.ToastHelper;

/**
 * @author tony
 */
public class NewOrderFragment extends BaseFragment {

    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;

    NewOrdersAdapter newOrdersAdapter;
    //模拟数据
    private List<String> mDatas;

    public static NewOrderFragment newInstance() {
        return new NewOrderFragment();
    }


    public NewOrderFragment() {
        // Required empty public constructor
    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 1; i < 80; i++) {
            mDatas.add("#" +  i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        newOrdersAdapter = new NewOrdersAdapter(mDatas,getContext());
        recyclerList.setAdapter(newOrdersAdapter);
        setRefreshLayout();
    }

    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeColors(R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark);
        layoutSwipeRefresh.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                layoutSwipeRefresh.setRefreshing(false);
                ToastHelper.makeText("刷新成功！", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                initData();
                newOrdersAdapter.notifyDataSetChanged();
            },1000);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
