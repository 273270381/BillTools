package com.example.billtools.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by hejunfeng on 2020/8/25 0025
 */
public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;
    protected CompositeDisposable mDisposable;
    protected void unSubscribe(){
        if (mDisposable != null){
            mDisposable.dispose();
        }
    }
    protected void addDisposable(Disposable disposable){
        if (mDisposable == null ){
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(disposable);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }


    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    public T getView(){
        return mView;
    }
}
