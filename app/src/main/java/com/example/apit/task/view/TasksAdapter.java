package com.example.apit.task.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.presenter.LoginPresenter;
import com.example.apit.task.presenter.NewTaskPresenter;
import com.example.apit.task.repositories.imp.NewTaskRepositoryImp;
import com.example.apit.task.utils.PrefUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.tasksViewHolder>{

    private Tasks[] tasks;
    private NEW_TASKS task;
    private Category userCategory;
    private Context context;
    private ProgressDialog progressDialog;
    private NewTaskPresenter newTaskPresenter;


    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    TasksAdapter(Tasks[] tasks){
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public tasksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_card, viewGroup, false);
        return new tasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tasksViewHolder tasksViewHolder, int position) {
        //Assign tasks data to fields.
        if (tasks[position].TASK_KEY_AR!=null)
            tasksViewHolder.task_number.setText(tasks[position].TASK_KEY_AR);
        if (tasks[position].TASK_NAME!=null)
            tasksViewHolder.task_name.setText(tasks[position].TASK_NAME);
        if (tasks[position].TASK_PRIORITY!=null)
            tasksViewHolder.task_priority.setText(tasks[position].TASK_PRIORITY);
        if (tasks[position].TASK_STATUS!=null)
            tasksViewHolder.task_status.setText(tasks[position].TASK_STATUS);
        if (tasks[position].TASK_REQUEST!=null)
            tasksViewHolder.task_req.setText(tasks[position].TASK_REQUEST);
        if (tasks[position].TASK_CATEGORY!=null)
            tasksViewHolder.task_type.setText(tasks[position].TASK_CATEGORY);
        if (tasks[position].TASK_START_DATE_HIJRI_AR!=null)
            tasksViewHolder.task_start_date.setText(tasks[position].TASK_START_DATE_HIJRI_AR);
        if (tasks[position].TASK_END_DATE_HIJRI_AR!=null)
            tasksViewHolder.task_end_date.setText(tasks[position].TASK_END_DATE_HIJRI_AR);
        if (tasks[position].USER_RESPONSIABLE!=null)
            tasksViewHolder.task_rperson.setText(tasks[position].USER_RESPONSIABLE);

        context = App.getContext();

        String selected = PrefUtils.getKeys(context , context.getString(R.string.search_selected));

        if(selected.equals("1")){
            tasksViewHolder.edit_gold.setVisibility(PercentRelativeLayout.GONE);
        }else{
                tasksViewHolder.remove_gold.setVisibility(PercentRelativeLayout.GONE);
                tasksViewHolder.accept.setVisibility(View.GONE);
        }

        final int finalPosition = position;

        tasksViewHolder.accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                NewTaskInterface newTaskInterface = new NewTaskInterface() {
                    @Override
                    public void RetrieveTask(NEW_TASKS Task) {
                    }

                    @Override
                    public void EditTask(String value) {
                        progressDialog.dismiss();
                        Intent i = new Intent(App.getContext(), TasksActivity.class);
                        App.getContext().startActivity(i);
                    }

                    @Override
                    public void NotTaskFound(String error) {
                        progressDialog.dismiss();
                        Snackbar.make(swipeRefreshLayout, error, Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void RetrieveCategory(Category Task) {

                    }

                    @Override
                    public void NotCategory(String error) {

                    }
                };
                newTaskPresenter = new NewTaskPresenter(newTaskInterface, new NewTaskRepositoryImp());
                Edit(tasks[finalPosition] , "Accept");
            }
        });

        tasksViewHolder.reject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                final View promptsView = li.inflate(R.layout.prompts, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setPositiveButton("حفظ", null);
                alert.setNegativeButton("الغاء", null);
                alert.setView(promptsView);

                // Set an EditText view to get user input
                final EditText input = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                final TextView massage = (TextView) promptsView.findViewById(R.id.massage);

                final AlertDialog mAlertDialog = alert.create();
                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog) {

                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setBackgroundColor(0xC9AE5D);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                // TODO Do something
                                if (input.getText().toString().equals("")) {
                                    massage.setText("من فضلك ادخل سبب الرفض...");
                                } else {
                                    NewTaskInterface newTaskInterface = new NewTaskInterface() {
                                        @Override
                                        public void RetrieveTask(NEW_TASKS Task) {
                                        }

                                        @Override
                                        public void EditTask(String value) {
                                            progressDialog.dismiss();
                                            Intent i = new Intent(App.getContext(), TaskActivity.class);
                                            App.getContext().startActivity(i);
                                        }

                                        @Override
                                        public void NotTaskFound(String error) {
                                            progressDialog.dismiss();
                                        }

                                        @Override
                                        public void RetrieveCategory(Category category) {
                                        }

                                        @Override
                                        public void NotCategory(String error) {

                                        }
                                    };


                                    newTaskPresenter = new NewTaskPresenter(newTaskInterface, new NewTaskRepositoryImp());
                                    Edit(tasks[finalPosition], input.getText().toString());
                                    mAlertDialog.dismiss();
                                }
                            }
                        });


                        Button c = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        c.setBackgroundColor(0xC9AE5D);
                        c.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                // Canceled.
                                mAlertDialog.dismiss();
                            }
                        });

                    }
                });
                mAlertDialog.show();
            }
        });

        tasksViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NewTaskInterface newTaskInterface = new NewTaskInterface() {
                    @Override
                    public void RetrieveTask(NEW_TASKS Task) {
                        task = Task;
                        Intent i = new Intent(App.getContext(), TaskActivity.class);
                        Bundle b = new Bundle();
                        b.putSerializable("Task", task);
                        b.putSerializable("Category", userCategory);
                        i.putExtras(b);
                        App.getContext().startActivity(i);
                        ((TasksActivity)context).finish();
                    }

                    @Override
                    public void EditTask(String value) {

                    }

                    @Override
                    public void NotTaskFound(String error) {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void RetrieveCategory(Category category) {
                        progressDialog.dismiss();
                        if (category!=null) {
                            userCategory = category;
                            Show(tasks[finalPosition]);
                        }else {
                            Show();
                        }

                    }

                    @Override
                    public void NotCategory(String error) {
                        progressDialog.dismiss();
                    }
                };
                newTaskPresenter = new NewTaskPresenter(newTaskInterface, new NewTaskRepositoryImp());
                Show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (tasks == null){
            return 0;
        }
        return tasks.length;
    }

    class tasksViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.task_card)
        CardView task_card;
        @BindView(R.id.task_number)
        TextView task_number;
        @BindView(R.id.task_name)
        TextView task_name;
        @BindView(R.id.task_priority)
        TextView task_priority;
        @BindView(R.id.task_status)
        TextView task_status;
        @BindView(R.id.task_req)
        TextView task_req;
        @BindView(R.id.task_type)
        TextView task_type;
        @BindView(R.id.task_start_date)
        TextView task_start_date;
        @BindView(R.id.task_end_date)
        TextView task_end_date;
        @BindView(R.id.task_rperson)
        TextView task_rperson;
        @BindView(R.id.remove_gold)
        PercentRelativeLayout remove_gold;
        @BindView(R.id.edit_gold)
        PercentRelativeLayout edit_gold;
        @BindView(R.id.accept)
        Button accept;
        @BindView(R.id.reject)
        Button reject;
        @BindView(R.id.edit)
        Button edit;

        private tasksViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private void Edit(Tasks tasks , String type) {
        progressDialog= new ProgressDialog(context,R.style.AppTheme_Dark_Dialog);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("يتم تحميل بيانات المهمة...");
        progressDialog.show();
        String syskey = String.valueOf(tasks.TASK_SYSKEY);
        newTaskPresenter.getTask(syskey , type);
        newTaskPresenter.getCategory();
    }

    private void Show() {
        progressDialog= new ProgressDialog(context,R.style.AppTheme_Dark_Dialog);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("يتم تحميل بيانات المهمة...");
        progressDialog.show();
        newTaskPresenter.getCategory();
    }

    private void Show(Tasks tasks) {
        String syskey = String.valueOf(tasks.TASK_SYSKEY);
        newTaskPresenter.Task(syskey);
    }



}
