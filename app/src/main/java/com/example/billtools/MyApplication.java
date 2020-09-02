package com.example.billtools;

import android.app.Application;
import android.content.Context;

import com.example.billtools.model.been.remote.MyUser;
import com.example.billtools.utils.OSCSharedPreference;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by hejunfeng on 2020/8/22 0022
 */
public class MyApplication extends Application {

    public static MyApplication application;
    private static MyUser currentUser;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        application = this;
        Bmob.initialize(this, "bdf6c94f3e359f73b0d0a453679a96c6");
        currentUser = BmobUser.getCurrentUser(MyUser.class);
        context = getApplicationContext();
        OSCSharedPreference.init(this, "setting");
    }

    /**
     * @description 获取上下文
     * @author hejunfeng
     * @time 2020/8/25 0025
     */
    public static Context getContext(){
        return context;
    }

    /**
     * @description 获取用户id
     * @author hejunfeng
     * @time 2020/8/25 0025 
     */
    public static String getCurrentUserId(){
        currentUser = BmobUser.getCurrentUser(MyUser.class);
        if (currentUser == null){
            return null;
        }
        return currentUser.getObjectId();
    }
}
