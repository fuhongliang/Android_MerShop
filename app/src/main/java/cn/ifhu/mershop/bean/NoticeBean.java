package cn.ifhu.mershop.bean;

public class NoticeBean {


    /**
     * sm_id : 18
     * sm_content : 您有订单需要处理，订单编号：2000000000004601。
     */

    private int sm_id;
    private String sm_content;

    public int getSm_id() {
        return sm_id;
    }

    public void setSm_id(int sm_id) {
        this.sm_id = sm_id;
    }

    public String getSm_content() {
        return sm_content;
    }

    public void setSm_content(String sm_content) {
        this.sm_content = sm_content;
    }
}
