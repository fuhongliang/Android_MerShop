package cn.ifhu.mershop.net;

import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.UserServiceBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface UserService {
    @POST("member_login")
    public Observable<BaseEntity<UserServiceBean.LoginResponse>> login(@Body UserServiceBean.LoginForm loginForm);

    @FormUrlEncoded
    @POST("check_mobile")
    public Observable<BaseEntity<Object>> checkMobile(@Field("mobile") String  mobile);

    @FormUrlEncoded
    @POST("member_register")
    public Observable<BaseEntity<Object>> memberRegister(@Field("mobile") String  mobile,@Field("password") String  password,@Field("verify_code") String  verify_code,String app_type,String device_tokens);

}
