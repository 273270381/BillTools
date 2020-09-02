package com.example.billtools.presenter.contract;

import com.example.billtools.base.BaseContract;
import com.example.billtools.model.been.remote.MyUser;

/**
 * Created by hejunfeng on 2020/8/26 0026
 */
public interface RegisterContract {
    interface View extends BaseContract.BaseView{
        void registerSuccess(MyUser user);

        void registerFailure(Throwable e);
    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void register(String username, String password, String email);
    }
}
