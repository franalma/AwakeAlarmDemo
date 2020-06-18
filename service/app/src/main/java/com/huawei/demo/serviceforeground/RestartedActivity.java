package com.huawei.demo.serviceforeground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.huawei.demo.serviceforeground.service.NormalService;

public class RestartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("---restarted activity");
        setContentView(R.layout.activity_restarted);
        AppWindowManager.getInstance().setWindow(getWindow());
        AppWindowManager.getInstance().setContext(getBaseContext());
        AppWindowManager.getInstance().setActivity(this);
        AppWindowManager.getInstance().sendToBackGround(RestartedActivity.class, this);
    }

}
