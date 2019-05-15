package cn.ifhu.mershop.net;

import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.UserServiceBean;
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


    @FormUrlEncoded
    @POST("store_set_phone")
    public Observable<BaseEntity<Object>> storeSetPhone(@Field("store_id") int store_id,@Field("phone_number") String phone_number);



    @FormUrlEncoded
    @POST("store_set_desc")
    public Observable<BaseEntity<Object>> storeSetDesc(@Field("store_id") int store_id,@Field("store_desc") String store_desc);

    @FormUrlEncoded
    @POST("store_set_worktime")
    public Observable<BaseEntity<Object>> storeSetWorktime(@Field("store_id") int store_id,@Field("work_start_time") String work_start_time,@Field("work_end_time") String work_end_time);

    @FormUrlEncoded
    @POST("get_sms")
    public Observable<BaseEntity<Object>> getSms(@Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("edit_passwd")
    public Observable<BaseEntity<Object>> editPasswd(@Field("member_id") String member_id,@Field("phone_number") String phone_number,@Field("verify_code") String verify_code,@Field("new_passwd") String new_passwd,@Field("con_new_passwd") String con_new_passwd);

    @FormUrlEncoded
    @POST("store_set_desc")
    public Observable<BaseEntity<Object>> storeSetSesc(@Field("store_id") String store_id,@Field("store_desc") String store_desc);

    @FormUrlEncoded
    @POST("change_avator")
    public Observable<BaseEntity<UserServiceBean.LoginResponse>> changeAvator(@Field("store_id") String store_id, @Field("avator") String avator);

    @FormUrlEncoded
    @POST("auto_receive_order")
    public Observable<BaseEntity<Object>> autoReceiveOrder(@Field("store_id") int store_id, @Field("is_open") int is_open);

    @FormUrlEncoded
    @POST("member_logout")
    public Observable<BaseEntity<Object>> memberLogout(@Field("store_id") int store_id);


}
