package com.gy.hospital.net;

import com.gy.hospital.utils.Utils;

import org.xutils.DbManager;
import org.xutils.x;

public enum Db {

    INSTANCE;

    public DbManager getDb() {
        return x.getDb(Utils.daoConfig);
    }


}
