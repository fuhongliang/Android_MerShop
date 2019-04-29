package cn.ifhu.mershop.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.net.MeService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.net.UserService;
import cn.ifhu.mershop.utils.StringUtils;
import cn.ifhu.mershop.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_verification)
    EditText etVerification;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    private int mCurSecond = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        ButterKnife.bind(this);
    }


    public void getVerifyCode() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(MeService.class).getSms(etPhone.getText().toString().replaceAll(" ", ""))
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                showCountDown();
            }
        });
    }

    private void showCountDown() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(
                () -> {
                    mCurSecond--;
                    if (mCurSecond < 1) {
                        runOnUiThread(() -> {
                            tvGetCode.setText("获取验证码");
                            tvGetCode.setClickable(true);
                            executor.shutdown();
                        });
                        mCurSecond = 10;
                    } else {
                        String sencond = getString(R.string.get_code_again, mCurSecond);
                        runOnUiThread(() -> {
                            tvGetCode.setText(sencond);
                            tvGetCode.setClickable(false);
                        });
                    }
                },
                0,
                1000,
                TimeUnit.MILLISECONDS);
    }


    public void memberRegister() {
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(UserService.class).memberRegister(etPhone.getText().toString().replaceAll(" ", ""), etPassword.getText().toString(), etVerification.getText().toString())
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();

            }
        });
    }

    public boolean checkContent(boolean isCheckCode) {
        if (StringUtils.isEmpty(etPhone.getText().toString().replaceAll(" ", ""))) {
            ToastHelper.makeText("请输入手机号码").show();
            return false;
        }

        if (StringUtils.isEmpty(etPassword.getText().toString().replaceAll(" ", ""))) {
            ToastHelper.makeText("请输入密码").show();
            return false;
        }

        if (isCheckCode) {
            if (StringUtils.isEmpty(etVerification.getText().toString().replaceAll(" ", ""))) {
                ToastHelper.makeText("请输入验证码").show();
                return false;
            }
        }
        return true;
    }

    @OnClick(R.id.tv_get_code)
    public void onTvGetCodeClicked() {
        if (checkContent(false)) {
            getVerifyCode();
        }
    }

    @OnClick(R.id.tv_register)
    public void onBtnLoginClicked() {
        if (checkContent(true)) {
            memberRegister();
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
