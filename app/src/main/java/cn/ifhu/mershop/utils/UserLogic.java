package cn.ifhu.mershop.utils;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import cn.ifhu.mershop.bean.UserServiceBean;
import static cn.ifhu.mershop.utils.Constants.USER;
public class UserLogic {

    public static void saveUser(UserServiceBean.LoginResponse dataBean) {
        Logger.d(dataBean);
        if (dataBean != null) {
            Logger.d("saveUser"+dataBean.getStore_id());
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            IrReference.getInstance().saveString(USER, json);
            Logger.d("saveUser"+json);
        }
    }


    public static UserServiceBean.LoginResponse getUser() {
        String json = IrReference.getInstance().getString(USER, "");
        Logger.d("getUser"+json);
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            UserServiceBean.LoginResponse mUser = gson.fromJson(json, UserServiceBean.LoginResponse.class);
            return mUser;
        }
        return null;
    }

    public static boolean isLogin() {
        if (getUser() == null) {
            return false;
        } else {
            return true;
        }
    }

    public static void loginOut() {
        IrReference.getInstance().saveString(USER, "");
    }

}
