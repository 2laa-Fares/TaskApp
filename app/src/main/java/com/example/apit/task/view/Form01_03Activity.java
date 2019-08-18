package com.example.apit.task.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.NEW_TASKS;

public class Form01_03Activity extends AppCompatActivity {

    int tabs;
    NEW_TASKS task;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form01_03);
        App.setContext(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        RelativeLayout spinner = (RelativeLayout) findViewById(R.id.spinnerlayout);
        spinner.setVisibility(View.GONE);

        task =(NEW_TASKS) getIntent().getSerializableExtra("task");
        int taskID = task.TASK_SYSKEY;
        category = (Category) getIntent().getSerializableExtra("category");
        int issueID = getIntent().getIntExtra("issueId", 0);
        int houseID = task.HOUSING_SYSKEY;
        tabs = getIntent().getIntExtra("tabs", 0);

        final ImageButton logout = (ImageButton) findViewById(R.id.logout);
        logout.setBackgroundResource(R.mipmap.back);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Form01_03Activity.this, TaskActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("Task", task);
                b.putSerializable("Category", category);
                if(tabs == 4) b.putInt("Tab", 3);
                else b.putInt("Tab",2);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        });

        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        bundle.putInt("tabs", tabs);
        bundle.putSerializable("category" , category);
        bundle.putInt("issueId", issueID);
        bundle.putInt("housingId", houseID);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RevealedPilgrimsHousingFormFragment fragment = new RevealedPilgrimsHousingFormFragment();
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragment,fragment);
        transaction.commit();
    }


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Form01_03Activity.this, TaskActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("Task", task);
        b.putSerializable("Category", category);
        if(tabs == 4) b.putInt("Tab", 3);
        else b.putInt("Tab",2);
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}
