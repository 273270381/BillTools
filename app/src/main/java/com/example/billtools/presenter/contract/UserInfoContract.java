package com.example.billtools.presenter.contract;

import com.example.billtools.base.BaseContract;
import com.example.billtools.model.been.remote.MyUser;

/**
 * Created by hejunfeng on 2020/8/28 0028
 */
public interface UserInfoContract extends BaseContract {
    interface View extends BaseView{
        void updateSuccess(MyUser user);

        void updateFailure(Throwable e);
    }

    interface Presenter extends BasePresenter<View>{
        void updateUser(MyUser user);
    }
}
