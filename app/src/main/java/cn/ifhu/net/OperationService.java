package cn.ifhu.net;

import java.util.List;

import cn.ifhu.bean.AddGoodsBean;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.ProductManageBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author fuhongliang
 */
public interface OperationService {

    @FormUrlEncoded
    @POST("goods_list")
    public Observable<BaseEntity<ProductManageBean>> goodsList(@Field("store_id") int store_id, @Field("class_id") int class_id);


    @FormUrlEncoded
    @POST("add_goods_class")
    public Observable<BaseEntity<List<Object>>> addGoodsClass(@Field("store_id") int store_id, @Field("class_name") String class_name);

    @FormUrlEncoded
    @POST("add_goods_class")
    public Observable<BaseEntity<List<Object>>> updateGoodsClass(@Field("store_id") int store_id, @Field("class_id") int class_id, @Field("class_name") String class_name);

    @POST("add_goods")
//  public Observable<BaseEntity<Object>> addGoods(@Field("store_id") int store_id, @Field("class_id") int class_id, @Field("goods_name") String goods_name, @Field("goods_price") double goods_price, @Field("origin_price") double origin_price, @Field("goods_storage") int goods_storage);
    public Observable<BaseEntity<Object>> addGoods(@Body AddGoodsBean addGoodsBean);


}
