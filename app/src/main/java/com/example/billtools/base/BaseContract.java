package com.example.billtools.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by hejunfeng on 2020/8/25 0025
 */
public interface BaseContract {
    interface BasePresenter<T> {
        void attachView(T view);

        void detachView();
    }

    interface BaseView{

        /**
         * 显示确认对话框
         */
        AlertDialog showConfirmDialog(String msg, String title, DialogInterface.OnClickListener listener);

        /**
         * 隐藏确认对话框
         */
        void hideConfirmDialog();

        /**
         * 显示正在加载进度框
         */
        ProgressDialog showLoading();
        /**
         * 显示正在加载进度框
         */
        ProgressDialog showLoading(int resid);

        /**
         * 显示正在加载进度框
         */
        ProgressDialog showLoading(String msg);
        /**
         * 隐藏正在加载进度框
         */
        void hideLoading();

        /**
         * 显示Choice对话框
         */
        MaterialDialog.Builder showChoiceDialog(String title,String[] strings, MaterialDialog.ListCallbackSingleChoice callbackSingleChoice);


        /**
         * 显示电话输入对话框
         */
        MaterialDialog.Builder showPhoneNumberDialog( String title, int inputType, String hint, String msg, MaterialDialog.InputCallback inputCallback);

        /**
         * 显示提示
         */
        void showToast(String message, int icon, int gravity);

        void showToast(String message);

        void showToast(int resid);

        void showSnackbar(int resid);

        void showSnackbar(String message);
    }
}
