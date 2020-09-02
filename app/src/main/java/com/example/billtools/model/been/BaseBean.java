package com.example.billtools.model.been;

/**
 * Created by hejunfeng on 2017/10/16 0016.
 * 通用javabean
 */

public class BaseBean {
    /**
     * status : 100
     * message : 成功！
     */

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}