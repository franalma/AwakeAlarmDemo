package com.huawei.demo.serviceforeground.utils;

import android.content.Intent;
import android.os.CountDownTimer;

public class CustomTimer extends CountDownTimer {

    private TimerDelegate delegate;

    public CustomTimer(long millisInFuture, long countDownInterval, TimerDelegate delegate) {
        super(millisInFuture, countDownInterval);
        this.delegate = delegate;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        delegate.onTick(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        this.delegate.onFinish();
    }

    public static Intent prepareIntentForTimer(Intent intent, long timerValue){
        return  intent.putExtra("timer", timerValue);
    }

    public static long getTimerIntentValue(Intent intent){
        return intent.getLongExtra("timer", -1);
    }

    public interface TimerDelegate{
        void onFinish();
        void onTick(long value);

    }
}
