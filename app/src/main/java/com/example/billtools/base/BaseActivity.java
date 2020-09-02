package com.example.billtools.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.billtools.R;
import com.example.billtools.utils.CommonToast;
import com.example.billtools.utils.DialogHelper;
import com.example.billtools.utils.SnackbarUtils;
import com.example.billtools.utils.ThemeManager;
import com.example.billtools.utils.ToastNotRepeat;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by hejunfeng on 2020/8/25 0025
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseContract.BaseView{
    protected static String TAG;
    protected Activity mActivity;
    protected Context mContext;
    protected CompositeDisposable mDisposable;
    protected Toolbar mToolbar;
    private AlertDialog _comformDialog;
    private ProgressDialog _waitDialog;
    private MaterialDialog.Builder materialDialogBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置主题色，，，一定要在setView之前
        ThemeManager.getInstance().init(this);
        //initWindow();//设置透明状态栏
        setContentView(getLayoutId());
        mActivity = this;
        mContext = this;
        // 设置 TAG
        TAG = this.getClass().getSimpleName();
        ButterKnife.bind(this);
        initData(savedInstanceState);
        initToolbar();
        initWidget();
        initEvent();
        initClick();
        processLogic();
    }

    protected void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected void addDisposabled(Disposable d){
        if (mDisposable == null){
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }

    /**
     * 配置Toolbar
     */
    protected void setUpToolbar(Toolbar toolbar){

    }

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected void initData(Bundle savedInstanceState) {
    }

    /**
     * 初始化零件
     */
    protected void initWidget() {
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    protected void initClick(){

    }

    /**
     * 执行逻辑
     */
    protected void processLogic() {
    }
    protected void beforeDestroy() {
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            supportActionBar(mToolbar);
            setUpToolbar(mToolbar);
        }
    }

    protected ActionBar supportActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(
                (v) -> finish()
        );
        return actionBar;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beforeDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public AlertDialog showConfirmDialog(String msg, String title, DialogInterface.OnClickListener listener) {
        if (_comformDialog == null){
            _comformDialog = DialogHelper.getConfirmDialog(this, msg, listener).create();
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
    public MaterialDialog.Builder showChoiceDialog(String title,String[] strings, MaterialDialog.ListCallbackSingleChoice callbackSingleChoice) {
        materialDialogBuilder = DialogHelper.getChoiceDialog(this,title, strings,callbackSingleChoice );
        materialDialogBuilder.show();
        return materialDialogBuilder;
    }



    @Override
    public MaterialDialog.Builder showPhoneNumberDialog(String title, int inputType, String hint, String msg, MaterialDialog.InputCallback inputCallback){
        materialDialogBuilder = DialogHelper.getPhoneNumberDialog(this,title,inputType,hint,msg,inputCallback);
        materialDialogBuilder.show();
        return materialDialogBuilder;
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
            _waitDialog = DialogHelper.getProgressDialog(this, message);
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
    public void showToast(String message, int icon, int gravity) {
        CommonToast toast = new CommonToast(this);
        toast.setMessage(message);
        toast.setMessageIc(icon);
        toast.setLayoutGravity(gravity);
        toast.show();
    }


    @Override
    public void showToast(int resid) {
        showToast(getString(resid));
    }

    @Override
    public void showToast(String message) {
        ToastNotRepeat.show(this, message);
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
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager == null){
                return;
            }
            if (view.getWindowToken() == null){
                return;
            }
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
