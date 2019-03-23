package cn.ifhu.bean;

/**
 * @author fuhongliang
 */
public class BaseEntity<T> {

    private static int SUCCESS_CODE = 200;
    private static int TOKENTIMEOUT_CODE = 1000;
    private int code = 0;
    private String msg;
    private T data;


    public boolean isSuccess() {
        if (code == SUCCESS_CODE) {
            return true;
        }
        return false;
    }

    public boolean isTokenTimeOut() {
        if (code == TOKENTIMEOUT_CODE) {
            return true;
        }
        return false;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

