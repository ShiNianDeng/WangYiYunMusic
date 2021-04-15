package com.example.lxc.wangyiyunmusic.application;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.lxc.wangyiyunmusic.helps.RealmHelp;
import com.example.lxc.wangyiyunmusic.helps.UserHelp;
import com.example.lxc.wangyiyunmusic.utils.SPUtils;

import io.realm.Realm;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Utils
        Utils.init(this);
        //初始化Realm
        Realm.init(this);
        RealmHelp.migration();
    }
}
