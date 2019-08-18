package com.example.apit.task.repositories.imp;

import com.example.apit.task.model.NEW_TASKS_ACTION;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.stp_users;

public interface OnRequestCompletedListenerProcedures {
    void onRequestCompleted(NEW_TASKS_ACTION[] actions);
    void onRequestCompleted(stp_users[] stp_users);
    void onRequestCompleted(SYSCODMTI[] SYSCODMTIS);
    void onRequestCompleted(Boolean deleteResult);
    void onRequestCompleted(String result);
}
