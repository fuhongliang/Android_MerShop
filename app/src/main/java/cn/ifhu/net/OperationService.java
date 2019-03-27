package cn.ifhu.net;

import java.util.List;

import cn.ifhu.bean.AddGoodsBean;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.ProductManageBean;
import cn.ifhu.bean.ReviewBean;
import cn.ifhu.bean.SortCategoryBean;
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
    public Observable<BaseEntity<List<ProductManageBean.ClassListBean>>> addGoodsClass(@Field("store_id") int store_id, @Field("class_name") String class_name);

    @FormUrlEncoded
    @POST("add_goods_class")
    public Observable<BaseEntity<List<ProductManageBean.ClassListBean>>> updateGoodsClass(@Field("store_id") int store_id, @Field("class_id") int class_id, @Field("class_name") String class_name);

    @POST("add_goods")
    public Observable<BaseEntity<Object>> addGoods(@Body AddGoodsBean addGoodsBean);

    @FormUrlEncoded
    @POST("sort_goods_class")
    public Observable<BaseEntity<List<ProductManageBean.ClassListBean>>> sortGoodsClass(@Field("class_ids") String sortCategoryBean,@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("get_store_com")
    public Observable<BaseEntity<ReviewBean>> getStoreReviews(@Field("store_id") int store_id);


    @FormUrlEncoded
    @POST("store_feedback")
    public Observable<BaseEntity<Object>> storeFeedback(@Field("store_id") int store_id,@Field("content") String content,@Field("parent_id") int parent_id);


}
