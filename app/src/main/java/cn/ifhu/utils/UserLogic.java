package cn.ifhu.utils;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.stetho.common.StringUtil;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import cn.ifhu.bean.UserServiceBean;

import static cn.ifhu.utils.Constants.TOKEN;
import static cn.ifhu.utils.Constants.USER;

public class UserLogic {

    public static void saveUser(UserServiceBean.LoginResponse.DataBean dataBean) {
        if (dataBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            IrReference.getInstance().saveString(USER, json);
        }
    }

    /**
     * 获取link user用户信息
     *
     * @return
     */
    public static UserServiceBean.LoginResponse.DataBean getUser() {
        String json = IrReference.getInstance().getString(USER, "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            UserServiceBean.LoginResponse.DataBean mUser = gson.fromJson(json, UserServiceBean.LoginResponse.DataBean.class);
            return mUser;
        }
        return new UserServiceBean.LoginResponse.DataBean();
    }

    public static boolean isLogin() {
        if (TextUtils.isEmpty(IrReference.getInstance().getString(TOKEN, ""))
                || getUser() == null) {
            return false;
        } else {
            return true;
        }
    }


}
