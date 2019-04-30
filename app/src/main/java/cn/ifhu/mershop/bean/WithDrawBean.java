package cn.ifhu.mershop.bean;

import java.util.List;

/**
 * @author fuhongliang
 */
public class WithDrawBean {
    /**
     * list : []
     * balance : 0.00
     * total_amount : 0
     */

//    private String balance;
//    private int total_amount;
    private List<ListBean> list;

//    public String getBalance() {
//        return balance;
//    }
//
//    public void setBalance(String balance) {
//        this.balance = balance;
//    }
//
//    public int getTotal_amount() {
//        return total_amount;
//    }
//
//    public void setTotal_amount(int total_amount) {
//        this.total_amount = total_amount;
//    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    /**
     * list : [{"amount":"6.00","payment_state":"0","add_time":1556249270,"bank_no":"123456"}]
     * balance : 9993.00
     * total_amount : 6.00
     */


    public class ListBean{
        String amount;
        String payment_state;
        long add_time;
        String bank_no;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPayment_state() {
            return payment_state;
        }

        public void setPayment_state(String payment_state) {
            this.payment_state = payment_state;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }
    }

}
