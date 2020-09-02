package com.example.billtools.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billtools.MyApplication;
import com.example.billtools.R;
import com.example.billtools.base.BaseMVPFragment;
import com.example.billtools.model.been.local.BBill;
import com.example.billtools.model.been.local.MonthListBean;
import com.example.billtools.model.been.remote.CoBill;
import com.example.billtools.model.been.remote.MyUser;
import com.example.billtools.model.event.SyncEvent;
import com.example.billtools.presenter.MonthListPresenter;
import com.example.billtools.presenter.contract.MonthListContract;
import com.example.billtools.ui.activity.BillAddActivity;
import com.example.billtools.ui.adapter.MonthListAdapter;
import com.example.billtools.utils.DateUtils;
import com.example.billtools.widget.stickyheader.StickyHeaderGridLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

import static android.app.Activity.RESULT_OK;
import static com.example.billtools.utils.DateUtils.FORMAT_M;
import static com.example.billtools.utils.DateUtils.FORMAT_Y;

/**
 * @Author hejunfeng
 * @Date 14:08 2020/8/29 0029
 * @Description 账单明细
 **/
public class MonthListFragment extends BaseMVPFragment<MonthListContract.Presenter> implements
        MonthListContract.View, View.OnClickListener{

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.float_btn)
    FloatingActionButton floatBtn;

    private static final int SPAN_SIZE = 1;
    private String setYear = DateUtils.getCurYear(FORMAT_Y);
    private String setMonth = DateUtils.getCurMonth(FORMAT_M);
    private StickyHeaderGridLayoutManager mLayoutManager;
    private MonthListAdapter adapter;
    private MonthListListener monthListListener;
    private List<MonthListBean.DaylistBean> list = null;
    int part, index;

    @Override
    protected MonthListContract.Presenter bindPresenter() {
        return new MonthListPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_month_list;
    }

    @Override
    public void loadDataSuccess(MonthListBean monthListBean) {
        monthListListener.OnDataChanged(monthListBean.getT_outcome(), monthListBean.getT_income(), monthListBean.getT_total());
        list = monthListBean.getDaylist();
        adapter.setmDatas(list);
        adapter.notifyAllSectionsDataSetChanged();
    }

    @Override
    public void onSuccess() {
        hideLoading();
        //从列表中移除后需要重新计算当月总计
        mPresenter.getMonthList(MyApplication.getCurrentUserId(), setYear, setMonth);
    }

    @Override
    public void onFailure(Throwable e) {
        hideLoading();
        showSnackbar(e.getMessage());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(SyncEvent event) {
        if (event.getState() == 100){
            mPresenter.getMonthList(MyApplication.getCurrentUserId(), setYear, setMonth);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //注册 EventBus
        EventBus.getDefault().register(this);
        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(5);
        adapter = new MonthListAdapter(mContext, list);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        rvList = getViewById(R.id.rv_list);
        rvList.setItemAnimator(new DefaultItemAnimator(){
            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                dispatchRemoveFinished(holder);
                return false;
            }
        });
        rvList.setLayoutManager(mLayoutManager);
        rvList.setAdapter(adapter);
    }


    @Override
    protected void initClick() {
        adapter.setOnStickyHeaderClickListener(new MonthListAdapter.OnStickyHeaderClickListener() {
            @Override
            public void OnDeleteClick(CoBill item, int section, int offset) {
                item.setVersion(-1);
                //将删除的账单版本号设置为负，而非直接删除
                //便于同步删除服务器数据
                showLoading("正在删除...");
                mPresenter.deleteBill(item);
                part = section;
                index = offset;
            }

            @Override
            public void OnEditClick(CoBill item, int section, int offset) {
                // TODO: 2020/8/31 0031 跳转
                Intent intent = new Intent(mContext, BillAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cobill",item);
//                //bundle.putLong("id", item.getId());
//                //bundle.putString("rid", item.getRid());
//                bundle.putString("objId",item.getObjectId());
//                bundle.putString("sortName", item.getSortName());
//                bundle.putString("payName", item.getPayName());
//                bundle.putString("content", item.getContent());
//                bundle.putDouble("cost", item.getCost());
//                bundle.putLong("date", item.getCrdate());
//                bundle.putBoolean("income", item.getIncome());
//                bundle.putInt("version", item.getVersion());
                intent.putExtras(bundle);

                startActivityForResult(intent, 0);
            }
        });
    }

    @OnClick({R.id.float_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.float_btn:
                if (BmobUser.getCurrentUser(MyUser.class) == null){
                    showSnackbar("请先登录");
                }else{
                    // TODO: 2020/8/31 跳转
                    Intent intent = new Intent(getContext(), BillAddActivity.class);
                    startActivityForResult(intent, 0);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        //请求当月数据
        if (MyApplication.getCurrentUserId() != null){
            mPresenter.getMonthList(MyApplication.getCurrentUserId(), setYear, setMonth);
        }
    }

    /*****************************************************************************/

    public void setMonthListListener(MonthListListener monthListListener) {
        this.monthListListener = monthListListener;
    }

    public interface MonthListListener {
        void OnDataChanged(String outcome, String income, String total);
    }

    public void changeDate(String year, String month) {
        setYear = year;
        setMonth = month;
        mPresenter.getMonthList(MyApplication.getCurrentUserId(), setYear, setMonth);
    }

    @Override
    protected void beforeDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    mPresenter.getMonthList(MyApplication.getCurrentUserId(), setYear, setMonth);
                    break;
            }
        }
    }
}
