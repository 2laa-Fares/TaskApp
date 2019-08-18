package com.example.apit.task.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.apit.task.R;
import com.example.apit.task.SplashActivity;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.presenter.TasksPresenter;
import com.example.apit.task.repositories.imp.TasksRepositoryImp;
import com.example.apit.task.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksActivity extends AppCompatActivity implements TasksInterface {

    @BindView(R.id.tasks)
    RecyclerView tasksRecyclerView;
    @BindView(R.id.emptyTasksList)
    TextView emptyTaskList;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    TasksPresenter presenter;
    TasksInterface taskInterface;
    Tasks[] tasks;
    ProgressDialog progressDialog;
    int check = 0;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        App.setContext(this);
        context = App.getContext();


        progressDialog= new ProgressDialog(this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("يتم تحميل المهام...");
        progressDialog.show();

        final List<String> list = new ArrayList<String>();
        list.add("المهام الواردة حديثآ");
        list.add("المهام الحالية");
        list.add("المهام المتاخرة");
        list.add("المهام المنتهية");

        String selectedValue = PrefUtils.getKeys(context , context.getString(R.string.search_selected));
        int value = Integer.parseInt(selectedValue)-1;

        TextView textView = (TextView) findViewById(R.id.mytext);
        textView.setText(list.get(value));

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TasksActivity.this, R.layout.spinner, list);
        spinner.setAdapter(adapter);
        spinner.setSelection(Integer.parseInt(selectedValue)-1);

        Button btnChange = (Button)findViewById(R.id.dropdown_list);
        btnChange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                spinner.performClick();
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        if(++check > 1) {
                            int selected = position + 1;
                            PrefUtils.storeKeys(context, context.getString(R.string.search_selected), String.valueOf(selected));
                            Intent intent1 = new Intent(TasksActivity.this, TasksActivity.class);
                            startActivity(intent1);
                            finish();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });
            }
        });




        taskInterface = this;
        presenter = new TasksPresenter(new TasksRepositoryImp(), taskInterface);
        presenter.taskRequest();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.taskRequest();
            }
        });

        ImageButton logout = (ImageButton) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefUtils.clear();
                Intent intent = new Intent(TasksActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                /*moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);*/
            }
        });
    }

    @Override
    public void getTasks(Tasks[] tasks) {
        progressDialog.dismiss();
        this.tasks = tasks;
        emptyTaskList.setVisibility(View.GONE);
        tasksRecyclerView.setVisibility(View.VISIBLE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        tasksRecyclerView.setLayoutManager(layoutManager);
        TasksAdapter adapter = new TasksAdapter(tasks);
        tasksRecyclerView.setAdapter(adapter);
        onItemsLoadedComplete();
    }

    @Override
    public void noTasks(String message) {
        progressDialog.dismiss();
        tasksRecyclerView.setVisibility(View.GONE);
        emptyTaskList.setVisibility(View.VISIBLE);
        emptyTaskList.setText(message);
        onItemsLoadedComplete();
    }

    private void onItemsLoadedComplete() {
        swipeRefresh.setRefreshing(false);
    }


}
