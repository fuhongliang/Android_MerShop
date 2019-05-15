package cn.ifhu.mershop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import cn.ifhu.mershop.activity.login.LoginActivity;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.net.MeService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.net.UserService;
import cn.ifhu.mershop.utils.IrReference;
import cn.ifhu.mershop.utils.StringUtils;
import cn.ifhu.mershop.utils.ToastHelper;

import static cn.ifhu.mershop.utils.Constants.DEVICETOKEN;

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
    @BindView(R.id.tv_register)
    TextView tvRegister;
    private int mCurSecond = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        ButterKnife.bind(this);

        //判断输入是否填写、填写完成才能点击按钮
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvRegister.setEnabled(checkPhoneNumber());
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvRegister.setEnabled(checkPhoneNumber());
            }
        });
        etVerification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvRegister.setEnabled(checkPhoneNumber());
            }
        });

    }

    //判断是否空
    public boolean checkPhoneNumber() {
        if (StringUtils.isEmpty(etPhone.getText().toString())) {
            return false;
        }
        if (StringUtils.isEmpty(etPassword.getText().toString())) {
            return false;
        }
        if (StringUtils.isEmpty(etVerification.getText().toString())) {
            return false;
        }

        return true;
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
                        mCurSecond = 60;
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
        RetrofitAPIManager.create(UserService.class).memberRegister(etPhone.getText().toString().replaceAll(" ", ""), etPassword.getText().toString(), etVerification.getText().toString(), "1", IrReference.getInstance().getString(DEVICETOKEN, ""))
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<Object> t) throws Exception {
                ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                finish();
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
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.ll_agreement)
    public void onLlAgreementClicked() {
        WebViewActivity.startLoadAssetsHtml(RegisterActivity.this,"protocol.html","服务协议");
    }
}
