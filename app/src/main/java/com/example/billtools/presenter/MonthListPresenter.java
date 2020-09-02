package com.example.billtools.presenter;

import com.example.billtools.base.BaseObserver;
import com.example.billtools.base.RxPresenter;
import com.example.billtools.model.been.local.BBill;
import com.example.billtools.model.been.remote.CoBill;
import com.example.billtools.model.repository.BmobRepository;
import com.example.billtools.model.repository.LocalRepository;
import com.example.billtools.presenter.contract.MonthListContract;
import com.example.billtools.utils.BillUtils;
import com.example.billtools.utils.CoBillUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author hejunfeng
 * @Date 13:48 2020/8/31 0031
 * @Description com.example.billtools.presenter
 **/
public class MonthListPresenter extends RxPresenter<MonthListContract.View> implements MonthListContract.Presenter{
    private String TAG="MonthListPresenter";
    @Override
    public void getMonthList(String id, String year, String month) {
        //账单查询
        List<BmobQuery<CoBill>> queries = BmobRepository.getInstance().queryBills(id,year,month);
        BmobQuery<CoBill> coBillBmobQuery = new BmobQuery<>();
        coBillBmobQuery.and(queries);
        coBillBmobQuery.findObjects(new FindListener<CoBill>() {
            @Override
            public void done(List<CoBill> list, BmobException e) {
                if (e == null){
                    getView().loadDataSuccess(CoBillUtils.packageDetailList(list));
                }else{
                    if (getView() != null){
                        getView().onFailure(e);
                    }
                }
            }
        });
//         查询本地数据库
//        LocalRepository.getInstance().getBBillByUserIdWithYM(id, year, month)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<BBill>>() {
//                    @Override
//                    protected void onSuccees(List<BBill> bBills) throws Exception {
//                        if (getView() != null){
//                            getView().loadDataSuccess(BillUtils.packageDetailList(bBills));
//                        }
//                    }
//
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        if (getView() != null){
//                            getView().onFailure(e);
//                        }
//                    }
//                });
    }

    @Override
    public void deleteBill(CoBill coBill) {
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
//        LocalRepository.getInstance().deleteBBillById(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<Long>() {
//                    @Override
//                    protected void onSuccees(Long aLong) throws Exception {
//                        if (getView() != null){
//                            getView().onSucess();
//                        }
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        if (getView() != null){
//                            getView().onFailure(e);
//                        }
//                    }
//                });
    }

    @Override
    public void updateBill(CoBill coBill) {
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
//        LocalRepository.getInstance().updateBBill(bBill)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<BBill>() {
//                    @Override
//                    protected void onSuccees(BBill bBill) throws Exception {
//                        if (getView() != null){
//                            getView().onSuccess();
//                        }
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        if (getView() != null){
//                            getView().onFailure(e);
//                        }
//                    }
//                });
    }
}
