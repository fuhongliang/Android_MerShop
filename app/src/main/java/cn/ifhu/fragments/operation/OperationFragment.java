package cn.ifhu.fragments.operation;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.R;
import cn.ifhu.activity.ProductActivity;
import cn.ifhu.activity.ReviewListActivity;
import cn.ifhu.base.BaseFragment;

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ll_OperationProduct)
    public void onLlOperationProductClicked() {
        startActivity(new Intent(getActivity(), ProductActivity.class));
    }

    @OnClick(R.id.ll_reviews)
    public void onLlReviewsClicked() {
        startActivity(new Intent(getActivity(), ReviewListActivity.class));
    }
}
