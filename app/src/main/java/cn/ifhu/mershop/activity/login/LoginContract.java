package cn.ifhu.mershop.activity.login;


import cn.ifhu.mershop.base.BasePresenter;
import cn.ifhu.mershop.base.BaseView;

/**
 * @author fuhongliang
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        /**
         * 页面显示后初始化操作入口
         */
        void initData();

        /**
         * 显示最后一次登录的手机号码
         */
        void showLastTimePhoneNumber();

        /**
         * 提示信息
         * @param msg 内容
         */
        void showToast(String msg);

        void setLoadingMessageIndicator(boolean indicator);

        void loginSuccess();
    }

    interface Presenter extends BasePresenter {
        boolean checkPhoneNumber(String phoneNo,String passWord);
        void loginWithCode(String phone, String code);
    }
}
