package com.huawei.demo.serviceforeground;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.huawei.demo.serviceforeground.service.ForegroundService;
import com.huawei.demo.serviceforeground.service.NormalService;

public class MainActivity extends AppCompatActivity implements NormalService.ServiceDelegate {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppWindowManager.getInstance().setWindow(getWindow());
        AppWindowManager.getInstance().setContext(getBaseContext());
        AppWindowManager.getInstance().setActivity(this);
    }

    @Override
    public void onBackPressed() { }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    public void start(View view){
        AppWindowManager.getInstance().startService(this);
    }

    public void stop(View view){
        AppWindowManager.getInstance().stop();
        Player.getInstance().stop();
    }

    @Override
    public void onServiceStarted() {
        System.out.println("---on service started");
    }
}

