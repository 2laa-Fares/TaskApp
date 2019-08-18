package com.example.apit.task.repositories;

public interface NewTaskRepository {
    void task(String taskID, String type, OnRequestCompletedListener listener);
    void getTask(String taskID, OnRequestCompletedListener listener);
    void category(OnRequestCompletedListener listener);
}
