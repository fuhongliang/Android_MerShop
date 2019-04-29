package cn.ifhu.mershop.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.ifhu.mershop.bean.OrderBean;
import cn.ifhu.mershop.bean.ProductManageBean;
import cn.ifhu.mershop.bean.SellingTime;

import static cn.ifhu.mershop.utils.Constants.CLASSLIST;
import static cn.ifhu.mershop.utils.Constants.DISCOUNTPRODUCTLIST;
import static cn.ifhu.mershop.utils.Constants.ORDERPRINTING;
import static cn.ifhu.mershop.utils.Constants.SELLINGTIME;

/**
 * @author fuhongliang
 */
public class OrderLogic {

    public static void savePrintingOrder(OrderBean orderBean) {
        if (orderBean != null) {
            Gson gson = new Gson();
            String json = gson.toJson(orderBean);
            IrReference.getInstance().saveString(ORDERPRINTING, json);
        }
    }


    public static OrderBean getPrintingOrder() throws Exception {
        String json = IrReference.getInstance().getString(ORDERPRINTING, "");
        if (!TextUtils.isEmpty(json)) {
            OrderBean orderBean = GsonUtils.fromJson(json,OrderBean.class);
            return orderBean;
        }
        return null;
    }
}
