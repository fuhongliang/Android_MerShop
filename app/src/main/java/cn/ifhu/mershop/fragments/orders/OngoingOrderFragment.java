package cn.ifhu.mershop.fragments.orders;


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
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.adapter.OnGoingOrdersAdapter;
import cn.ifhu.mershop.base.BaseFragment;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.MessageEvent;
import cn.ifhu.mershop.bean.OrderBean;
import cn.ifhu.mershop.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.mershop.net.OrderService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.DialogUtils;
import cn.ifhu.mershop.utils.DividerItemDecoration;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;

import static cn.ifhu.mershop.utils.Constants.LOGOUT;
import static cn.ifhu.mershop.utils.Constants.ORDERGOING;
import static cn.ifhu.mershop.utils.Constants.ORDERONGOING;

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
        EventBus.getDefault().register(this);
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
        DialogUtils.showConfirmDialog("温馨提示", "确定打印小票？", getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
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
        EventBus.getDefault().unregister(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (ORDERGOING.equals(messageEvent.getMessage())) {
           getData();
        }
    }
}
