package com.example.billtools.presenter;

import com.example.billtools.base.BaseObserver;
import com.example.billtools.base.RxPresenter;
import com.example.billtools.model.been.local.BBill;
import com.example.billtools.model.been.remote.CoBill;
import com.example.billtools.model.repository.BmobRepository;
import com.example.billtools.model.repository.LocalRepository;
import com.example.billtools.presenter.contract.MonthChartContract;
import com.example.billtools.ui.fragment.MonthChartFragment;
import com.example.billtools.utils.BillUtils;
import com.example.billtools.utils.CoBillUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author hejunfeng
 * @Date 9:23 2020/9/1 0001
 * @Description com.example.billtools.presenter
 **/
public class MonthChartPresenter extends RxPresenter<MonthChartContract.View> implements MonthChartContract.Presenter {
    private String TAG="MonthChartPresenter";
    @Override
    public void getMonthChart(String id, String year, String month) {
        //账单查询
        List<BmobQuery<CoBill>> queries = BmobRepository.getInstance().queryBills(id,year,month);
        BmobQuery<CoBill> coBillBmobQuery = new BmobQuery<>();
        coBillBmobQuery.and(queries);
        coBillBmobQuery.findObjects(new FindListener<CoBill>() {
            @Override
            public void done(List<CoBill> list, BmobException e) {
                if (e == null){
                    getView().loadDataSuccess(CoBillUtils.packageChartList(list));
                }else{
                    if (getView() != null){
                        getView().loadDataFailure(e);
                    }
                }
            }
        });


//        //查询本地数据库
//        LocalRepository.getInstance().getBBillByUserIdWithYM(id, year, month)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<BBill>>() {
//                    @Override
//                    protected void onSuccees(List<BBill> bBills) throws Exception {
//                        if (getView() != null){
//                            getView().loadDataSuccess(BillUtils.packageChartList(bBills));
//                        }
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        if (getView() != null){
//                            getView().loadDataFailure(e);
//                        }
//                    }
//                });
    }
}
