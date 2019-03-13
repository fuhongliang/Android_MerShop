package cn.ifhu.activity.login;

import android.os.Bundle;

import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;

/**
 * @author fuhongliang
 */
public class LoginActivity extends BaseActivity implements LoginContract.View{

    LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neworder);
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
    public void setLoadingMessageIndicator(boolean active) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
