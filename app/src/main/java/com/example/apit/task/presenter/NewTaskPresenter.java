package com.example.apit.task.presenter;

import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.FileInfo;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.model.stp_users;
import com.example.apit.task.repositories.NewTaskRepository;
import com.example.apit.task.repositories.OnRequestCompletedListener;
import com.example.apit.task.view.NewTaskInterface;

public class NewTaskPresenter {
        NewTaskInterface taskInterface;
        NewTaskRepository taskRepository;

        public NewTaskPresenter(NewTaskInterface taskInterface, NewTaskRepository taskRepository){
            this.taskInterface = taskInterface;
            this.taskRepository = taskRepository;
        }

        public void getTask(String taskID , String type){
            OnRequestCompletedListener listener = new OnRequestCompletedListener() {
                @Override
                public void onRequestCompleted(String user) {
                    if(user.equals("false")){
                        taskInterface.NotTaskFound("Please check your connection and try again later.");
                    }else {
                        taskInterface.EditTask(user);
                    }
                }

                @Override
                public void onRequestCompleted(Tasks[] tasks) {

                }

                @Override
                public void onRequestCompleted(NEW_TASKS task) {
                    if(task == null){
                        taskInterface.NotTaskFound("");
                    }else {
                        taskInterface.RetrieveTask(task);
                    }
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
            taskRepository.task(taskID,type,listener);

        }

        public void Task(String taskID){
        OnRequestCompletedListener listener = new OnRequestCompletedListener() {
            @Override
            public void onRequestCompleted(String user) {
            }

            @Override
            public void onRequestCompleted(Tasks[] tasks) {

            }

            @Override
            public void onRequestCompleted(NEW_TASKS task) {
                if(task == null){
                    taskInterface.NotTaskFound("");
                }else {
                    taskInterface.RetrieveTask(task);
                }
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
        taskRepository.getTask(taskID, listener);

    }

        public void getCategory(){
        OnRequestCompletedListener listener = new OnRequestCompletedListener() {
            @Override
            public void onRequestCompleted(String user) {
            }

            @Override
            public void onRequestCompleted(Tasks[] tasks) {

            }

            @Override
            public void onRequestCompleted(NEW_TASKS task) {
            }

            @Override
            public void onRequestCompleted(Category category) {
                if(category == null){
                    taskInterface.NotCategory("");
                }else {
                    taskInterface.RetrieveCategory(category);
                }
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
        taskRepository.category(listener);

    }
}
