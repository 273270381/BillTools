package com.example.billtools.presenter.contract;

import com.example.billtools.base.BaseContract;
import com.example.billtools.model.been.remote.MyUser;

/**
 * Created by hejunfeng on 2020/8/25 0025
 */
public interface LogInContract  {
    interface View extends BaseContract.BaseView{
        void loginSuccess(MyUser user);

        void loginFailure(Throwable e);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void login(String userName, String passWord);
    }
}
