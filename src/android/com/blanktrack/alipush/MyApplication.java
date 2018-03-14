package com.blanktrack.alipush;

import android.app.Application;

import static com.blanktrack.alipush.AliPushPlugin.initCloudChannel;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initCloudChannel(this);
    }

}
