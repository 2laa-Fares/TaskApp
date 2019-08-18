package com.example.apit.task.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.NEW_TASKS;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    NEW_TASKS task;
    Category category;
    int tabs;
    FailureFixing03_03[] _03_03Files;
    public ViewPagerAdapter(FragmentManager fm , NEW_TASKS task, Category category, int tabs, FailureFixing03_03[] _03_03Files) {
        super(fm);
        this.task = task;
        this.category = category;
        this.tabs = tabs;
        this._03_03Files = _03_03Files;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if(tabs == 4) {
            if (i == 0) {
                Task03_03Fragment fragment1 = new Task03_03Fragment();
                Bundle args = new Bundle();
                args.putSerializable("Task", task);
                args.putSerializable("Category", category);
                args.putSerializable("Files", _03_03Files);
                fragment1.setArguments(args);
                fragment = fragment1;

            }
            if (i == 1) {
                TaskAttachmentsFragment fragment1 = new TaskAttachmentsFragment();
                Bundle args = new Bundle();
                args.putSerializable("Task", task);
                args.putSerializable("Category", category);
                fragment1.setArguments(args);
                fragment = fragment1;

            } else if (i == 2) {
                ProceduresOnTasksFragment fragment1 = new ProceduresOnTasksFragment();
                Bundle args = new Bundle();
                args.putSerializable("Task", task);
                args.putSerializable("Category", category);
                args.putInt("Tabs", tabs);
                fragment1.setArguments(args);
                fragment = fragment1;

            } else if (i == 3) {
                TaskDetailsFragment fragment1 = new TaskDetailsFragment();
                Bundle args = new Bundle();
                args.putSerializable("Task", task);
                fragment1.setArguments(args);
                fragment = fragment1;
            }
        }else{
            if (i == 0) {
                TaskAttachmentsFragment fragment1 = new TaskAttachmentsFragment();
                Bundle args = new Bundle();
                args.putSerializable("Task", task);
                args.putSerializable("Category", category);
                fragment1.setArguments(args);
                fragment = fragment1;

            } else if (i == 1) {
                ProceduresOnTasksFragment fragment1 = new ProceduresOnTasksFragment();
                Bundle args = new Bundle();
                args.putSerializable("Task", task);
                args.putSerializable("Category", category);
                args.putInt("Tabs", tabs);
                fragment1.setArguments(args);
                fragment = fragment1;

            } else if (i == 2) {
                TaskDetailsFragment fragment1 = new TaskDetailsFragment();
                Bundle args = new Bundle();
                args.putSerializable("Task", task);
                fragment1.setArguments(args);
                fragment = fragment1;
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        if(tabs == 4) {
            if (position == 0) {
                //toolbarText.setText("محاضر القصور");
                title = "محاضر القصور";
            } else if (position == 1) {
                //toolbarText.setText("المرفقات");
                title = "المرفقات";
            } else if (position == 2) {
                //toolbarText.setText("الإجراء على المهمة");
                title = "الإجراء على المهمة";
            } else if (position == 3) {
                //toolbarText.setText("تفاصيل المهمة");
                title = "تفاصيل المهمة";
            }
        }else{
            if (position == 0) {
                //toolbarText.setText("المرفقات");
                title = "المرفقات";
            } else if (position == 1) {
                //toolbarText.setText("الإجراء على المهمة");
                title = "الإجراء على المهمة";
            } else if (position == 2) {
                //toolbarText.setText("تفاصيل المهمة");
                title = "تفاصيل المهمة";
            }
        }
        return title;
    }
}
