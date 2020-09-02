package com.example.billtools.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.billtools.R;
import com.example.billtools.base.BaseMVPActivity;
import com.example.billtools.model.been.remote.MyUser;
import com.example.billtools.model.repository.BmobRepository;
import com.example.billtools.presenter.LoginPresenter;
import com.example.billtools.presenter.contract.LogInContract;
import com.example.billtools.utils.ExampleUtil;
import com.example.billtools.utils.OSCSharedPreference;
import com.example.billtools.utils.SnackbarUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * 登陆注册界面
 * Created by hejunfeng on 2020/8/25 0025
 */
public class LoginActivity extends BaseMVPActivity<LogInContract.Presenter> implements
                            LogInContract.View, View.OnFocusChangeListener, View.OnClickListener {
    @BindView(R.id.ly_retrieve_bar)
    LinearLayout mLayBackBar;
    @BindView(R.id.ll_login_username)
    LinearLayout mLlLoginUsername;
    @BindView(R.id.et_login_username)
    EditText mEtLoginUsername;
    @BindView(R.id.iv_login_username_del)
    ImageView mIvLoginUsernameDel;
    @BindView(R.id.ll_login_pwd)
    LinearLayout mLlLoginPwd;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    @BindView(R.id.iv_login_pwd_del)
    ImageView mIvLoginPwdDel;
    @BindView(R.id.iv_login_hold_pwd)
    ImageView mIvHoldPwd;
    @BindView(R.id.tv_login_forget_pwd)
    TextView mTvLoginForgetPwd;
    @BindView(R.id.bt_login_submit)
    Button mBtLoginSubmit;
    @BindView(R.id.bt_login_register)
    Button mBtLoginRegister;
    private MyUser currentUser;

    @Override
    protected void initWidget() {
        mEtLoginUsername.setOnFocusChangeListener(this);
        mEtLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = s.toString().trim();
                if (username.length() > 0) {
                    mLlLoginUsername.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginUsernameDel.setVisibility(View.VISIBLE);
                } else {
                    mLlLoginUsername.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginUsernameDel.setVisibility(View.INVISIBLE);
                }

                String pwd = mEtLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd)) {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }

            }
        });

        mEtLoginPwd.setOnFocusChangeListener(this);
        mEtLoginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0) {
                    mLlLoginPwd.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginPwdDel.setVisibility(View.VISIBLE);
                } else {
                    mIvLoginPwdDel.setVisibility(View.INVISIBLE);
                }

                String username = mEtLoginUsername.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    showToast(R.string.message_username_null);
                }
                String pwd = mEtLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd)) {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });

        TextView label = (TextView) mLayBackBar.findViewById(R.id.tv_navigation_label);
        ImageButton bt = mLayBackBar.findViewById(R.id.ib_navigation_back);
        label.setVisibility(View.INVISIBLE);
        bt.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEtLoginUsername.setText(OSCSharedPreference.getInstance().getUserName());
        mEtLoginPwd.setText(OSCSharedPreference.getInstance().getPassword());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LogInContract.Presenter bindPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void loginSuccess(MyUser user) {
        currentUser = BmobUser.getCurrentUser(MyUser.class);
        BmobRepository.getInstance().syncBill(currentUser.getObjectId());
        hideKeyBoard();
        hideLoading();
        showSnackbar(R.string.login_success_hint);
        setResult(RESULT_OK, new Intent());
        holdAccount();
        finish();
    }

    private void holdAccount() {
        OSCSharedPreference.getInstance().putUserName(mEtLoginUsername.getText().toString().trim());
        OSCSharedPreference.getInstance().putPassWord(mEtLoginPwd.getText().toString().trim());
    }

    @Override
    public void loginFailure(Throwable e) {
        hideLoading();
        showSnackbar("用户名或密码错误");
        Log.e(TAG,e.getMessage());
    }

    public static void show(Context context){
        context.startActivity(new Intent(context,LoginActivity.class));
    }

    @OnClick({R.id.bt_login_submit,R.id.ib_navigation_back, R.id.et_login_username,R.id.et_login_pwd, R.id.tv_login_forget_pwd,
            R.id.iv_login_username_del, R.id.iv_login_pwd_del,R.id.lay_login_container, R.id.bt_login_register})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lay_login_container:
                try {
                    hideKeyBoard();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.iv_login_pwd_del:
                mEtLoginPwd.setText(null);
                break;
            case R.id.iv_login_username_del:
                mEtLoginUsername.setText(null);
                break;
            case R.id.tv_login_forget_pwd:
                //忘记密码
                break;
            case R.id.et_login_pwd:
                mEtLoginUsername.clearFocus();
                mEtLoginPwd.setFocusableInTouchMode(true);
                mEtLoginPwd.requestFocus();
                break;
            case R.id.et_login_username:
                mEtLoginPwd.clearFocus();
                mEtLoginUsername.setFocusableInTouchMode(true);
                mEtLoginUsername.requestFocus();
                break;
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.bt_login_submit:
                login();
                break;
            case R.id.bt_login_register:
                sign();
                break;
            default:
                break;
        }
       
    }

    private void sign() {
        RegisterActivity.show(this);
    }

    private void login() {
        hideKeyBoard();
        String userName = mEtLoginUsername.getText().toString().trim();
        String passWord = mEtLoginPwd.getText().toString().trim();
        if (!ExampleUtil.isConnected(this)){
            showSnackbar("确认网络是否断开");
            return;
        }
        if (userName.length() == 0 || passWord.length() == 0) {
            showSnackbar("用户名或密码不能为空");
            return;
        }
        mPresenter.login(userName, passWord);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.et_login_username:
                if (hasFocus) {
                    mLlLoginUsername.setActivated(true);
                    mLlLoginPwd.setActivated(false);
                }
                break;
            case R.id.et_login_pwd:
                if (hasFocus) {
                    mLlLoginPwd.setActivated(true);
                    mLlLoginUsername.setActivated(false);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                Log.d(TAG,"resultOk");
                break;
        }
    }
}
