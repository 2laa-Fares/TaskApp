package com.example.apit.task.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.NEW_TASKS;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Task03_03Fragment extends Fragment {

    @BindView(R.id.house_no)
    TextView house_no;
    @BindView(R.id.questionnairesRecyclerView)
    RecyclerView questionnairesRecyclerView;

    FailureFixing03_03[] _03_03Files;
    NEW_TASKS task;
    Category category;

    Context context = App.getContext();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_03_03_fragment, container, false);
        ButterKnife.bind(this, view);

        context = App.getContext();

        /*TextView toolbatTitle =((TaskActivity) getActivity()).findViewById(R.id.mytext);
        toolbatTitle.setText(" محاضر القصور");*/

        if (getArguments() != null) {
            task = (NEW_TASKS) getArguments().getSerializable("Task");
            category = (Category) getArguments().getSerializable("Category");
            _03_03Files = (FailureFixing03_03[]) getArguments().getSerializable("Files");
            if (task != null) {
                house_no.setText("  " + task.HOUSING_BUILDING_ID + "    ");
                if(_03_03Files != null) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                    questionnairesRecyclerView.setLayoutManager(layoutManager);
                    _03_03Adapter adapter = new _03_03Adapter(_03_03Files, task, category);
                    questionnairesRecyclerView.setAdapter(adapter);
                }
            }
        }
        return view;
    }
}