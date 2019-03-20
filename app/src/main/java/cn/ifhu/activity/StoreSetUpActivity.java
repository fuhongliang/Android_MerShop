package cn.ifhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;

/**
 * @author fuhongliang
 */
public class StoreSetUpActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_store_state)
    LinearLayout llStoreState;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.rl_notice)
    RelativeLayout rlNotice;
    @BindView(R.id.tv_store_phone)
    TextView tvStorePhone;
    @BindView(R.id.ll_store_phone)
    LinearLayout llStorePhone;
    @BindView(R.id.tv_store_add)
    TextView tvStoreAdd;
    @BindView(R.id.tv_store_time)
    TextView tvStoreTime;
    @BindView(R.id.rl_store_license)
    RelativeLayout rlStoreLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setup);
        ButterKnife.bind(this);
        tvTitle.setText("门店设置");
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.ll_store_state)
    public void onLlStoreStateClicked() {
        startActivity(new Intent(StoreSetUpActivity.this,StoreStateActivity.class));
    }

    @OnClick(R.id.rl_notice)
    public void onRlNoticeClicked() {
    }

    @OnClick(R.id.ll_store_phone)
    public void onLlStorePhoneClicked() {
        startActivity(new Intent(StoreSetUpActivity.this,ChangeStorePhoneActivity.class));

    }

    @OnClick(R.id.tv_store_time)
    public void onTvStoreTimeClicked() {
    }

    @OnClick(R.id.rl_store_license)
    public void onRlStoreLicenseClicked() {
    }
}
