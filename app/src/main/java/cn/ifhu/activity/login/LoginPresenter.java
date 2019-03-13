package cn.ifhu.activity.login;


import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;

import cn.ifhu.base.BaseObserver;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.UserServiceBean;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.net.UserService;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * @author fuhongliang
 */
public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View loginView;

    @SuppressLint("RestrictedApi")
    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = checkNotNull(loginView, "loginView cannot be null!");
        this.loginView.setPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void start() {
        loginView.initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean checkPhoneNumber(String phoneNo) {
        return true;
    }

    @Override
    public void loginWithCode(String phone, String code) {
        UserServiceBean.LoginForm loginForm = new UserServiceBean.LoginForm();
        loginForm.setMobile(Long.parseLong(phone));
        loginForm.setCode(Integer.parseInt(code));
        RetrofitAPIManager.create(UserService.class).login(loginForm)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver(true) {
            @Override
            protected void onApiComplete() {

            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

}
