package cn.ifhu.net;

import java.util.ArrayList;

import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.OrderBean;
import cn.ifhu.bean.UserServiceBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface OrderService {
    @FormUrlEncoded
    @POST("get_neworder")
    public Observable<BaseEntity<ArrayList<OrderBean>>> getNewOrder(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("receive_order")
    public Observable<BaseEntity<ArrayList<OrderBean>>> receiveOrder(@Field("order_id") String order_id);

}
