package cn.ifhu.mershop.bean;

public class ValuePostBean {
    int voucher_id;
    int store_id;
    String title;
    String mianzhi;
    String limit_price;


    String describe;
    String end_time;

    int total_nums;
    int each_limit;

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMianzhi(String mianzhi) {
        this.mianzhi = mianzhi;
    }

    public void setLimit_price(String limit_price) {
        this.limit_price = limit_price;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setTotal_nums(int total_nums) {
        this.total_nums = total_nums;
    }

    public void setEach_limit(int each_limit) {
        this.each_limit = each_limit;
    }
}
