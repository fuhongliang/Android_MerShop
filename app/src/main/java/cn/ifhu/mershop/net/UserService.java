package cn.ifhu.mershop.net;

import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.UserServiceBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface UserService {
    @POST("member_login")
    public Observable<BaseEntity<UserServiceBean.LoginResponse>> login(@Body UserServiceBean.LoginForm loginForm);

}
