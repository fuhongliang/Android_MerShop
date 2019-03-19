package cn.ifhu.net;

import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.UserServiceBean;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface UserService {
    @POST("member_login")
    public Observable<BaseEntity<UserServiceBean.LoginResponse>> login(@Body UserServiceBean.LoginForm loginForm);

}
