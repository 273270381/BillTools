package com.example.billtools.presenter;

import com.example.billtools.base.RxPresenter;
import com.example.billtools.model.been.remote.MyUser;
import com.example.billtools.presenter.contract.RegisterContract;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by hejunfeng on 2020/8/26 0026
 */
public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {
    @Override
    public void register(String username, String password, String email) {
        MyUser myUser =new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(password);
        myUser.setEmail(email);
        if (getView() != null){
            getView().showLoading();
        }
        myUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser user, BmobException e) {
                if (getView() != null){
                    if (e == null){
                        getView().registerSuccess(user);
                    }else{
                        getView().registerFailure(e);
                    }
                }
            }
        });
    }
}
