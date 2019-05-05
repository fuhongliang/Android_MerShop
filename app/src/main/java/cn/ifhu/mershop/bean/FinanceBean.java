package cn.ifhu.mershop.bean;

public class FinanceBean {


    /**
     * y_jiesuan : 0
     * d_jiesuan : 0
     * list : {"amount":0,"state":"1","ob_no":2019024,"os_month":201902}
     * account : {"bank_type":null,"account_number":"13121454125"}
     */

    private int y_jiesuan;
    private int d_jiesuan;
    private ListBean list;
    private AccountBean account;
    public int getY_jiesuan() {
        return y_jiesuan;
    }

    public void setY_jiesuan(int y_jiesuan) {
        this.y_jiesuan = y_jiesuan;
    }

    public int getD_jiesuan() {
        return d_jiesuan;
    }

    public void setD_jiesuan(int d_jiesuan) {
        this.d_jiesuan = d_jiesuan;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public static class ListBean {
        /**
         * amount : 0
         * state : 1
         * ob_no : 2019024
         * os_month : 201902
         */

        private int amount;
        private String state;
        private int ob_no;
        private int os_month;

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

        public int getOs_month() {
            return os_month;
        }

        public void setOs_month(int os_month) {
            this.os_month = os_month;
        }
    }

    public static class AccountBean {
        /**
         * bank_type : null
         * account_number : 13121454125
         */

        private Object bank_type;
        private String account_number;

        public Object getBank_type() {
            return bank_type;
        }

        public void setBank_type(Object bank_type) {
            this.bank_type = bank_type;
        }

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }
    }
}
