package cn.ifhu.mershop.bean;

public class NoticeDetailBean {

    /**
     * sm_id : 70
     * sm_content : 您有一条新的店铺消费记录，金额：5，操作人：yali，备注：购买满即送。
     * sm_addtime : 2019-04-25 15:58:56
     */

    private int sm_id;
    private String sm_content;
    private String sm_addtime;

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

    public String getSm_addtime() {
        return sm_addtime;
    }

    public void setSm_addtime(String sm_addtime) {
        this.sm_addtime = sm_addtime;
    }
}
