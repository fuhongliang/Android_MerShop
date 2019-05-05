package cn.ifhu.mershop.net;

import java.util.List;

import cn.ifhu.mershop.bean.AccoutInformationBean;
import cn.ifhu.mershop.bean.AddGoodsBean;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.DiscountBean;
import cn.ifhu.mershop.bean.DiscountInfoBean;
import cn.ifhu.mershop.bean.DiscountPackageBean;
import cn.ifhu.mershop.bean.DiscountPackageInfoBean;
import cn.ifhu.mershop.bean.DiscountPackagePostBean;
import cn.ifhu.mershop.bean.DiscountPostBean;
import cn.ifhu.mershop.bean.EditGoodsBean;
import cn.ifhu.mershop.bean.FinanceBean;
import cn.ifhu.mershop.bean.FullCutBean;
import cn.ifhu.mershop.bean.FullCutPostBean;
import cn.ifhu.mershop.bean.JSBean;
import cn.ifhu.mershop.bean.OperationBean;
import cn.ifhu.mershop.bean.ProductManageBean;
import cn.ifhu.mershop.bean.ReleaseBankBean;
import cn.ifhu.mershop.bean.ReviewBean;
import cn.ifhu.mershop.bean.ValueBean;
import cn.ifhu.mershop.bean.ValuePostBean;
import cn.ifhu.mershop.bean.VouCherBean;
import cn.ifhu.mershop.bean.VouCherInfoBean;
import cn.ifhu.mershop.bean.WithDrawBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @POST("edit_goods")
    public Observable<BaseEntity<Object>> editGoods(@Body EditGoodsBean editGoodsBean);

    @FormUrlEncoded
    @POST("sort_goods_class")
    public Observable<BaseEntity<List<ProductManageBean.ClassListBean>>> sortGoodsClass(@Field("class_ids") String sortCategoryBean, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("get_store_com")
    public Observable<BaseEntity<ReviewBean>> getStoreReviews(@Field("store_id") int store_id);


    @FormUrlEncoded
    @POST("get_store_com")
    public Observable<BaseEntity<ReviewBean>> getNoReplyReviews(@Field("store_id") int store_id, @Field("no_com") int no_com);


    @FormUrlEncoded
    @POST("store_feedback")
    public Observable<BaseEntity<Object>> storeFeedback(@Field("store_id") int store_id, @Field("content") String content, @Field("parent_id") int parent_id);

    @FormUrlEncoded
    @POST("store_yunying")
    public Observable<BaseEntity<OperationBean>> storeYunying(@Field("store_id") int store_id);


    @FormUrlEncoded
    @POST("del_goods_class")
    public Observable<BaseEntity<List<Object>>> delGoodsClass(@Field("class_id") int class_id, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("chgoods_state")
    public Observable<BaseEntity<Object>> chGoodsState(@Field("goods_id") int goods_id, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("del_goods")
    public Observable<BaseEntity<Object>> delGoods(@Field("goods_id") int goods_id, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("xianshi_list")
    public Observable<BaseEntity<List<DiscountBean>>> getDiscountList(@Field("store_id") int store_id);


    @POST("xianshi_edit")
    public Observable<BaseEntity<Object>> xianshiAddOrEdit(@Body DiscountPostBean discountPostBean);


    @FormUrlEncoded
    @POST("xianshi_del")
    public Observable<BaseEntity<Object>> delDiscount(@Field("xianshi_id") String xianshi_id, @Field("store_id") String store_id);



    @FormUrlEncoded
    @POST("xianshi_info")
    public Observable<BaseEntity<DiscountInfoBean>> getDiscountInfo(@Field("xianshi_id") String xianshi_id, @Field("store_id") String store_id);


    @FormUrlEncoded
    @POST("mamsong_list")
    public Observable<BaseEntity<List<FullCutBean>>> getFullCutList(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("mamsong_del")
    public Observable<BaseEntity<Object>> delFullCut(@Field("mansong_id") String xianshi_id, @Field("store_id") String store_id);



    @POST("mamsong_edit")
    public Observable<BaseEntity<Object>> mamsongEditOrAdd(@Body FullCutPostBean fullCutPostBean);

    @FormUrlEncoded
    @POST("bundling_list")
    public Observable<BaseEntity<List<DiscountPackageBean>>> getDiscountPackageList(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("bundling_info")
    public Observable<BaseEntity<DiscountPackageInfoBean>> getDiscountPackageinfo(@Field("bundling_id") String bundling_id, @Field("store_id") String store_id);

    @POST("bundling_edit")
    public Observable<BaseEntity<Object>> AddOrEditDiscountPackage(@Body DiscountPackagePostBean discountPackagePostBean);


    @FormUrlEncoded
    @POST("bundling_del")
    public Observable<BaseEntity<Object>> delDiscountPackage(@Field("bundling_id") String bundling_id, @Field("store_id") String store_id);

    @POST("mianzhi_list")
    public Observable<BaseEntity<List<ValueBean>>> getMianzhiList();


    @POST("voucher_edit")
    public Observable<BaseEntity<Object>> voucherEdit(@Body ValuePostBean valuePostBean);


    @FormUrlEncoded
    @POST("voucher_list")
    public Observable<BaseEntity<List<VouCherBean>>> getVoucherList(@Field("store_id") String store_id);

    @FormUrlEncoded
    @POST("voucher_info")
    public Observable<BaseEntity<VouCherInfoBean>> getVoucherInfo(@Field("voucher_id") String voucher_id, @Field("store_id") String store_id);


    @FormUrlEncoded
    @POST("voucher_del")
    public Observable<BaseEntity<Object>> delVouCher(@Field("voucher_id") String voucher_id, @Field("store_id") String store_id);


    @FormUrlEncoded
    @POST("add_xianshi_quota")
    public Observable<BaseEntity<Object>> buyDiscount_quota(@Field("month") int month, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("add_mansong_quota")
    public Observable<BaseEntity<Object>> buy_mansong_quota(@Field("month") int month, @Field("store_id") int store_id);


    @FormUrlEncoded
    @POST("add_bundling_quota")
    public Observable<BaseEntity<Object>> buy_bundling_quota(@Field("month") int month, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("add_voucher_quota")
    public Observable<BaseEntity<Object>> buy_voucher_quota(@Field("month") int month, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("pd_cash_list")
    public Observable<BaseEntity<WithDrawBean>> pdCashList(@Field("keyword") String keyword, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("all_store_jiesuan")
    public Observable<BaseEntity<JSBean>> allStoreJiesuan(@Field("keyword") String keyword, @Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("xianshi_goods_list")
    public Observable<BaseEntity<ProductManageBean>> getXianshiGoodsList(@Field("store_id") int store_id, @Field("class_id") int class_id);

    @FormUrlEncoded
    @POST("add_bank_account")
    public Observable<BaseEntity<Object>> addBankAccount(@Field("store_id") String store_id, @Field("account_name") String account_name,@Field("account_number") String account_number,@Field("bank_name") String bank_name,@Field("bank_type") String bank_type);

    @FormUrlEncoded
    @POST("bank_account_list")
    public Observable<BaseEntity<ReleaseBankBean>> bankAccountList(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("del_bank_account")
    public Observable<BaseEntity<Object>> delBankAccount(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("store_jiesuan")
    public Observable<BaseEntity<FinanceBean>> storeJiesuan(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("bank_account_info")
    public Observable<BaseEntity<AccoutInformationBean>> bankAccountInfo(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("pd_cash_add")
    public Observable<BaseEntity<Object>>pdCashAdd(@Field("store_id") String store_id,@Field("money") String money);


}
