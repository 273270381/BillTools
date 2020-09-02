package com.example.billtools.model.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.billtools.model.been.local.BBill;
import com.example.billtools.model.been.local.BPay;
import com.example.billtools.model.been.local.BSort;

import com.example.billtools.model.gen.BBillDao;
import com.example.billtools.model.gen.BPayDao;
import com.example.billtools.model.gen.BSortDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bBillDaoConfig;
    private final DaoConfig bPayDaoConfig;
    private final DaoConfig bSortDaoConfig;

    private final BBillDao bBillDao;
    private final BPayDao bPayDao;
    private final BSortDao bSortDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bBillDaoConfig = daoConfigMap.get(BBillDao.class).clone();
        bBillDaoConfig.initIdentityScope(type);

        bPayDaoConfig = daoConfigMap.get(BPayDao.class).clone();
        bPayDaoConfig.initIdentityScope(type);

        bSortDaoConfig = daoConfigMap.get(BSortDao.class).clone();
        bSortDaoConfig.initIdentityScope(type);

        bBillDao = new BBillDao(bBillDaoConfig, this);
        bPayDao = new BPayDao(bPayDaoConfig, this);
        bSortDao = new BSortDao(bSortDaoConfig, this);

        registerDao(BBill.class, bBillDao);
        registerDao(BPay.class, bPayDao);
        registerDao(BSort.class, bSortDao);
    }
    
    public void clear() {
        bBillDaoConfig.clearIdentityScope();
        bPayDaoConfig.clearIdentityScope();
        bSortDaoConfig.clearIdentityScope();
    }

    public BBillDao getBBillDao() {
        return bBillDao;
    }

    public BPayDao getBPayDao() {
        return bPayDao;
    }

    public BSortDao getBSortDao() {
        return bSortDao;
    }

}
