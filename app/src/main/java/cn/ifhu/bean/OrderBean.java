package cn.ifhu.bean;

import java.util.List;

public class OrderBean {

    /**
     * order_id : 8
     * order_sn : 2000000000000801
     * buyer_id : 4
     * add_time : 2019-03-14 14:46:53
     * extend_order_common : {"phone":"17189183213","address":"河南\t南阳市\t邓州市 刘集乡","reciver_name":"张三的收货地址"}
     * extend_order_goods : [{"goods_name":"溪秀气垫CC霜 保湿补水遮瑕裸妆粉底液持久控油隔离BB霜","goods_price":"128.00","goods_num":2,"commis_rate":0,"goods_pay_price":"256.00"},{"goods_name":"溪秀面膜补水保湿冰膜冰感透肌舒缓蚕丝面膜护肤套装男女","goods_price":"68.00","goods_num":2,"commis_rate":0,"goods_pay_price":"136.00"}]
     * total_price : 392
     * commis_price : 0
     * goods_pay_price : 392
     */

    private int order_id;
    private long order_sn;
    private int buyer_id;
    private String add_time;
    private ExtendOrderCommonBean extend_order_common;
    private int total_price;
    private int commis_price;
    private int goods_pay_price;
    private List<ExtendOrderGoodsBean> extend_order_goods;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public long getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(long order_sn) {
        this.order_sn = order_sn;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public ExtendOrderCommonBean getExtend_order_common() {
        return extend_order_common;
    }

    public void setExtend_order_common(ExtendOrderCommonBean extend_order_common) {
        this.extend_order_common = extend_order_common;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getCommis_price() {
        return commis_price;
    }

    public void setCommis_price(int commis_price) {
        this.commis_price = commis_price;
    }

    public int getGoods_pay_price() {
        return goods_pay_price;
    }

    public void setGoods_pay_price(int goods_pay_price) {
        this.goods_pay_price = goods_pay_price;
    }

    public List<ExtendOrderGoodsBean> getExtend_order_goods() {
        return extend_order_goods;
    }

    public void setExtend_order_goods(List<ExtendOrderGoodsBean> extend_order_goods) {
        this.extend_order_goods = extend_order_goods;
    }

    public static class ExtendOrderCommonBean {
        /**
         * phone : 17189183213
         * address : 河南	南阳市	邓州市 刘集乡
         * reciver_name : 张三的收货地址
         */

        private String phone;
        private String address;
        private String reciver_name;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getReciver_name() {
            return reciver_name;
        }

        public void setReciver_name(String reciver_name) {
            this.reciver_name = reciver_name;
        }
    }

    public static class ExtendOrderGoodsBean {
        /**
         * goods_name : 溪秀气垫CC霜 保湿补水遮瑕裸妆粉底液持久控油隔离BB霜
         * goods_price : 128.00
         * goods_num : 2
         * commis_rate : 0
         * goods_pay_price : 256.00
         */

        private String goods_name;
        private String goods_price;
        private int goods_num;
        private int commis_rate;
        private String goods_pay_price;

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

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public int getCommis_rate() {
            return commis_rate;
        }

        public void setCommis_rate(int commis_rate) {
            this.commis_rate = commis_rate;
        }

        public String getGoods_pay_price() {
            return goods_pay_price;
        }

        public void setGoods_pay_price(String goods_pay_price) {
            this.goods_pay_price = goods_pay_price;
        }
    }
}
