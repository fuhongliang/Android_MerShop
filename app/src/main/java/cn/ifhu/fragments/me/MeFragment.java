package cn.ifhu.fragments.me;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baba.GlideImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.R;
import cn.ifhu.activity.AccountAndSafeActivity;
import cn.ifhu.activity.FeedBackActivity;
import cn.ifhu.activity.MainActivity;
import cn.ifhu.activity.RingSettingsActivity;
import cn.ifhu.activity.StoreSetUpActivity;
import cn.ifhu.activity.login.LoginActivity;
import cn.ifhu.base.BaseFragment;
import cn.ifhu.dialog.nicedialog.ConfirmDialog;
import cn.ifhu.utils.DialogUtils;
import cn.ifhu.utils.UserLogic;

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
    @BindView(R.id.iv_store_logo)
    GlideImageView ivStoreLogo;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_store_add)
    TextView tvStoreAdd;

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
        setStoreInfo();
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
        startActivity(new Intent(getActivity(), RingSettingsActivity.class));
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
        DialogUtils.showConfirmDialog("客服电话", "020-78785656", "取消", "联系客服", getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {
            }

            @Override
            public void ok() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "020-78785656");
                intent.setData(data);
                startActivity(intent);
            }
        });
    }


    public void setStoreInfo() {
        tvStoreName.setText(UserLogic.getUser().getStore_name());
        tvStoreAdd.setText(UserLogic.getUser().getStore_address());
        ivStoreLogo.loadCircle(UserLogic.getUser().getStore_avatar());
    }


    @OnClick(R.id.ll_about_us)
    public void onLlAboutUsClicked() {

    }

    @OnClick(R.id.btn_logout)
    public void onBtnLogoutClicked() {
        ((MainActivity)getActivity()).logout();
    }
}
