package cn.ifhu.fragments.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.R;
import cn.ifhu.activity.AccountAndSafeActivity;
import cn.ifhu.activity.FeedBackActivity;
import cn.ifhu.activity.StoreSetUpActivity;
import cn.ifhu.base.BaseFragment;

/**
 * @author tony
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.ll_store_setup)
    LinearLayout llStoreSetup;
    @BindView(R.id.ll_print_setup)
    LinearLayout llPrintSetup;
    @BindView(R.id.ll_notice)
    LinearLayout llNotice;
    @BindView(R.id.ll_account_safe)
    LinearLayout llAccountSafe;
    @BindView(R.id.ll_feedback)
    LinearLayout llFeedback;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    Unbinder unbinder;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;

    public static MeFragment newInstance() {
        return new MeFragment();
    }


    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ll_store_setup)
    public void onLlStoreSetupClicked() {
        startActivity(new Intent(getActivity(), StoreSetUpActivity.class));
    }

    @OnClick(R.id.ll_print_setup)
    public void onLlPrintSetupClicked() {

    }

    @OnClick(R.id.ll_notice)
    public void onLlNoticeClicked() {

    }

    @OnClick(R.id.ll_account_safe)
    public void onLlAccountSafeClicked() {
        startActivity(new Intent(getActivity(), AccountAndSafeActivity.class));
    }

    @OnClick(R.id.ll_feedback)
    public void onLlFeedbackClicked() {
        startActivity(new Intent(getActivity(), FeedBackActivity.class));
    }

    @OnClick(R.id.ll_service)
    public void onLlServiceClicked() {

    }

    @OnClick(R.id.ll_about_us)
    public void onViewClicked() {

    }
}
