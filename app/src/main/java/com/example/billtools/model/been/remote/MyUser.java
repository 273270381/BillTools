package com.example.billtools.model.been.remote;

import cn.bmob.v3.BmobUser;

/**
 * Created by hejunfeng on 2020/8/25 0025
 */
public class MyUser extends BmobUser {

    private String image;

    private String gender;

    private String age;

    public String getImage() {
        return image;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
