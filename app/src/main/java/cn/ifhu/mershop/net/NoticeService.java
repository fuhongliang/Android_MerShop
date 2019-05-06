package cn.ifhu.mershop.net;

import java.util.List;

import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.NoticeBean;
import cn.ifhu.mershop.bean.NoticeDetailBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface NoticeService {

    @FormUrlEncoded
    @POST("msg_list")
    public Observable<BaseEntity<List<NoticeBean>>> getMsgList(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("msg_info")
    public Observable<BaseEntity<NoticeDetailBean>> getMsgInfo(@Field("store_id") int store_id,@Field("sm_id") int sm_id);

}
