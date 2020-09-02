package com.example.billtools.model.been.local;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author hejunfeng
 * @Date 11:24 2020/8/29 0029
 * @Description 支付方式
 **/
@Entity
public class BPay {
    @Id
    private Long id;
    private String payName;
    private String payImg;
    private Float inCome;
    private Float outCome;

    @Generated(hash = 577580253)
    public BPay(Long id, String payName, String payImg, Float inCome,
            Float outCome) {
        this.id = id;
        this.payName = payName;
        this.payImg = payImg;
        this.inCome = inCome;
        this.outCome = outCome;
    }

    @Generated(hash = 48271616)
    public BPay() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPayName() {
        return this.payName;
    }
    public void setPayName(String payName) {
        this.payName = payName;
    }
    public String getPayImg() {
        return this.payImg;
    }
    public void setPayImg(String payImg) {
        this.payImg = payImg;
    }
    public Float getInCome() {
        return this.inCome;
    }
    public void setInCome(Float inCome) {
        this.inCome = inCome;
    }
    public Float getOutCome() {
        return this.outCome;
    }
    public void setOutCome(Float outCome) {
        this.outCome = outCome;
    }
}
