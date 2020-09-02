package com.example.billtools.presenter;

import com.example.billtools.base.RxPresenter;
import com.example.billtools.model.been.remote.MyUser;
import com.example.billtools.presenter.contract.LogInContract;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by hejunfeng on 2020/8/25 0025
 */
public class LoginPresenter extends RxPresenter<LogInContract.View> implements LogInContract.Presenter {
    @Override
    public void login(String userName, String passWord) {
        if (getView() != null){
            getView().showLoading("正在登陆...");
        }
        MyUser.loginByAccount(userName, passWord, new LogInListener<MyUser>() {
            @Override
            public void done(MyUser user, BmobException e) {
                if (getView() != null){
                    if (e == null){
                        getView().loginSuccess(user);
                    }else {
                        getView().loginFailure(e);
                    }
                }
            }
        });
    }
}
