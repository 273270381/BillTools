package com.example.billtools.presenter;


import com.example.billtools.base.BaseObserver;
import com.example.billtools.base.RxPresenter;
import com.example.billtools.model.been.local.BBill;
import com.example.billtools.model.been.remote.CoBill;
import com.example.billtools.model.been.remote.MyUser;
import com.example.billtools.model.repository.LocalRepository;
import com.example.billtools.presenter.contract.BillContract;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-01-08
 * Github: https://github.com/zas023
 */
public class BillPresenter extends RxPresenter<BillContract.View> implements BillContract.Presenter {

    private String TAG = "BillPresenter";

    @Override
    public void getBillNote() {
        //此处采用同步的方式，防止账单分类出现白块
        mView.loadDataSuccess(LocalRepository.getInstance().getBillNote());
    }

    @Override
    public void addBill(CoBill coBill) {
//        LocalRepository.getInstance().saveBBill(bBill)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<BBill>() {
//                    @Override
//                    protected void onSuccees(BBill bBill) throws Exception {
//                        mView.onSuccess();
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        mView.onFailure(e);
//                    }
//                });
        coBill.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    mView.onSuccess();
                }else{
                    mView.onFailure(e);
                }
            }
        });
    }

    @Override
    public void updateBill(CoBill coBill) {
//        LocalRepository.getInstance()
//                .updateBBill(bBill)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<BBill>() {
//                    @Override
//                    protected void onSuccees(BBill bBill) throws Exception {
//                        mView.onSuccess();
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        mView.onFailure(e);
//                    }
//                });
        coBill.update(coBill.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    mView.onSuccess();
                }else{
                    mView.onFailure(e);
                }
            }
        });
    }

    @Override
    public void deleteBillById(CoBill coBill) {
//        LocalRepository.getInstance()
//                .deleteBBillById(id);
        coBill.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    getView().onSuccess();
                }else{
                    getView().onFailure(e);
                }
            }
        });
    }
}
