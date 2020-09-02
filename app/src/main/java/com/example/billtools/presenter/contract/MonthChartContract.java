package com.example.billtools.presenter.contract;

import com.example.billtools.base.BaseContract;
import com.example.billtools.model.been.local.MonthChartBean;

/**
 * @Author hejunfeng
 * @Date 15:10 2020/8/31 0031
 * @Description com.example.billtools.presenter.contract
 **/
public interface MonthChartContract extends BaseContract {
    interface View extends BaseView{
        void loadDataSuccess(MonthChartBean bean);

        void loadDataFailure(Throwable e);
    }

    interface Presenter extends BasePresenter<View>{
        void getMonthChart(String id, String year, String month);
    }
}
