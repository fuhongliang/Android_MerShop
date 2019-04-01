package cn.ifhu.fragments.operation;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.R;
import cn.ifhu.activity.ProductManageActivity;
import cn.ifhu.activity.ReviewListActivity;
import cn.ifhu.base.BaseFragment;
import cn.ifhu.base.BaseObserver;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.OperationBean;
import cn.ifhu.net.OperationService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.utils.UserLogic;

/**
 * @author tony
 */
public class OperationFragment extends BaseFragment {

    @BindView(R.id.ll_OperationProduct)
    LinearLayout llOperationProduct;
    @BindView(R.id.ll_reviews)
    LinearLayout llReviews;
    @BindView(R.id.ll_operation_data)
    LinearLayout llOperationData;
    @BindView(R.id.find_ic_xszk)
    ImageView findIcXszk;
    @BindView(R.id.find_ic_mlj)
    ImageView findIcMlj;
    @BindView(R.id.find_ic_yhtc)
    ImageView findIcYhtc;
    @BindView(R.id.find_ic_djq)
    ImageView findIcDjq;
    Unbinder unbinder;
    @BindView(R.id.tv_earn_today)
    TextView tvEarnToday;
    @BindView(R.id.tv_orders_today)
    TextView tvOrdersToday;
    @BindView(R.id.tv_store_collect)
    TextView tvStoreCollect;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_30_ordernum)
    TextView tv30Ordernum;
    @BindView(R.id.tv_30_orderamount)
    TextView tv30Orderamount;

    public static OperationFragment newInstance() {
        return new OperationFragment();
    }


    public OperationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operation, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getOperationData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getOperationData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ll_OperationProduct)
    public void onLlOperationProductClicked() {
        startActivity(new Intent(getActivity(), ProductManageActivity.class));
    }

    @OnClick(R.id.ll_reviews)
    public void onLlReviewsClicked() {
        startActivity(new Intent(getActivity(), ReviewListActivity.class));
    }

    public void getOperationData() {
        RetrofitAPIManager.create(OperationService.class).storeYunying(UserLogic.getUser().getStore_id())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<OperationBean>(false) {
            @Override
            protected void onApiComplete() {

            }

            @Override
            protected void onSuccees(BaseEntity<OperationBean> t) throws Exception {
                initData(t.getData());
            }
        });
    }

    public void initData(OperationBean operationBean){
        tvEarnToday.setText(operationBean.getToday_orderamount());
        tvOrdersToday.setText(operationBean.getToday_ordernum()+"");
        tvStoreCollect.setText(operationBean.getStore_collect()+"");
        tvGoodsNum.setText(operationBean.getGoods_num()+"");
        tv30Ordernum.setText(operationBean.get_$30_ordernum()+"");
        tv30Orderamount.setText(operationBean.get_$30_orderamount());

    }
}
