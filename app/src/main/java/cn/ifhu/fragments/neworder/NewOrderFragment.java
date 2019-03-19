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

import com.orhanobut.logger.Logger;

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
import cn.ifhu.base.BaseObserver;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.OrderBean;
import cn.ifhu.bean.UserServiceBean;
import cn.ifhu.dialog.DialogListFragment;
import cn.ifhu.net.OrderService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.net.UserService;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;

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

    public void getNewOrders(){
        Logger.d("getNewOrders");
        layoutSwipeRefresh.setRefreshing(true);
        RetrofitAPIManager.create(OrderService.class).getNewOrder(UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ArrayList<OrderBean>>(true) {

            @Override
            protected void onApiComplete() {
                Logger.d("onApiComplete");
                layoutSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ArrayList<OrderBean>> t) throws Exception {
                Logger.d("onSuccees"+t);
                if (t.getData().isEmpty()){
//                    recyclerList
                }else {
                    mDatas.clear();
                    mDatas.addAll(t.getData());
                    newOrdersAdapter.updateData(mDatas);
                }

                ToastHelper.makeText("刷新成功！", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            }
        });
    }

    public void receiveOrder(String orderId){

        RetrofitAPIManager.create(OrderService.class).receiveOrder(orderId)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<ArrayList<OrderBean>>(true) {

            @Override
            protected void onApiComplete() {
                Logger.d("onApiComplete");
                layoutSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onSuccees(BaseEntity<ArrayList<OrderBean>> t) throws Exception {
                Logger.d("onSuccees"+t);
                if (t.getData().isEmpty()){
//                    recyclerList
                }else {
                    mDatas.clear();
                    mDatas.addAll(t.getData());
                    newOrdersAdapter.updateData(mDatas);
                }

                ToastHelper.makeText("刷新成功！", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            }
        });
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
        Logger.d("onViewCreated");
        initData();
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        newOrdersAdapter = new NewOrdersAdapter(mDatas, getContext(), new NewOrdersAdapter.OnclickButton() {
            @Override
            public void refuse(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "请选择拒单理由");
                bundle.putStringArrayList("stringList", reasonList);
                showOptionDialog(bundle);
            }

            @Override
            public void accept(int position) {
                mDatas.remove(position);
                newOrdersAdapter.updateData(mDatas);
                ToastHelper.makeText("接单成功",Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            }
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
            getNewOrders();
        });
    }

    /**
     * @param bundle 携带参数
     */
    public void showOptionDialog(Bundle bundle) {
        DialogListFragment.showOperateDialog(getActivity().getSupportFragmentManager(), bundle, string -> {

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
