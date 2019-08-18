package com.example.apit.task.presenter;

import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.FileInfo;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.model.stp_users;
import com.example.apit.task.repositories.OnRequestCompletedListener;
import com.example.apit.task.repositories.TasksRepository;
import com.example.apit.task.view.TasksInterface;

public class
TasksPresenter {
    private TasksRepository taskRepository;
    private TasksInterface taskInterface;
    public TasksPresenter(TasksRepository taskRepository, TasksInterface taskInterface){
        this.taskInterface = taskInterface;
        this.taskRepository = taskRepository;
    }

    public void taskRequest(){
        OnRequestCompletedListener completedListener = new OnRequestCompletedListener() {
            @Override
            public void onRequestCompleted(String user) {}
            @Override
            public void onRequestCompleted(Tasks[] tasks) {
                if(tasks != null) {
                    taskInterface.getTasks(tasks);
                }else {
                    taskInterface.noTasks("لا يوجد مهام متاحه الان...");
                }
            }

            @Override
            public void onRequestCompleted(NEW_TASKS task) {

            }


            @Override
            public void onRequestCompleted(Category category) {

            }

            @Override
            public void onRequestCompleted(SYSCODMTI[] dropdowns) {

            }

            @Override
            public void onRequestCompleted(stp_users[] stp_users) {

            }

            @Override
            public void onRequestCompleted(Boolean result) {

            }

            @Override
            public void onRequestComplete(FileInfo[] fileInfos) {

            }

            @Override
            public void onRequestComplete(FailureFixing03_03[] _03_03Files) {

            }
        };
        taskRepository.tasks(completedListener);
    }
}
