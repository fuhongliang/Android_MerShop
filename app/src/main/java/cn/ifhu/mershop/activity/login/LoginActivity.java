package cn.ifhu.mershop.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.activity.MainActivity;
import cn.ifhu.mershop.activity.RegisterActivity;
import cn.ifhu.mershop.activity.WebViewActivity;
import cn.ifhu.mershop.activity.me.SearchBluetoothActivity;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.utils.StringUtils;
import cn.ifhu.mershop.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    LoginContract.Presenter mPresenter;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;

    TextView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        new LoginPresenter(this);
        mPresenter.start();
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> {
            setLoadingMessageIndicator(true);
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (mPresenter.checkPhoneNumber(phone, password)) {
                mPresenter.loginWithCode(phone, password);
            } else {
                setLoadingMessageIndicator(false);
            }
        });

        if (UserLogic.isLogin()) {
            loginSuccess();
        }
        TextView register = findViewById(R.id.tv_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnLogin.setEnabled(checkPhoneNumber());
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
                btnLogin.setEnabled(checkPhoneNumber());
            }
        });

    }

    public boolean checkPhoneNumber() {
        if (StringUtils.isEmpty(etPhone.getText().toString())) {
            return false;
        }
        if (StringUtils.isEmpty(etPassword.getText().toString())) {
            return false;
        }

        return true;
    }


    @Override
    public void initData() {
        showLastTimePhoneNumber();
    }

    @Override
    public void showLastTimePhoneNumber() {

    }


    @Override
    public void showToast(String msg) {

    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @OnClick(R.id.ll_agreement)
    public void onLlAgreementClicked() {
        WebViewActivity.startLoadAssetsHtml(LoginActivity.this, "protocol.html", "服务协议");
    }
}
