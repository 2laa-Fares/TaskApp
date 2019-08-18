package com.example.apit.task.view;

import com.example.apit.task.model.Category;
import com.example.apit.task.model.NEW_TASKS;

public interface NewTaskInterface {
    void RetrieveTask(NEW_TASKS Task);
    void EditTask(String value);
    void NotTaskFound(String error);
    void RetrieveCategory(Category Task);
    void NotCategory(String error);
}
