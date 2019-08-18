package com.example.apit.task.view;

import com.example.apit.task.model.NEW_TASKS_ACTION;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.stp_users;

public interface ProceduresInterface {
    void Procedures(NEW_TASKS_ACTION[] actions);
    void by(stp_users[] stp_users);
    void taskNameList(SYSCODMTI[] SYSCODMTIS);
    void delete(Boolean result);
    void addAction(Boolean result);
    void pdfName(String pdf);
}
