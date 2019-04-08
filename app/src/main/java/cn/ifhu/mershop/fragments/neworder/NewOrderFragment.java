package cn.ifhu.mershop.fragments.neworder;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ifhu.R;
import cn.ifhu.mershop.adapter.NewOrdersAdapter;
import cn.ifhu.mershop.base.BaseFragment;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.OrderBean;
import cn.ifhu.mershop.dialog.DialogListFragment;
import cn.ifhu.mershop.net.OrderService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.DividerItemDecoration;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class NewOrderFragment extends BaseFragment {

    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;

    NewOrdersAdapter newOrdersAdapter;
    @BindView(R.id.rl_empty)
    RelativeLayout llEmpty;
    private List<OrderBean> mDatas;
    private ArrayList<String> reasonList;

    public static NewOrderFragment newInstance() {
        return new NewOrderFragment();
    }


    public NewOrderFragment() {
        // Required empty public constructor
    }

    protected void initData() {
        mDatas = new ArrayList<>();
        reasonList = new ArrayList<>();
        reasonList.add("商品已售罄");
        reasonList.add("商家已打样");
        reasonList.add("其他");
    }

    public void getNewOrders() {
        layoutSwipeRefresh.setRefreshing(true);
        RetrofitAPIManager.create(OrderService.class).getNewOrder(UserLogic.getUser().getStore_id())
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
                    newOrdersAdapter.updateData(mDatas);
                }
                ToastHelper.makeText("刷新成功！", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });
    }

    public void receiveOrder(String orderId, int position) {

        RetrofitAPIManager.create(OrderService.class).receiveOrder(orderId)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

            @Override
            protected void onApiComplete() {

            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                mDatas.remove(position);
                newOrdersAdapter.updateData(mDatas);
                ToastHelper.makeText("接单成功", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });
    }

    public void refuseOrder(int orderId, int position, String reason) {

        RetrofitAPIManager.create(OrderService.class).refuseOrder(orderId, reason)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {

            @Override
            protected void onApiComplete() {

            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                mDatas.remove(position);
                newOrdersAdapter.updateData(mDatas);
                ToastHelper.makeText("已拒绝！", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        newOrdersAdapter = new NewOrdersAdapter(mDatas, getContext(), new NewOrdersAdapter.OnclickButton() {
            @Override
            public void refuse(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "请选择拒单理由");
                bundle.putStringArrayList("stringList", reasonList);
                showOptionDialog(bundle, position);
            }

            @Override
            public void accept(int position) {
                receiveOrder(mDatas.get(position).getOrder_id() + "¥", position);
            }
        });
        recyclerList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerList.setAdapter(newOrdersAdapter);
        setRefreshLayout();
        getNewOrders();
    }

    @SuppressLint("ResourceAsColor")
    public void setRefreshLayout() {
        layoutSwipeRefresh.setColorSchemeColors(R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark);

        layoutSwipeRefresh.setOnRefreshListener(() -> {
            getNewOrders();
        });
    }

    /**
     * @param bundle 携带参数
     */
    public void showOptionDialog(Bundle bundle, int position) {
        DialogListFragment.showOperateDialog(getActivity().getSupportFragmentManager(), bundle, string -> {
            refuseOrder(mDatas.get(position).getOrder_id(), position, string);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
