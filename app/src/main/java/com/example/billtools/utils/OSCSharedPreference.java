package com.example.billtools.utils;

import android.content.Context;

/**
 * Created by hejunfeng on 2020/8/25 0025
 */
public final class OSCSharedPreference extends SharedPreferenceUtil{

    private static OSCSharedPreference mInstance;

    public static void init(Context context,String name){
        if (mInstance == null){
            mInstance = new OSCSharedPreference(context, name);
        }
    }

    public static OSCSharedPreference getInstance(){
        return mInstance;
    }

    public OSCSharedPreference(Context context, String name) {
        super(context, name);
    }

    public String getCurrentTheme(){
        return getString("theme", "原谅绿");
    }

    public void putCurrentTheme(String theme){
        put("theme", theme);
    }

    public void putUserName(String name){
        put("userName", name);
    }

    public String getUserName(){
        return getString("userName", "");
    }

    public void putPassWord(String password){
        put("passWord", password);
    }

    public String getPassword(){
        return getString("passWord","");
    }

    public void setFirstStart(){
        put("first", false);
    }

    public boolean isFirstStart(){
        boolean isFirst = getBoolean("first", true);
        if (isFirst){
            setFirstStart();
        }
        return isFirst;
    }
}
