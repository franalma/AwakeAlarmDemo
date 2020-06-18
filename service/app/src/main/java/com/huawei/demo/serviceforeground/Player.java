package com.huawei.demo.serviceforeground;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class Player {
    private Ringtone ringotone;
    private static Player instance;
    private Uri notification;

    private Player () {
        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    }

    public void play(Context context){
        ringotone = RingtoneManager.getRingtone(context, notification);
        ringotone.play();
    }

    public void stop(){
        ringotone.stop();
    }

    public static Player getInstance(){
        if (instance == null){
            instance = new Player();
        }
        return instance;
    }
}
