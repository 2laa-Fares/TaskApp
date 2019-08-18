package com.example.apit.task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.apit.task.app.App;
import com.example.apit.task.utils.PrefUtils;
import com.example.apit.task.view.LoginActivity;
import com.example.apit.task.view.TasksActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setContext(this);
        String isLogin = PrefUtils.getKeys(App.getContext(), getString(R.string.login_key));
        if(isLogin != null){
            startActivity(new Intent(SplashActivity.this, TasksActivity.class));
            finish();
        }else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }
}
