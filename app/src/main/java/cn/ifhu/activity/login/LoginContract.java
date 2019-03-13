package cn.ifhu.activity.login;


import cn.ifhu.base.BasePresenter;
import cn.ifhu.base.BaseView;

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
         * 是否显示加载提示
         * @param active 是否激活
         */
        void setLoadingMessageIndicator(boolean active);

        /**
         * 提示信息
         * @param msg 内容
         */
        void showToast(String msg);

    }

    interface Presenter extends BasePresenter {
        boolean checkPhoneNumber(String phoneNo);
        void loginWithCode(String phone, String code);
    }
}
