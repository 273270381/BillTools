package com.example.billtools.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.billtools.R;
import com.example.billtools.base.BaseMVPActivity;
import com.example.billtools.model.been.remote.MyUser;
import com.example.billtools.presenter.RegisterPresenter;
import com.example.billtools.presenter.contract.RegisterContract;
import com.example.billtools.utils.ExampleUtil;
import com.example.billtools.utils.OSCSharedPreference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hejunfeng on 2020/8/26 0026
 */
public class RegisterActivity extends BaseMVPActivity<RegisterContract.Presenter> implements
        RegisterContract.View, View.OnFocusChangeListener, View.OnClickListener {

    @BindView(R.id.ly_retrieve_bar)
    LinearLayout mLayBackBar;
    @BindView(R.id.ll_retrieve_username)
    LinearLayout mLlRegisterUsername;
    @BindView(R.id.et_retrieve_username)
    EditText mEtRegisterUsername;
    @BindView(R.id.iv_retrieve_username_del)
    ImageView mIvRegisterUsernameDel;
    @BindView(R.id.ll_retrieve_password)
    LinearLayout mLlRegisterPwd;
    @BindView(R.id.et_retrieve_password_input)
    EditText mEtRegisterPwd;
    @BindView(R.id.iv_retrieve_password_del)
    ImageView mIvRegisterPwdDel;
    @BindView(R.id.ll_retrieve_email)
    LinearLayout mLlRegisterEmail;
    @BindView(R.id.et_retrieve_email_input)
    EditText mEtRegisterEmail;
    @BindView(R.id.iv_retrieve_email_del)
    ImageView mIvRegisterEmailDel;
    @BindView(R.id.bt_retrieve_submit)
    Button mBtRegisterSubmit;

    @Override
    protected void initWidget() {
        mEtRegisterUsername.setOnFocusChangeListener(this);
        mEtRegisterUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = s.toString().trim();
                mLlRegisterUsername.setBackgroundResource(R.drawable.bg_login_input_ok);
                if (username.length() > 0){
                    mIvRegisterUsernameDel.setVisibility(View.VISIBLE);
                }else{
                    mIvRegisterUsernameDel.setVisibility(View.INVISIBLE);
                }

                String pwd = mEtRegisterPwd.getText().toString().trim();
                String email = mEtRegisterEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(email)) {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });

        mEtRegisterPwd.setOnFocusChangeListener(this);
        mEtRegisterPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                mLlRegisterPwd.setBackgroundResource(R.drawable.bg_login_input_ok);
                if (length > 0) {
                    mIvRegisterPwdDel.setVisibility(View.VISIBLE);
                } else {
                    mIvRegisterPwdDel.setVisibility(View.INVISIBLE);
                }

                String username = mEtRegisterUsername.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    showToast(R.string.message_username_null);
                }
                String pwd = mEtRegisterPwd.getText().toString().trim();
                String email = mEtRegisterEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(email)) {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });

        mEtRegisterEmail.setOnFocusChangeListener(this);
        mEtRegisterEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                mLlRegisterEmail.setBackgroundResource(R.drawable.bg_login_input_ok);
                if (length > 0) {
                    mIvRegisterEmailDel.setVisibility(View.VISIBLE);
                } else {
                    mIvRegisterEmailDel.setVisibility(View.INVISIBLE);
                }
                String pwd = mEtRegisterPwd.getText().toString().trim();
                String email = mEtRegisterEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(email)) {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });

        TextView label = (TextView) mLayBackBar.findViewById(R.id.tv_navigation_label);
        label.setText(R.string.login_register_hint);
    }

    @Override
    protected RegisterContract.Presenter bindPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void registerSuccess(MyUser user) {
        hideKeyBoard();
        hideLoading();
        showSnackbar(R.string.register_success_hint);
        holdAccount();
        finish();
    }

    private void holdAccount() {
        OSCSharedPreference.getInstance().putUserName(mEtRegisterUsername.getText().toString().trim());
        OSCSharedPreference.getInstance().putPassWord("");
    }

    @Override
    public void registerFailure(Throwable e) {
        hideLoading();
        if (e.getMessage().contains("already") && e.getMessage().contains("username")){
            showSnackbar("用户名已存在");
        }else if (e.getMessage().contains("already") && e.getMessage().contains("email")){
            showSnackbar("该邮箱已被注册");
        }else if (e.getMessage().contains("email Must be a valid email address")){
            showSnackbar("邮箱格式错误");
        }else{
            showSnackbar("注册失败");
        }
    }

    public static void show(Context context){
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @OnClick({R.id.iv_retrieve_username_del, R.id.iv_retrieve_password_del, R.id.iv_retrieve_email_del, R.id.bt_retrieve_submit
                ,R.id.lay_retrieve_container, R.id.ib_navigation_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lay_retrieve_container:
                try {
                    hideKeyBoard();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.iv_retrieve_username_del:
                mEtRegisterUsername.setText(null);
                break;
            case R.id.iv_retrieve_password_del:
                mEtRegisterPwd.setText(null);
                break;
            case R.id.iv_retrieve_email_del:
                mEtRegisterEmail.setText(null);
                break;
            case R.id.bt_retrieve_submit:
                signUp();
                break;
            case R.id.ib_navigation_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void signUp() {
        hideKeyBoard();
        String userName = mEtRegisterUsername.getText().toString().trim();
        String passWord = mEtRegisterPwd.getText().toString().trim();
        String email = mEtRegisterEmail.getText().toString().trim();
        if (!ExampleUtil.isConnected(this)){
            showSnackbar("确认网络是否断开");
            return;
        }
        if (userName.length() == 0 || passWord.length() == 0) {
            showSnackbar("用户名或密码不能为空");
            return;
        }
        if (email.length() == 0){
            showSnackbar("邮箱不能为空");
            return;
        }
        mPresenter.register(userName, passWord, email);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.et_retrieve_username:
                if (hasFocus){
                    mLlRegisterUsername.setActivated(true);
                    mLlRegisterPwd.setActivated(false);
                    mLlRegisterEmail.setActivated(false);
                }
                break;
            case R.id.et_retrieve_password_input:
                if (hasFocus){
                    mLlRegisterUsername.setActivated(false);
                    mLlRegisterPwd.setActivated(true);
                    mLlRegisterEmail.setActivated(false);
                }
                break;
            case R.id.et_retrieve_email_input:
                if (hasFocus){
                    mLlRegisterUsername.setActivated(false);
                    mLlRegisterPwd.setActivated(false);
                    mLlRegisterEmail.setActivated(true);
                }
                break;
            default:
                break;
        }
    }
}
