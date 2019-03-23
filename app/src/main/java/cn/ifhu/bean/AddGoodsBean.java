package cn.ifhu.bean;

import java.util.List;

public class AddGoodsBean {
    int store_id;
    int class_id;
    String goods_name;
    double goods_price;
    double origin_price;
    int goods_storage;
    List<SellTime> sell_time;
    String goods_desc;

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public double getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(double origin_price) {
        this.origin_price = origin_price;
    }

    public int getGoods_storage() {
        return goods_storage;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    public List<SellTime> getSell_time() {
        return sell_time;
    }

    public void setSell_time(List<SellTime> sell_time) {
        this.sell_time = sell_time;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    class  SellTime{
        String start_time;
        String end_time;

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
    }
}
