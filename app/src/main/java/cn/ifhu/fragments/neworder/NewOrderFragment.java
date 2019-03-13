package cn.ifhu.fragments.neworder;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.R;
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
            mDatas.add("" +  i);
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
        recyclerList.setAdapter(new NewOrdersAdapter(mDatas,getContext()));
        setRefreshLayout();
    }

    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeColors(R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark);
        layoutSwipeRefresh.setOnRefreshListener(() -> {

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
