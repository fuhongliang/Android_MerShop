package cn.ifhu.mershop.fragments.neworder;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
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

import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.activity.MainActivity;
import cn.ifhu.mershop.activity.notice.NoticeListActivity;
import cn.ifhu.mershop.adapter.NewOrdersAdapter;
import cn.ifhu.mershop.base.BaseFragment;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.MessageEvent;
import cn.ifhu.mershop.bean.NewOrderBean;
import cn.ifhu.mershop.bean.OrderBean;
import cn.ifhu.mershop.dialog.DialogListFragment;
import cn.ifhu.mershop.net.OrderService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.notificaitons.Notificaitons;
import cn.ifhu.mershop.utils.DividerItemDecoration;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;

import static cn.ifhu.mershop.utils.Constants.LOGOUT;
import static cn.ifhu.mershop.utils.Constants.ORDERCOMING;
import static cn.ifhu.mershop.utils.Constants.ORDERGOING;

/**
 * @author fuhongliang
 */
public class NewOrderFragment extends BaseFragment {
    @BindView(R.id.simpleMarqueeView)
    SimpleMarqueeView<String> simpleMarqueeView;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    Unbinder unbinder;

    NewOrdersAdapter newOrdersAdapter;
    @BindView(R.id.rl_empty)
    RelativeLayout llEmpty;
    @BindView(R.id.rl_marquee_view)
    RelativeLayout rlMarqueeView;
    private List<NewOrderBean.ListBean> mDatas;
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
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<NewOrderBean>(true) {

            @Override
            protected void onApiComplete() {
                layoutSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onSuccees(BaseEntity<NewOrderBean> t) throws Exception {

                if (t.getData() == null || t.getData().getList().isEmpty()) {

                } else {
                    mDatas.clear();
                    mDatas.addAll(t.getData().getList());
                    newOrdersAdapter.updateData(mDatas);
                }
                updateEmptyView();
                ToastHelper.makeText("刷新成功！", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            }
        });
    }

    public void updateEmptyView() {
        if (newOrdersAdapter.getItemCount() > 0) {
            llEmpty.setVisibility(View.GONE);
            stopFlipping(false);
        } else {
            llEmpty.setVisibility(View.VISIBLE);
            stopFlipping(true);
        }
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
                updateEmptyView();
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
                updateEmptyView();
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
                receiveOrder(mDatas.get(position).getOrder_id() + "", position);
            }
        });
        recyclerList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerList.setAdapter(newOrdersAdapter);
        setRefreshLayout();
        getNewOrders();
        setSimpleMarqueeView();
    }

    public void setSimpleMarqueeView() {
        final List<String> datas = Arrays.asList("您有一笔新订单，系统已自动接单~", "您有一笔新订单，系统已自动接单~");
        SimpleMF<String> marqueeFactory = new SimpleMF(getContext());
        marqueeFactory.setData(datas);
        simpleMarqueeView.setMarqueeFactory(marqueeFactory);
        simpleMarqueeView.startFlipping();
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


    @OnClick(R.id.iv_notice)
    public void onIvNoticeClicked() {
        startActivity(new Intent(getContext(), NoticeListActivity.class));
    }

    @OnClick(R.id.iv_close)
    public void onIvCloseClicked() {
        stopFlipping(true);
    }

    public void stopFlipping(boolean stop) {
        if (stop){
            simpleMarqueeView.stopFlipping();
            rlMarqueeView.setVisibility(View.GONE);
        }else {
            simpleMarqueeView.startFlipping();
            rlMarqueeView.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case ORDERCOMING:
                try {
                    JSONObject jsonObject = new JSONObject(messageEvent.getData());
                    UMessage msg = new UMessage(jsonObject);
                    sendNotification(getActivity(),msg.title,msg.text,msg.title);
                    getNewOrders();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
        }
    }

    public void sendNotification(Context context, String title, String text, String contentTitle){
        NotificationManager mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notificaitons.getInstance().sendOrderComingNotification(context,mNM,title,text,contentTitle);
    }

}
