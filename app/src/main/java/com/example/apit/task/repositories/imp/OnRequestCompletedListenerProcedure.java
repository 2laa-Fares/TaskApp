package com.example.apit.task.repositories.imp;

import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.stp_users;

public interface OnRequestCompletedListenerProcedure {
    void onRequestComplete(SYSCODMTI[] SYSCODMTIS);
    void onRequestComplete(stp_users[] stp_users);
    void onRequestComplete(Boolean result);
}
