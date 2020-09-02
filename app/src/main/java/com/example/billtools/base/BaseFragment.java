package com.example.billtools.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.billtools.R;
import com.example.billtools.utils.CommonToast;
import com.example.billtools.utils.DialogHelper;
import com.example.billtools.utils.SnackbarUtils;
import com.example.billtools.utils.ToastNotRepeat;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @Author hejunfeng
 * @Date 14:17 2020/8/29 0029
 * @Description com.example.billtools.base
 **/
public abstract class BaseFragment extends Fragment implements BaseContract.BaseView{
    protected String TAG;
    protected CompositeDisposable mDisposable;
    protected Activity mActivity;
    protected Context mContext;
    private View root;
    private AlertDialog _comformDialog;
    private ProgressDialog _waitDialog;
    private MaterialDialog.Builder materialDialogBuilder;
    private Unbinder unbinder;

    @Override
    public void onAttach(@NonNull Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId = getLayoutId();
        root = inflater.inflate(resId, container, false);
        unbinder = ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
        TAG = getClass().getName();
        initWidget(savedInstanceState);
        initClick();
        processLogic();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        beforeDestroy();
        if (mDisposable != null){
            mDisposable.clear();
        }
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected void addDisposable(Disposable a){
        if (mDisposable == null){
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(a);
    }

    protected void initData(Bundle savedInstanceState){

    }

    /**
     * 初始化点击事件
     */
    protected void initClick(){
    }

    /**
     * 逻辑使用区
     */
    protected void processLogic(){
    }

    /**
     * 初始化零件
     */
    protected void initWidget(Bundle savedInstanceState){
    }

    protected void beforeDestroy(){

    }

    protected <V>V getViewById(int id){
        if (root == null){
            return null;
        }
        return (V) root.findViewById(id);
    }

    public String getName(){
        return getClass().getName();
    }


    @Override
    public AlertDialog showConfirmDialog(String msg, String title, DialogInterface.OnClickListener listener) {
        if (_comformDialog == null){
            _comformDialog = DialogHelper.getConfirmDialog(mContext, msg, listener).create();
        }
        if (_comformDialog != null){
            _comformDialog.setTitle(title);
            _comformDialog.show();
        }
        return _comformDialog;
    }

    @Override
    public void hideConfirmDialog() {
        if (_comformDialog != null){
            try {
                _comformDialog.dismiss();
                _comformDialog = null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public ProgressDialog showLoading() {
        return showLoading(R.string.loading);
    }

    @Override
    public ProgressDialog showLoading(int resid) {
        return showLoading(getString(resid));
    }

    @Override
    public ProgressDialog showLoading(String message) {
        if (_waitDialog == null) {
            _waitDialog = DialogHelper.getProgressDialog(mContext, message);
        }
        if (_waitDialog != null) {
            _waitDialog.setMessage(message);
            _waitDialog.show();
        }
        return _waitDialog;
    }

    @Override
    public void hideLoading() {
        if (_waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public MaterialDialog.Builder showChoiceDialog(String title, String[] strings, MaterialDialog.ListCallbackSingleChoice callbackSingleChoice) {
        return null;
    }

    @Override
    public MaterialDialog.Builder showPhoneNumberDialog(String title, int inputType, String hint, String msg, MaterialDialog.InputCallback inputCallback) {
        return null;
    }

    @Override
    public void showToast(String message, int icon, int gravity) {
        CommonToast toast = new CommonToast(mActivity);
        toast.setMessage(message);
        toast.setMessageIc(icon);
        toast.setLayoutGravity(gravity);
        toast.show();
    }

    @Override
    public void showToast(String message) {
        ToastNotRepeat.show(mContext, message);
    }

    @Override
    public void showToast(int resid) {
        showToast(getString(resid));
    }

    @Override
    public void showSnackbar(int resid) {
        showSnackbar(getString(resid));
    }

    @Override
    public void showSnackbar(String message) {
        SnackbarUtils.show(mContext, message);
    }

    protected void hideKeyBoard() {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager == null){
                return;
            }
            if (view.getWindowToken() == null){
                return;
            }
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
