package cn.ifhu.mershop.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.ifhu.mershop.bean.ProductManageBean;
import cn.ifhu.mershop.bean.SellingTime;

import static cn.ifhu.mershop.utils.Constants.CLASSLIST;
import static cn.ifhu.mershop.utils.Constants.DISCOUNTPRODUCTLIST;
import static cn.ifhu.mershop.utils.Constants.SELLINGTIME;

/**
 * @author fuhongliang
 */
public class ProductLogic {

    public static void saveClass(List<ProductManageBean.ClassListBean> dataBean) {
        if (dataBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            IrReference.getInstance().saveString(CLASSLIST, json);
        }
    }


    public static List<ProductManageBean.ClassListBean> getClassList() throws Exception {
        String json = IrReference.getInstance().getString(CLASSLIST, "");
        if (!TextUtils.isEmpty(json)) {
            ArrayList<ProductManageBean.ClassListBean> classListBean = GsonUtils.fromJsonArrayToArrayList(json,ProductManageBean.ClassListBean.class);
            return classListBean;
        }
        return null;
    }

    public static void saveSellingTime(List<SellingTime> dataBean) {
        if (dataBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(dataBean);
            IrReference.getInstance().saveString(SELLINGTIME, json);
        }
    }


    public static List<SellingTime> getSellingTime() throws Exception {
        String json = IrReference.getInstance().getString(SELLINGTIME, "");
        if (!TextUtils.isEmpty(json)) {
            ArrayList<SellingTime> classListBean = GsonUtils.fromJsonArrayToArrayList(json,SellingTime.class);
            return classListBean;
        }
        return null;
    }

//保存选择的折扣商品信息
    public static void saveDiscountGoods(List<ProductManageBean.GoodsListBean> goodsListBeans) {
        if (goodsListBeans != null) {
            Gson gson = new Gson();
            String json = gson.toJson(goodsListBeans);
            IrReference.getInstance().saveString(DISCOUNTPRODUCTLIST, json);
        }
    }

    public static void clearDiscountGoods() {
        IrReference.getInstance().saveString(DISCOUNTPRODUCTLIST, "");
    }


    public static List<ProductManageBean.GoodsListBean> getDiscountGoods() throws Exception {
        String json = IrReference.getInstance().getString(DISCOUNTPRODUCTLIST, "");
        if (!TextUtils.isEmpty(json)) {
            ArrayList<ProductManageBean.GoodsListBean> goodsListBeans = GsonUtils.fromJsonArrayToArrayList(json,ProductManageBean.GoodsListBean.class);
            return goodsListBeans;
        }
        return null;
    }
}
