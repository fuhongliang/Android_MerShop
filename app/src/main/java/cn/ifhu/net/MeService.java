package cn.ifhu.net;

import java.util.ArrayList;

import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.OrderBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface MeService {

    @FormUrlEncoded
    @POST("store_msg_feedback")
    public Observable<BaseEntity<Object>> storeMsgFeedback(@Field("store_id") int store_id,@Field("content") String content,@Field("type") int type);

    @FormUrlEncoded
    @POST("store_set_workstate")
    public Observable<BaseEntity<Object>> storeSetWorkstate(@Field("store_id") int store_id,@Field("store_state") int store_state);

}
