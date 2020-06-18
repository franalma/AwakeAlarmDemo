package com.huawei.demo.serviceforeground.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.huawei.demo.serviceforeground.AppWindowManager;

public class BroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWindowManager.getInstance().continueService(context, intent);
    }
}
