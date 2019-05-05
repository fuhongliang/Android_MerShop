package cn.ifhu.mershop.bean;

import java.util.List;

public class JSBean {

    /**
     * list : [{"amount":0,"state":"1","ob_no":2019024,"os_month":"02"},{"amount":0,"state":"1","ob_no":2019034,"os_month":"03"}]
     * total_amount : 0
     */

    private int total_amount;
    private List<ListBean> list;

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * amount : 0
         * state : 1
         * ob_no : 2019024
         * os_month : 02
         */

        private int amount;
        private String state;
        private int ob_no;
        private String os_month;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getOb_no() {
            return ob_no;
        }

        public void setOb_no(int ob_no) {
            this.ob_no = ob_no;
        }

        public String getOs_month() {
            return os_month;
        }

        public void setOs_month(String os_month) {
            this.os_month = os_month;
        }
    }
}
