package com.lulu.folatingvideodemo;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Lulu on 2016/10/22.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        //初始化xUtils
        x.Ext.init(this);
    }
}
