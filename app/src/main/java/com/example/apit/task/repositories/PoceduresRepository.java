package com.example.apit.task.repositories;

import com.example.apit.task.repositories.imp.OnRequestCompletedListenerProcedures;

import java.util.Date;

public interface PoceduresRepository {
    void procedures(String taskNO, OnRequestCompletedListenerProcedures listener);
    void taskNameList2(String syskey,OnRequestCompletedListenerProcedures listener);
    void deleteAction(String syskey, String userId, OnRequestCompletedListenerProcedures listener);
    void addAction(String action, OnRequestCompletedListenerProcedures listener);
    void pdfName(int taskID, int actionID,  OnRequestCompletedListenerProcedures listener);
}
