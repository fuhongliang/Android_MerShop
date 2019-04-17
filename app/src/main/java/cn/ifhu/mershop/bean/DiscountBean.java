package cn.ifhu.mershop.bean;

/**
 * @author fuhongliang
 */
public class DiscountBean {

    /**
     * xianshi_name : 古典诗歌的法国
     * start_time : 0
     * end_time : 0
     * state : 1
     */

    private String xianshi_name;
    private int start_time;
    private int end_time;
    private int state;

    public String getXianshi_name() {
        return xianshi_name;
    }

    public void setXianshi_name(String xianshi_name) {
        this.xianshi_name = xianshi_name;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
