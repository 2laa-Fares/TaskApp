package com.example.apit.task.repositories;


public interface TasksRepository {
    void tasks(OnRequestCompletedListener listener);
}
