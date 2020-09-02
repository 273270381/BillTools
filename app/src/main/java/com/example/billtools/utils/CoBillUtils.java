package com.example.billtools.utils;

import com.example.billtools.model.been.local.BBill;
import com.example.billtools.model.been.local.MonthChartBean;
import com.example.billtools.model.been.local.MonthListBean;
import com.example.billtools.model.been.remote.CoBill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hejunfeng
 * @Date 16:21 2020/9/1 0001
 * @Description 包装账单展示列表工具类
 **/
public class CoBillUtils {
    /**
     * 账单按天分类
     * @param list
     * @return
     */
    public static MonthListBean packageDetailList(List<CoBill> list) {
        MonthListBean bean = new MonthListBean();
        float t_income = 0;
        float t_outcome = 0;
        List<MonthListBean.DaylistBean> daylist = new ArrayList<>();
        List<CoBill> beanList = new ArrayList<>();
        float income = 0;
        float outcome = 0;
        String preDay = "";  //记录前一天的时间
        for (int i = 0; i < list.size(); i++) {
            CoBill coBill = list.get(i);
            //计算总收入支出
            if (coBill.getIncome()) {
                t_income += coBill.getCost();
            } else {
                t_outcome += coBill.getCost();
            }

            //判断后一个账单是否于前者为同一天
            if (i == 0 || preDay.equals(DateUtils.getDay(coBill.getCrdate()))) {

                if (coBill.getIncome()) {
                    income += coBill.getCost();
                } else {
                    outcome += coBill.getCost();
                }
                beanList.add(coBill);

                if (i==0) {
                    preDay = DateUtils.getDay(coBill.getCrdate());
                }
            } else {
                //局部变量防止引用冲突
                List<CoBill> tmpList = new ArrayList<>();
                tmpList.addAll(beanList);
                MonthListBean.DaylistBean tmpDay = new MonthListBean.DaylistBean();
                tmpDay.setList(tmpList);
                tmpDay.setMoney("支出：" + outcome + " 收入：" + income);
                tmpDay.setTime(preDay);
                daylist.add(tmpDay);

                //清空前一天的数据
                beanList.clear();
                income = 0;
                outcome = 0;

                //添加数据
                if (coBill.getIncome()) {
                    income += coBill.getCost();
                } else {
                    outcome += coBill.getCost();
                }
                beanList.add(coBill);
                preDay = DateUtils.getDay(coBill.getCrdate());
            }
        }
        if (beanList.size() > 0) {
            //局部变量防止引用冲突
            List<CoBill> tmpList = new ArrayList<>();
            tmpList.addAll(beanList);
            MonthListBean.DaylistBean tmpDay = new MonthListBean.DaylistBean();
            tmpDay.setList(tmpList);
            tmpDay.setMoney("支出：" + outcome + " 收入：" + income);
            tmpDay.setTime(DateUtils.getDay(beanList.get(0).getCrdate()));
            daylist.add(tmpDay);
        }

        bean.setT_income(String.valueOf(t_income));
        bean.setT_outcome(String.valueOf(t_outcome));
        bean.setT_total(String.valueOf(t_income-t_outcome));
        bean.setDaylist(daylist);
        return bean;
    }

    /**
     * 账单按类型分类
     * @param list
     * @return
     */
    public static MonthChartBean packageChartList(List<CoBill> list) {
        MonthChartBean bean = new MonthChartBean();
        float t_income = 0;
        float t_outcome = 0;

        Map<String, List<CoBill>> mapIn = new HashMap<>();
        Map<String, Float> moneyIn = new HashMap<>();
        Map<String, List<CoBill>> mapOut = new HashMap<>();
        Map<String, Float> moneyOut = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            CoBill coBill = list.get(i);
            //计算总收入支出
            if (coBill.getIncome()) {
                t_income += coBill.getCost();
            } else {
                t_outcome += coBill.getCost();
            }

            //账单分类
            String sort = coBill.getSortName();
            List<CoBill> listBill;
            if (coBill.getIncome()) {
                if (mapIn.containsKey(sort)) {
                    listBill = mapIn.get(sort);
                } else {
                    listBill = new ArrayList<>();
                }
                if (moneyIn.containsKey(sort)) {
                    moneyIn.put(sort, moneyIn.get(sort) + coBill.getCost());
                } else {
                    moneyIn.put(sort, coBill.getCost());
                }
                listBill.add(coBill);
                mapIn.put(sort, listBill);
            } else {
                if (mapOut.containsKey(sort)) {
                    listBill = mapOut.get(sort);
                } else {
                    listBill = new ArrayList<>();
                }
                if (moneyOut.containsKey(sort)) {
                    moneyOut.put(sort, moneyOut.get(sort) + coBill.getCost());
                } else {
                    moneyOut.put(sort, coBill.getCost());
                }
                listBill.add(coBill);
                mapOut.put(sort, listBill);
            }
        }

        List<MonthChartBean.SortTypeList> outSortlist = new ArrayList<>();    //账单分类统计支出
        List<MonthChartBean.SortTypeList> inSortlist = new ArrayList<>();    //账单分类统计收入

        for (Map.Entry<String, List<CoBill>> entry : mapOut.entrySet()) {
            MonthChartBean.SortTypeList sortTypeList = new MonthChartBean.SortTypeList();
            sortTypeList.setList(entry.getValue());
            sortTypeList.setSortName(entry.getKey());
            sortTypeList.setSortImg(entry.getValue().get(0).getSortImg());
            sortTypeList.setMoney(moneyOut.get(entry.getKey()));
            sortTypeList.setBack_color(StringUtils.randomColor());
            outSortlist.add(sortTypeList);
        }
        for (Map.Entry<String, List<CoBill>> entry : mapIn.entrySet()) {
            MonthChartBean.SortTypeList sortTypeList = new MonthChartBean.SortTypeList();
            sortTypeList.setList(entry.getValue());
            sortTypeList.setSortName(entry.getKey());
            sortTypeList.setSortImg(entry.getValue().get(0).getSortImg());
            sortTypeList.setMoney(moneyIn.get(entry.getKey()));
            sortTypeList.setBack_color(StringUtils.randomColor());
            inSortlist.add(sortTypeList);
        }

        bean.setOutSortlist(outSortlist);
        bean.setInSortlist(inSortlist);
        bean.setTotalIn(t_income);
        bean.setTotalOut(t_outcome);
        return bean;
    }
}
