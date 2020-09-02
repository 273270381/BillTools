package com.example.billtools.presenter.contract;


import com.example.billtools.base.BaseContract;
import com.example.billtools.model.been.local.BBill;
import com.example.billtools.model.been.local.NoteBean;
import com.example.billtools.model.been.remote.CoBill;

/**
 * Created by Zhouas666 on 2019-01-08
 * Github: https://github.com/zas023
 */
public interface BillContract extends BaseContract {

    interface View extends BaseView {

        void loadDataSuccess(NoteBean bean);

        void onSuccess();

        void onFailure(Throwable e);

    }

    interface Presenter extends BasePresenter<View>{
        /**
         * 获取信息
         */
        void getBillNote();

        /**
         * 添加账单
         */
        void addBill(CoBill bBill);

        /**
         * 修改账单
         */
        void updateBill(CoBill bBill);


        /**
         * 删除账单
         */
        void deleteBillById(CoBill bBill);
    }
}
