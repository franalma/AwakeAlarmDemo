package com.huawei.demo.serviceforeground.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.Window;
import android.widget.RemoteViews;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.huawei.demo.serviceforeground.AppWindowManager;
import com.huawei.demo.serviceforeground.R;


public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    Window window;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public ForegroundService() {

    }

    public void setWindow(Window window) {
        this.window = window;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(serviceChannel);
        }
    }


    private void startWorkThread(){
         new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("----remaining:"+millisUntilFinished);
            }
            @Override
            public void onFinish() {
                System.out.println("---- On timer finshed.");
                AppWindowManager.getInstance().unlockScreen(getBaseContext());
                AppWindowManager.getInstance().bringToFront(getApplicationContext());

            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Test Service")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContent(new RemoteViews(getPackageName(), R.layout.custom_notif))
                .setPriority(Notification.PRIORITY_MIN)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .build();
        startForeground(1,notification);
        startWorkThread();
        return START_STICKY;
    }

}
