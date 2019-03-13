package cn.ifhu.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import cn.ifhu.utils.ToastHelper;

/**
 * @author fuhongliang
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    LoginContract.Presenter mPresenter;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_account_numbe)
    TextView tvAccountNumbe;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        new LoginPresenter(this);
        mPresenter.start();
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
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        setLoadingMessageIndicator(true);
        new Handler().postDelayed(() -> {
            ToastHelper.makeText("登录成功！", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        },1000);

    }
}
