package com.example.billtools.model.been.local;

import org.greenrobot.greendao.annotation.Entity;

import cn.bmob.v3.BmobObject;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by hejunfeng on 2020/8/22 0022
 */
@Entity
public class BSort extends BmobObject {

    @Id(autoincrement = true)
    @Unique
    private Long id;

    private String sortName;
    private String sortImg;
    private int priority;
    private float cost;
    private Boolean income;
    @Generated(hash = 442114462)
    public BSort(Long id, String sortName, String sortImg, int priority, float cost,
            Boolean income) {
        this.id = id;
        this.sortName = sortName;
        this.sortImg = sortImg;
        this.priority = priority;
        this.cost = cost;
        this.income = income;
    }
    @Generated(hash = 2092983496)
    public BSort() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public int getPriority() {
        return this.priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public float getCost() {
        return this.cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }
    public Boolean getIncome() {
        return this.income;
    }
    public void setIncome(Boolean income) {
        this.income = income;
    }


  }

