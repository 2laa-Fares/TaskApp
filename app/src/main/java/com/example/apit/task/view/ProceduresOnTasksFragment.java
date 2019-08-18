package com.example.apit.task.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.GoalRow;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Action;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.NEW_TASKS_ACTION;
import com.example.apit.task.model.stp_users;
import com.example.apit.task.presenter.ProceduresPresenter;
import com.example.apit.task.repositories.imp.ProceduresRepositoryImp;
import com.example.apit.task.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.GET;

public class ProceduresOnTasksFragment extends Fragment implements ProceduresInterface {

    @BindView(R.id.proceduresRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.addFloatingActionButton)
    FloatingActionButton addFloatingActionButton;
    @BindView(R.id.popup)
    ConstraintLayout popup;
    @BindView(R.id.actionsSpinner)
    Spinner actionsSpinner;
    @BindView(R.id.notesEditText )
    EditText notes;
    @BindView(R.id.saveButton)
    Button saveButton;
    @BindView(R.id.cancelButton)
    Button cancelButton;
    @BindView(R.id.noActions)
    TextView noActionsTextView;
    ProceduresInterface proceduresInterface;
    ProceduresPresenter presenter;
    List<NEW_TASKS_ACTION> actions = new ArrayList();
    SYSCODMTI[] actionNames;
    NEW_TASKS task;
    Category category;
    String userId, isHeadMaster;
    int tabs;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.procedures_on_tasks_fragment, container, false);
        ButterKnife.bind(this, view);

        task = (NEW_TASKS) getArguments().getSerializable("Task");
        category = (Category) getArguments().getSerializable("Category");
        tabs = getArguments().getInt("Tabs");
        context = App.getContext();
        userId = PrefUtils.getKeys(context, context.getString(R.string.user_id));

        proceduresInterface = this;
        presenter = new ProceduresPresenter(proceduresInterface, new ProceduresRepositoryImp());
        presenter.getProcedures(String.valueOf(task.TASK_SYSKEY));
        presenter.detTaskNameList(String.valueOf(task.TASK_NAME_LIST));

        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                if (actionNames != null) {
                    popup.setVisibility(View.VISIBLE);
                    noActionsTextView.setVisibility(View.GONE);
                    addFloatingActionButton.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }else{
                    Toast.makeText(context, "لا يوجد إجراءات متاحة لهذه القضية", Toast.LENGTH_LONG).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                popup.setVisibility(View.GONE);
                addFloatingActionButton.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Action action = new Action();
                action.CASE_NO = task.TASK_SYSKEY;
                action.ACTION_NOTE = notes.getText().toString();
                action.ACTION_USERKEY = Integer.valueOf(userId);
                for (SYSCODMTI syscodmti: actionNames){
                    if (syscodmti.ADESC.equals(actionsSpinner.getSelectedItem().toString())){
                        action.ACTION_TAKEN = syscodmti.SIKEY;
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(action);
                presenter.addAction(json);

            }
        });
        return view;
    }

    @Override
    public void Procedures(NEW_TASKS_ACTION[] actions) {
        if (actions != null && actions.length > 0) {
            this.actions.clear();
            noActionsTextView.setVisibility(View.GONE);
            for (NEW_TASKS_ACTION action: actions){
                if (action.ACTION_TAKEN!=null){
                    this.actions.add(action);
                }
            }
            presenter.detTaskNameList(String.valueOf(task.TASK_NAME_LIST));
        }else{
            recyclerView.setVisibility(View.GONE);
            noActionsTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void by(stp_users[] stp_users) {

    }

    @Override
    public void taskNameList(SYSCODMTI[] SYSCODMTIS) {
        if (SYSCODMTIS != null) {
            actionNames = SYSCODMTIS;
            String[] action = new String[SYSCODMTIS.length];
            for (int i = 0; i<action.length; i++){
                action[i] = SYSCODMTIS[i].ADESC;
            }
            ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, action);
            actionsSpinner.setAdapter(spinnerArrayAdapter1);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            ProceduresAdapter adapter = new ProceduresAdapter(task, actions, SYSCODMTIS, category, tabs, new ProceduresAdapter.OnItemClickListener() {

                @Override
                public void onDeleteClick(String syskey) {
                    presenter.deleteAction(syskey, userId);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            presenter.detTaskNameList(String.valueOf(task.TASK_NAME_LIST));
        }
    }

    @Override
    public void delete(Boolean result) {
        if (result) {
            presenter.getProcedures(String.valueOf(task.TASK_SYSKEY));
            Toast.makeText(App.getContext(), "تم حذف الإجراء", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(App.getContext(), "فشل حذف الإجراء", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void addAction(Boolean result) {
        if (result == false){
            Toast.makeText(App.getContext(), "فشل اضافة الإجراء", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(App.getContext(), "تم اضافة الإجراء", Toast.LENGTH_LONG).show();
            presenter.getProcedures(String.valueOf(task.TASK_SYSKEY));
            popup.setVisibility(View.GONE);
            addFloatingActionButton.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void pdfName(String pdf) {

    }
}
