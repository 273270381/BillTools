package com.example.billtools.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bumptech.glide.Glide;
import com.example.billtools.R;
import com.example.billtools.base.BaseActivity;
import com.example.billtools.model.Constants;
import com.example.billtools.model.been.local.BSort;
import com.example.billtools.model.been.local.NoteBean;
import com.example.billtools.model.been.remote.MyUser;
import com.example.billtools.model.repository.BmobRepository;
import com.example.billtools.model.repository.LocalRepository;
import com.example.billtools.ui.adapter.MainFragmentPagerAdapter;
import com.example.billtools.ui.fragment.MonthChartFragment;
import com.example.billtools.ui.fragment.MonthListFragment;
import com.example.billtools.utils.DateUtils;
import com.example.billtools.utils.GlideCacheUtil;
import com.example.billtools.utils.OSCSharedPreference;
import com.example.billtools.utils.SnackbarUtils;
import com.example.billtools.utils.ThemeManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import cn.bmob.v3.BmobUser;

/**
 * Created by hejunfeng on 2020/8/27 0027
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.drawLayout)
    DrawerLayout drawer;
    @BindView(R.id.main_nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.t_outcome)
    TextView tOutcome;
    @BindView(R.id.t_income)
    TextView tIncome;
    @BindView(R.id.t_total)
    TextView tTotal;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    private MyUser currentUser;
    private View drawerHeader;
    private ImageView drawerIv;
    private TextView drawerTvAccount, drawerTvMail;

    protected static final int USERINFOACTIVITY_CODE = 0;
    protected static final int LOGINACTIVITY_CODE = 1;

    // Tab
    private FragmentManager mFragmentManager;
    private MainFragmentPagerAdapter mFragmentPagerAdapter;
    private MonthListFragment monthListFragment;
    private MonthChartFragment monthChartFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        //第一次进入将默认账单分类添加到数据库
        if (OSCSharedPreference.getInstance().isFirstStart()){
            NoteBean note = new Gson().fromJson(Constants.BILL_NOTE, NoteBean.class);
            List<BSort> sorts = note.getOutSortlis();
            sorts.addAll(note.getInSortlis());
            LocalRepository.getInstance().saveBsorts(sorts);
            LocalRepository.getInstance().saveBPays(note.getPayinfo());
        }
        monthListFragment = new MonthListFragment();
        monthChartFragment = new MonthChartFragment();
    }

    @Override
    protected void initWidget() {
        //初始化Toolbar
        toolbar.setTitle("CocoBill");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawerHeader = navigationView.inflateHeaderView(R.layout.drawer_header);
        drawerIv = drawerHeader.findViewById(R.id.drawer_iv);
        drawerTvAccount = drawerHeader.findViewById(R.id.drawer_tv_name);
        drawerTvMail = drawerHeader.findViewById(R.id.drawer_tv_mail);
        //设置头部账户
        setDrawerHeaderAccount();
        //初始化ViewPager
        mFragmentManager = getSupportFragmentManager();
        mFragmentPagerAdapter = new MainFragmentPagerAdapter(mFragmentManager);
        mFragmentPagerAdapter.addFragment(monthListFragment, "明细");
        mFragmentPagerAdapter.addFragment(monthChartFragment, "图表");
        monthListFragment.setMonthListListener((outcome, income, total) -> {
            tOutcome.setText(outcome);
            tIncome.setText(income);
            tTotal.setText(total);
        });

        viewPager.setAdapter(mFragmentPagerAdapter);

        //初始化TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("明细"));
        tabLayout.addTab(tabLayout.newTab().setText("图表"));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setDrawerHeaderAccount() {
        currentUser = BmobUser.getCurrentUser(MyUser.class);
        //获取当前用户
        if (currentUser != null){
            drawerTvAccount.setText(currentUser.getUsername());
            drawerTvMail.setText(currentUser.getEmail());
            Glide.with(mContext).load(currentUser.getImage()).into(drawerIv);
        }else{
            drawerTvAccount.setText("账号");
            drawerTvMail.setText("点我登陆");
            drawerIv.setImageResource(R.mipmap.ic_def_icon);
        }
    }

    @Override
    protected void initClick() {
        //监听侧滑菜单项
        navigationView.setNavigationItemSelectedListener(this);
        //监听侧滑菜单头部点击事件
        drawerHeader.setOnClickListener(v -> {
            if (currentUser == null){
                startActivityForResult(new Intent(mContext, LoginActivity.class), LOGINACTIVITY_CODE);
            }else{
                startActivityForResult(new Intent(mContext, UserInfoActivity.class), USERINFOACTIVITY_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_date:
                if (currentUser == null){
                    SnackbarUtils.show(mContext, "请先登陆");
                }else{
                    //时间选择器
                    new TimePickerBuilder(mContext, (Date date, View v) -> {
                        monthListFragment.changeDate(DateUtils.date2Str(date, "yyyy"), DateUtils.date2Str(date, "MM"));
                        monthChartFragment.changeDate(DateUtils.date2Str(date, "yyyy"), DateUtils.date2Str(date, "MM"));
                    }).setType(new boolean[]{true, true, false, false, false, false})
                            .setRangDate(null, Calendar.getInstance())
                            .isDialog(true)//是否显示为对话框样式
                            .build().show();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_sync:    //同步账单
                if (currentUser == null) {
                    SnackbarUtils.show(mContext, "请先登陆");
                } else {
                    BmobRepository.getInstance().syncBill(currentUser.getObjectId());
                }
                break;
            case R.id.nav_exit:
                if (currentUser == null){
                    SnackbarUtils.show(mContext, "请先登陆");
                }else{
                    exitUser();
                }
                break;
            case R.id.nav_about:
                startActivity(new Intent(mContext,AboutActivity.class));
                break;
            case R.id.nav_theme:
                showUpdateThemeDialog();
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void exitUser() {
        showConfirmDialog("退出后将清空所有数据", "确认退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GlideCacheUtil.getInstance().clearImageDiskCache(mContext);
                MyUser.logOut();
                //清除本地数据
                //LocalRepository.getInstance().deleteAllBills();
                //重启
                finish();
                Intent intent = getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    /**
     * @return void
     * @Author hejunfeng
     * @Date 13:37 2020/9/1 0001
     * @Param []
     * @Description 显示修改主题色 Dialog
     **/
    private void showUpdateThemeDialog() {
        String[] themes = ThemeManager.getInstance().getThemes();
        new MaterialDialog.Builder(mContext)
                .title("选择主题")
                .titleGravity(GravityEnum.CENTER)
                .items(themes)
                .negativeText("取消")
                .itemsCallback(((dialog, itemView, position, text) -> {
                    ThemeManager.getInstance().setTheme(mActivity, themes[position]);
                }))
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case USERINFOACTIVITY_CODE:
                    setDrawerHeaderAccount();
                    break;
                case LOGINACTIVITY_CODE:
                    setDrawerHeaderAccount();
                    break;
            }
        }
    }
}