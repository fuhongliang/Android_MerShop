package cn.ifhu.fragments.orders;


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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.R;
import cn.ifhu.adapter.OnGoingOrdersAdapter;
import cn.ifhu.base.BaseFragment;
import cn.ifhu.base.BaseObserver;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.OrderBean;
import cn.ifhu.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.net.OrderService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.utils.DialogUtils;
import cn.ifhu.utils.DividerItemDecoration;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;

import static cn.ifhu.utils.Constants.ORDERONGOING;

/**
 * @author fuhongliang
 */
public class OngoingOrderFragment extends BaseFragment {

    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;

    OnGoingOrdersAdapter mOrdersAdapter;
    @BindView(R.id.rl_empty)
    RelativeLayout llEmpty;
    private List<OrderBean> mDatas = new ArrayList<OrderBean>();
    private ArrayList<String> reasonList;

    public static OngoingOrderFragment newInstance() {
        return new OngoingOrderFragment();
    }

    public OngoingOrderFragment() {
        // Required empty public constructor
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
        getData();
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOrdersAdapter = new OnGoingOrdersAdapter(mDatas, getContext());
        recyclerList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerList.setAdapter(mOrdersAdapter);
        setRefreshLayout();
    }

    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeColors(R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark);
        layoutSwipeRefresh.setOnRefreshListener(() -> {
            getData();
        });
    }


    public void showPrintDialog(int position) {
        DialogUtils.showConfirmDialog("提示", "确定打印小票？", getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {
                ToastHelper.makeText("点击了取消按钮", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }

            @Override
            public void ok() {
                ToastHelper.makeText("点击了确定按钮", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    protected void initData() {
        reasonList = new ArrayList<>();
        reasonList.add("商品已售罄");
        reasonList.add("商家已打样");
        reasonList.add("其他");
    }

    public void getData() {
        layoutSwipeRefresh.setRefreshing(true);
        RetrofitAPIManager.create(OrderService.class).getOrder(ORDERONGOING, UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ArrayList<OrderBean>>(true) {

            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ArrayList<OrderBean>> t) throws Exception {

                if (t.getData() == null || t.getData().isEmpty()) {
                    llEmpty.setVisibility(View.VISIBLE);
                } else {
                    llEmpty.setVisibility(View.GONE);
                    mDatas.clear();
                    mDatas.addAll(t.getData());
                    mOrdersAdapter.updateData(mDatas);
                }
                ToastHelper.makeText("刷新成功！", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });
    }
}
