package com.example.billtools.presenter.contract;

import com.example.billtools.base.BaseContract;
import com.example.billtools.model.been.local.BBill;
import com.example.billtools.model.been.local.MonthListBean;
import com.example.billtools.model.been.remote.CoBill;

/**
 * @Author hejunfeng
 * @Date 9:15 2020/8/31 0031
 * @Description com.example.billtools.presenter.contract
 **/
public interface MonthListContract extends BaseContract {
    interface View extends BaseContract.BaseView{
        void loadDataSuccess(MonthListBean list);

        void onSuccess();

        void onFailure(Throwable e);
    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void getMonthList(String id, String year, String month);

        void deleteBill(CoBill bBill);

        void updateBill(CoBill bBill);
    }
}
