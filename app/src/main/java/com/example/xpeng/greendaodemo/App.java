package com.example.xpeng.greendaodemo;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // regular SQLite database
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "collection-db");
        Database db = helper.getWritableDb();

        mDaoSession = new DaoMaster(db).newSession();

    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
