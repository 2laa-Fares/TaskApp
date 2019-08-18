package com.example.apit.task.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.presenter._03_03Presenter;
import com.example.apit.task.repositories.imp._03_03RepositoryImp;
import com.example.apit.task.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskActivity extends AppCompatActivity implements _03_03Interface{

    NEW_TASKS task;
    Category category;
    Integer tab = null;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    ViewPagerAdapter viewPagerAdapter;
    _03_03Interface Interface;
    _03_03Presenter presenter;
    ProgressDialog progressDialog;
    @SuppressLint("ResourceAsColor")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        App.setContext(this);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        RelativeLayout spinner = (RelativeLayout) findViewById(R.id.spinnerlayout);
        spinner.setVisibility(View.GONE);

        final ImageButton logout = (ImageButton) findViewById(R.id.logout);
        logout.setBackgroundResource(R.mipmap.back);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TaskActivity.this, TasksActivity.class);
                startActivity(i);
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        task = (NEW_TASKS) extras.getSerializable("Task");
        category = (Category) extras.getSerializable("Category");
        tab = extras.getInt("Tab");
        //PrefUtils.storeKeys(App.getContext(), "head", String.valueOf(category.IS_HEADMASTER));

        if (task != null) {
            if (task.HOUSING_SYSKEY != null) {
                progressDialog= new ProgressDialog(this,R.style.AppTheme_Dark_Dialog);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("يتم تحميل البيانات...");
                progressDialog.show();
                Interface = this;
                presenter = new _03_03Presenter(new _03_03RepositoryImp(), Interface);
                presenter.get03_03(String.valueOf(task.HOUSING_SYSKEY));
            }
            else {
                viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager() , task, null,3, null);
                viewPager.setAdapter(viewPagerAdapter);
                viewPager.setCurrentItem(2);
                tabLayout.setupWithViewPager(viewPager);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent MainActivityIntent = new Intent(TaskActivity.this, TasksActivity.class);
        startActivity(MainActivityIntent);
        TaskActivity.this.finish();
        super.onBackPressed();
    }


    public NEW_TASKS getTask(){
        return this.task;
    }

    @Override
    public void result(FailureFixing03_03[] _03_03Files) {
        progressDialog.dismiss();
        if(_03_03Files != null && _03_03Files.length > 0) {
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager() , task, category, 4, _03_03Files);
            viewPager.setAdapter(viewPagerAdapter);
            if(tab != null && tab != 0 ) viewPager.setCurrentItem(tab-1);
            else viewPager.setCurrentItem(3);
            tabLayout.setupWithViewPager(viewPager);
        }else{
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager() , task, null, 3, null);
            viewPager.setAdapter(viewPagerAdapter);
            if(tab != null && tab != 1 ) viewPager.setCurrentItem(tab-1);
            else viewPager.setCurrentItem(2);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
