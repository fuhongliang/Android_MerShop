package cn.ifhu.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.activity.MainActivity;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.dialog.loading.LoadingDialog;
import cn.ifhu.utils.StringUtils;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;

/**
 * @author fuhongliang
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    LoginContract.Presenter mPresenter;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;

    Button btnLogin;

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
