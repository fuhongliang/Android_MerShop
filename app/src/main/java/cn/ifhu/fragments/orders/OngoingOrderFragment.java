package cn.ifhu.fragments.orders;


import android.annotation.SuppressLint;
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
import cn.ifhu.adapter.NewOrdersAdapter;
import cn.ifhu.adapter.OnGoingOrdersAdapter;
import cn.ifhu.base.BaseFragment;
import cn.ifhu.dialog.DialogListFragment;
import cn.ifhu.dialog.NormalTextDialog;
import cn.ifhu.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.utils.DialogUtils;
import cn.ifhu.utils.ToastHelper;

/**
 * @author tony
 */
public class OngoingOrderFragment extends BaseFragment{

    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;

    OnGoingOrdersAdapter newOrdersAdapter;
    //模拟数据
    private List<String> mDatas;
    private ArrayList<String> reasonList;
    public static OngoingOrderFragment newInstance() {
        return new OngoingOrderFragment();
    }


    public OngoingOrderFragment() {
        // Required empty public constructor
    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 1; i < 80; i++) {
            mDatas.add("#" +  i);
        }
        reasonList = new ArrayList<>();
        reasonList.add("商品已售罄");
        reasonList.add("商家已打样");
        reasonList.add("其他");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongoging_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        newOrdersAdapter = new OnGoingOrdersAdapter(mDatas, getContext(), position -> {
            showPrintDialog(position);
        });
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


    public void showPrintDialog(int position) {
        DialogUtils.showConfirmDialog("提示","确定打印小票？", getActivity().getSupportFragmentManager(),new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {
                    ToastHelper.makeText("点击了取消按钮",Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            }

            @Override
            public void ok() {
                ToastHelper.makeText("点击了确定按钮",Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
