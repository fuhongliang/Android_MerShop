package cn.ifhu.mershop.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ifhu.R;
import cn.ifhu.mershop.activity.MainActivity;
import cn.ifhu.mershop.base.BaseActivity;
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

        if (UserLogic.isLogin()){
            loginSuccess();
        }
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


}
