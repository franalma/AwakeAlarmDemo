package com.huawei.demo.serviceforeground.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.huawei.demo.serviceforeground.AppWindowManager;
import com.huawei.demo.serviceforeground.Player;
import com.huawei.demo.serviceforeground.utils.CustomTimer;

public class NormalService extends Service implements CustomTimer.TimerDelegate{

    static CustomTimer timer;
    public static ServiceDelegate delegate;
    static long timerTick;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (timerTick < 1000)return;
        Intent intent = new Intent(getBaseContext(), BroadCastReceiver.class);
        intent = CustomTimer.prepareIntentForTimer(intent, timerTick);
        sendBroadcast(intent);
    }

    public static void start(){
        if (timer != null){
            timer.start();
        }
    }

    public static void stop(){
        if (timer != null){
            timer.cancel();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long timerValue = CustomTimer.getTimerIntentValue(intent);
        timer = new CustomTimer(timerValue, 1000, this);
        System.out.println("---timer: "+timer);
        timer.start();
        delegate.onServiceStarted();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onFinish() {
        System.out.println("---- On timer finshed."+hashCode());
        AppWindowManager.getInstance().unlockScreen(getBaseContext());
        AppWindowManager.getInstance().bringToFront(getBaseContext());
        Player.getInstance().play(getBaseContext());

    }

    @Override
    public void onTick(long value) {
        timerTick = value;
        System.out.println("-------------------remaining:"+value);
    }

    public interface ServiceDelegate{
        void onServiceStarted();
    }
}
