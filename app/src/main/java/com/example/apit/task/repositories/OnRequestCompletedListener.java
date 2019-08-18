package com.example.apit.task.repositories;


import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.FileInfo;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.NEW_TASKS_ACTION;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.model.stp_users;

public interface OnRequestCompletedListener {
    void onRequestCompleted(String user);
    void onRequestCompleted(Tasks[] tasks);
    void onRequestCompleted(NEW_TASKS task);
    void onRequestCompleted(Category category);
    void onRequestCompleted(SYSCODMTI[] dropdowns);
    void onRequestCompleted(stp_users[] stp_users);
    void onRequestCompleted(Boolean result);
    void onRequestComplete(FileInfo[] fileInfos);
    void onRequestComplete(FailureFixing03_03[] _03_03Files);
}
