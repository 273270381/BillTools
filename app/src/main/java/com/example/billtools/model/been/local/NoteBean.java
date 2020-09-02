package com.example.billtools.model.been.local;

import com.example.billtools.base.BaseBeen;

import java.util.List;

/**
 * Created by hejunfeng on 2020/8/29 0029
 */
public class NoteBean extends BaseBeen {
    private List<BSort> outSortlis;
    private List<BSort> inSortlis;
    private List<BPay> payinfo;

    public List<BSort> getOutSortlis() {
        return outSortlis;
    }

    public List<BSort> getInSortlis() {
        return inSortlis;
    }

    public List<BPay> getPayinfo() {
        return payinfo;
    }

    public void setOutSortlis(List<BSort> outSortlis) {
        this.outSortlis = outSortlis;
    }

    public void setInSortlis(List<BSort> inSortlis) {
        this.inSortlis = inSortlis;
    }

    public void setPayinfo(List<BPay> payinfo) {
        this.payinfo = payinfo;
    }
}
