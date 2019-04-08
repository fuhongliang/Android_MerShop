package cn.ifhu.mershop.bean;

import java.util.List;

public class ProductManageBean {


    private List<ClassListBean> class_list;
    private List<GoodsListBean> goods_list;

    public List<ClassListBean> getClass_list() {
        return class_list;
    }

    public void setClass_list(List<ClassListBean> class_list) {
        this.class_list = class_list;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class ClassListBean {
        /**
         * stc_id : 1
         * stc_name : 美食
         */

        private int stc_id;
        private String stc_name;

        public int getStc_id() {
            return stc_id;
        }

        public void setStc_id(int stc_id) {
            this.stc_id = stc_id;
        }

        public String getStc_name() {
            return stc_name;
        }

        public void setStc_name(String stc_name) {
            this.stc_name = stc_name;
        }
    }

    public static class GoodsListBean {
        /**
         * goods_id : 100002
         * goods_name : fdsfdsf
         * goods_price : 1.00
         * goods_marketprice : 1.00
         * goods_salenum : 0
         * goods_storage : 1011
         */

        private int goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_marketprice;
        private int goods_salenum;
        private int goods_storage;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_marketprice() {
            return goods_marketprice;
        }

        public void setGoods_marketprice(String goods_marketprice) {
            this.goods_marketprice = goods_marketprice;
        }

        public int getGoods_salenum() {
            return goods_salenum;
        }

        public void setGoods_salenum(int goods_salenum) {
            this.goods_salenum = goods_salenum;
        }

        public int getGoods_storage() {
            return goods_storage;
        }

        public void setGoods_storage(int goods_storage) {
            this.goods_storage = goods_storage;
        }
    }
}
