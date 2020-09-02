package com.example.billtools.base;

/**
 * Created by hejunfeng on 2020/8/25 0025
 */
public abstract class BaseMVPActivity<T extends BaseContract.BasePresenter> extends BaseActivity {
    protected T mPresenter;
    protected abstract T bindPresenter();

    @Override
    protected void processLogic() {
        attachView(bindPresenter());
    }

    private void attachView(T presenter){
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
