package com.example.billtools.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.billtools.R;
import com.example.billtools.base.BaseActivity;
import com.example.billtools.base.BaseMVPActivity;
import com.example.billtools.model.been.remote.MyUser;
import com.example.billtools.presenter.UserInfoPresenter;
import com.example.billtools.presenter.contract.UserInfoContract;
import com.example.billtools.utils.StringUtils;
import com.example.billtools.widget.CommonItemLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * Created by hejunfeng on 2020/8/27 0027
 */
public class UserInfoActivity extends BaseMVPActivity<UserInfoContract.Presenter> implements UserInfoContract.View, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rlt_update_icon)
    RelativeLayout iconRL;
    @BindView(R.id.img_icon)
    ImageView iconIv;
    @BindView(R.id.cil_username)
    CommonItemLayout usernameCL;
    @BindView(R.id.cil_sex)
    CommonItemLayout sexCL;
    @BindView(R.id.cil_phone)
    CommonItemLayout phoneCL;
    @BindView(R.id.cil_email)
    CommonItemLayout emailCL;


    private MyUser currentUser;
    protected static final int CHOOSE_PICTURE = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }


    

    @Override
    protected void initData(Bundle savedInstanceState) {
        currentUser = BmobUser.getCurrentUser(MyUser.class);
    }

    @Override
    protected void initWidget() {
        //初始化Toolbar
        toolbar.setTitle("账户");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            //返回消息更新上个Activity数据
            setResult(RESULT_OK, new Intent());
            finish();
        });
        //加载当前头像
        Glide.with(mContext).load(currentUser.getImage()).into(iconIv);
        //添加用户信息
        usernameCL.setRightText(currentUser.getUsername());
        sexCL.setRightText(currentUser.getGender());
        phoneCL.setRightText(currentUser.getMobilePhoneNumber());
        emailCL.setRightText(currentUser.getEmail());
    }



    /**
     * @return void
     * @Author hejunfeng
     * @Date 10:29 2020/8/29 0029
     * @Param [v]
     * @Description 事件监听
     **/
    @OnClick({R.id.rlt_update_icon, R.id.cil_username, R.id.cil_sex ,R.id.cil_phone, R.id.cil_email})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlt_update_icon:
                startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), CHOOSE_PICTURE);
                break;
            case R.id.cil_username:
                showSnackbar("江湖人行不更名，坐不改姓！");
                break;
            case R.id.cil_sex:
                showGenderDialog();
                break;
            case R.id.cil_phone:
                showPhoneDialog();
                break;
            case R.id.cil_email:
                showMailDialog();
                break;
            default:
                break;
        }
    }

    /**
     * @return void
     * @Author hejunfeng
     * @Date 10:23 2020/8/29 0029
     * @Param []
     * @Description 显示更换邮箱对话框
     **/
    private void showMailDialog() {
        String email = currentUser.getMobilePhoneNumber();
        showPhoneNumberDialog("邮箱", InputType.TYPE_CLASS_TEXT, "请输入邮箱地址", email, new MaterialDialog.InputCallback() {
            @Override
            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                String inputStr=input.toString();
                if (inputStr.equals("")) {
                    showSnackbar("内容不能为空！");
                } else {
                    if (StringUtils.checkEmail(inputStr)) {
                        currentUser.setEmail(inputStr);
                        emailCL.setRightText(inputStr);
                        doUpdate();
                    } else {
                        showSnackbar("请输入正确的电话号码");
                    }
                }
            }
        });
    }

    /**
     * @return void
     * @Author hejunfeng
     * @Date 10:23 2020/8/29 0029
     * @Param []
     * @Description 电话修改
     **/
    private void showPhoneDialog() {
        String phone = currentUser.getMobilePhoneNumber();
        showPhoneNumberDialog("电话", InputType.TYPE_CLASS_TEXT, "请输入电话号码", phone, new MaterialDialog.InputCallback() {
            @Override
            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                String inputStr=input.toString();
                if (inputStr.equals("")) {
                    showSnackbar("内容不能为空！");
                }else{
                    if (StringUtils.checkPhoneNumber(inputStr)) {
                        currentUser.setMobilePhoneNumber(inputStr);
                        phoneCL.setRightText(inputStr);
                        doUpdate();
                    }else{
                        showSnackbar("请输入正确的电话号码");
                    }
                }
            }
        });
    }


    /**
     * @Author hejunfeng
     * @Description 性别修改
     * @Date 10:36 2020/8/28 0028
     * @Param
     * @return
     **/
    private void showGenderDialog() {
        showChoiceDialog("选择性别", new String[]{"男", "女"}, new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                currentUser.setGender(text.toString());
                doUpdate();
                sexCL.setRightText(currentUser.getGender());
                dialog.dismiss();
                return false;
            }
        });
    }


    /**
     * @return void
     * @Author hejunfeng
     * @Date 10:23 2020/8/29 0029
     * @Param []
     * @Description 更新用户数据
     **/
    private void doUpdate() {
        if (currentUser != null){
            mPresenter.updateUser(currentUser);
        }
    }


    /**
     * @return com.example.billtools.presenter.contract.UserInfoContract.Presenter
     * @Author hejunfeng
     * @Date 10:28 2020/8/29 0029
     * @Param []
     * @Description UserInfoActivity
     **/
    @Override
    protected UserInfoContract.Presenter bindPresenter() {
        return new UserInfoPresenter();
    }


    /**
     * @return void
     * @Author hejunfeng
     * @Date 10:26 2020/8/29 0029
     * @Param [user]
     * @Description 更新成功
     **/
    @Override
    public void updateSuccess(MyUser user) {
        hideLoading();
        showSnackbar("更新成功");
        currentUser = BmobUser.getCurrentUser(MyUser.class);
    }


    /**
     * @return void
     * @Author hejunfeng
     * @Date 10:26 2020/8/29 0029
     * @Param [e]
     * @Description 更新失败
     **/
    @Override
    public void updateFailure(Throwable e) {
        hideLoading();
        showSnackbar(e.getMessage());
    }
}
