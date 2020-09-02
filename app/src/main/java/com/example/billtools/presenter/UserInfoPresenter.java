package com.example.billtools.presenter;

import com.example.billtools.base.RxPresenter;
import com.example.billtools.model.been.remote.MyUser;
import com.example.billtools.presenter.contract.UserInfoContract;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by hejunfeng on 2020/8/28 0028
 */
public class UserInfoPresenter extends RxPresenter<UserInfoContract.View> implements UserInfoContract.Presenter {
    private String TAG = "UserInfoPresenter";
    @Override
    public void updateUser(MyUser user) {
        if (getView() != null){
            getView().showLoading("正在更新");
            user.update(user.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (getView() != null){
                        if (e == null){
                            getView().updateSuccess(user);
                        }else{
                            getView().updateFailure(e);
                        }
                    }
                }
            });
        }
    }
}
