package com.example.billtools.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * @Author hejunfeng
 * @Date 9:03 2020/8/31 0031
 * @Description com.example.billtools.base
 **/
public abstract class BaseMVPFragment<T extends BaseContract.BasePresenter> extends BaseFragment implements BaseContract.BaseView {

    protected T mPresenter;

    protected abstract T bindPresenter();


    @Override
    protected void processLogic() {
        mPresenter = bindPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

}
