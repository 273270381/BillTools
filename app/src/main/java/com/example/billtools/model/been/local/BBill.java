package com.example.billtools.model.been.local;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author hejunfeng
 * @Date 13:45 2020/8/29 0029
 * @Description 账单been
 **/
@Entity
public class BBill {

    @Id(autoincrement = true)
    private Long id;  //本地id

    private String rid;  //服务器端id
    private float cost;  //金额
    private String content;  //内容
    private String userid;  //用户id
    private String payName;  //支付方式
    private String payImg;  //
    private String sortName;  //账单分类
    private String sortImg;  //
    private long crdate;  //创建时间
    private boolean income;  //收入支出
    private int version;  //版本
    @Generated(hash = 634586034)
    public BBill(Long id, String rid, float cost, String content, String userid,
            String payName, String payImg, String sortName, String sortImg,
            long crdate, boolean income, int version) {
        this.id = id;
        this.rid = rid;
        this.cost = cost;
        this.content = content;
        this.userid = userid;
        this.payName = payName;
        this.payImg = payImg;
        this.sortName = sortName;
        this.sortImg = sortImg;
        this.crdate = crdate;
        this.income = income;
        this.version = version;
    }
    @Generated(hash = 124482664)
    public BBill() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRid() {
        return this.rid;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }
    public float getCost() {
        return this.cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUserid() {
        return this.userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
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
    public String getSortName() {
        return this.sortName;
    }
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
    public String getSortImg() {
        return this.sortImg;
    }
    public void setSortImg(String sortImg) {
        this.sortImg = sortImg;
    }
    public long getCrdate() {
        return this.crdate;
    }
    public void setCrdate(long crdate) {
        this.crdate = crdate;
    }
    public boolean getIncome() {
        return this.income;
    }
    public void setIncome(boolean income) {
        this.income = income;
    }
    public int getVersion() {
        return this.version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
}
