package cn.ifhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baba.GlideImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.base.BaseObserver;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.UserServiceBean;
import cn.ifhu.dialog.DialogWheelFragment;
import cn.ifhu.net.MeService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;

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
    @BindView(R.id.tv_store_state)
    TextView tvStoreState;
    @BindView(R.id.tv_store_notice)
    TextView tvStoreNotice;
    UserServiceBean.LoginResponse loginResponse;
    @BindView(R.id.iv_logo)
    GlideImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setup);
        ButterKnife.bind(this);
        tvTitle.setText("门店设置");
        initData();
    }


    public void initData() {
        loginResponse = UserLogic.getUser();
        tvStoreState.setText(loginResponse.getStore_state() == 0 ? "已停止营业" : "正常营业中");
        tvStoreNotice.setText(loginResponse.getStore_description());
        ivLogo.load(loginResponse.getStore_avatar());
        tvStorePhone.setText(loginResponse.getStore_phone());
        tvStoreAdd.setText(loginResponse.getStore_address());
        tvStoreTime.setText(loginResponse.getWork_start_time() + "~" + loginResponse.getWork_end_time());
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
        startActivity(new Intent(StoreSetUpActivity.this, StoreStateActivity.class));
    }

    @OnClick(R.id.rl_notice)
    public void onRlNoticeClicked() {
        startActivity(new Intent(StoreSetUpActivity.this, StoreNoticeActivity.class));
    }

    @OnClick(R.id.ll_store_phone)
    public void onLlStorePhoneClicked() {
        startActivity(new Intent(StoreSetUpActivity.this, ChangeStorePhoneActivity.class));
    }

    @OnClick(R.id.ll_store_time)
    public void onTvStoreTimeClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("startTime", loginResponse.getWork_start_time());
        bundle.putString("endTime", loginResponse.getWork_end_time());

        DialogWheelFragment.showOperateDialog(getSupportFragmentManager(), bundle, new DialogWheelFragment.OperateDialogConfirmListner() {
            @Override
            public void onClickTextView(String beginTime, String endTime) {
                setStoreTime(beginTime, endTime);
            }
        });
    }

    @OnClick(R.id.rl_store_license)
    public void onRlStoreLicenseClicked() {
        startActivity(new Intent(StoreSetUpActivity.this, BusinessQualificationActivity.class));
    }


    public void setStoreTime(String beginTime, String endTime) {
        tvStoreTime.setText(beginTime + "~" + endTime);
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(MeService.class).storeSetWorktime(UserLogic.getUser().getStore_id(), beginTime, endTime)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                UserServiceBean.LoginResponse loginResponse = UserLogic.getUser();
                loginResponse.setWork_start_time(beginTime);
                loginResponse.setWork_end_time(endTime);
                UserLogic.saveUser(loginResponse);
            }
        });
    }

//
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
