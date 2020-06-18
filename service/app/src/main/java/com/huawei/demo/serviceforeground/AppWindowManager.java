package com.huawei.demo.serviceforeground;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;
import com.huawei.demo.serviceforeground.service.NormalService;
import com.huawei.demo.serviceforeground.utils.CustomTimer;

public class AppWindowManager {
    private static AppWindowManager instance;
    private Window window;
    private Context context;
    private Activity activity;
    private PowerManager.WakeLock wakeLock = null;

    public Window getWindow() {
        return window;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public static AppWindowManager getInstance(){
        if (instance == null){
            instance = new AppWindowManager();
        }
        return instance;
    }
    public void startService(NormalService.ServiceDelegate delegate){
        NormalService.delegate = delegate;
        Intent intent =  new Intent(activity,NormalService.class);
        CustomTimer.prepareIntentForTimer(intent, 10000);
        activity.startService(intent);
    }


    public void continueService(Context context,Intent prevIntent){
        System.out.println("---continue service");
        long timerValue = CustomTimer.getTimerIntentValue(prevIntent);
        System.out.println("---Timer value: "+timerValue);
        Intent intent =  new Intent(context,RestartedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        CustomTimer.prepareIntentForTimer(intent, timerValue );
        context.startActivity(intent);
    }

    public void stop(){
        NormalService.stop();
    }

    @SuppressLint("InvalidWakeLockTag")
    public  void unlockScreen(Context context) {

        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        assert pm != null;
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"test");
        wakeLock.acquire(10*60);
    }

    public void bringToFront(Context context){
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(i);
    }

    public void sendToBackGround(Class clz, Activity activity){
        Intent i = new Intent(activity, clz);
        activity.moveTaskToBack(true);
    }
}
