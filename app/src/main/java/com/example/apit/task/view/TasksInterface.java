package com.example.apit.task.view;

import com.example.apit.task.model.Tasks;

public interface TasksInterface {
    void getTasks(Tasks[] tasks);
    void noTasks(String message);
}
