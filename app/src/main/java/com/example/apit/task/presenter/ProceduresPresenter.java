package com.example.apit.task.presenter;

import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.NEW_TASKS_ACTION;
import com.example.apit.task.model.stp_users;
import com.example.apit.task.repositories.AddActionRepository;
import com.example.apit.task.repositories.PoceduresRepository;
import com.example.apit.task.repositories.imp.OnRequestCompletedListenerProcedure;
import com.example.apit.task.repositories.imp.OnRequestCompletedListenerProcedures;
import com.example.apit.task.view.ProceduresInterface;

import java.util.Date;

public class ProceduresPresenter {
    ProceduresInterface proceduresInterface;
    PoceduresRepository repository;
    AddActionRepository repository2;

    public ProceduresPresenter(ProceduresInterface proceduresInterface, PoceduresRepository repository){
        this.proceduresInterface = proceduresInterface;
        this.repository = repository;
    }
    public ProceduresPresenter(ProceduresInterface proceduresInterface, AddActionRepository repository){
        this.proceduresInterface = proceduresInterface;
        this.repository2 = repository;
    }

    public void getProcedures(String taskNO){
        OnRequestCompletedListenerProcedures listenerProcedures = new OnRequestCompletedListenerProcedures() {
            @Override
            public void onRequestCompleted(NEW_TASKS_ACTION[] actions) {
                if(actions != null){
                    proceduresInterface.Procedures(actions);
                }else {
                    proceduresInterface.Procedures(null);
                }
            }

            @Override
            public void onRequestCompleted(stp_users[] stp_users) {

            }

            @Override
            public void onRequestCompleted(SYSCODMTI[] dropdowns) {

            }

            @Override
            public void onRequestCompleted(Boolean deleteResult) {

            }

            @Override
            public void onRequestCompleted(String result) {

            }
        };
        repository.procedures(taskNO, listenerProcedures);
    }

    public void getByUsers(){
        OnRequestCompletedListenerProcedure listenerProcedure = new OnRequestCompletedListenerProcedure() {
            @Override
            public void onRequestComplete(SYSCODMTI[] dropdowns) {

            }

            @Override
            public void onRequestComplete(stp_users[] stp_users) {
                if (stp_users != null){
                    proceduresInterface.by(stp_users);
                }else {
                    proceduresInterface.by(null);
                }
            }

            @Override
            public void onRequestComplete(Boolean result) {

            }
        };
        repository2.by(listenerProcedure);
    }

    public void detTaskNameList(String taskNameList){
        OnRequestCompletedListenerProcedures listener = new OnRequestCompletedListenerProcedures() {
            @Override
            public void onRequestCompleted(NEW_TASKS_ACTION[] actions) {

            }

            @Override
            public void onRequestCompleted(stp_users[] stp_users) {

            }

            @Override
            public void onRequestCompleted(SYSCODMTI[] dropdowns) {
                if (dropdowns != null){
                    proceduresInterface.taskNameList(dropdowns);
                }else {
                    proceduresInterface.taskNameList(null);
                }
            }

            @Override
            public void onRequestCompleted(Boolean deleteResult) {

            }

            @Override
            public void onRequestCompleted(String result) {

            }
        };
        repository.taskNameList2(taskNameList, listener);
    }

    public void deleteAction(String syskey, String userId){
        final OnRequestCompletedListenerProcedures listener = new OnRequestCompletedListenerProcedures() {

            @Override
            public void onRequestCompleted(NEW_TASKS_ACTION[] actions) {

            }

            @Override
            public void onRequestCompleted(stp_users[] stp_users) {

            }

            @Override
            public void onRequestCompleted(SYSCODMTI[] SYSCODMTIS) {

            }

            @Override
            public void onRequestCompleted(Boolean deleteResult) {
                proceduresInterface.delete(deleteResult);
            }

            @Override
            public void onRequestCompleted(String result) {

            }
        };
        repository.deleteAction(syskey, userId, listener);
    }

    public void addAction(String action) {
        OnRequestCompletedListenerProcedures listener = new OnRequestCompletedListenerProcedures() {
            @Override
            public void onRequestCompleted(NEW_TASKS_ACTION[] actions) {

            }

            @Override
            public void onRequestCompleted(stp_users[] stp_users) {

            }

            @Override
            public void onRequestCompleted(SYSCODMTI[] SYSCODMTIS) {

            }

            @Override
            public void onRequestCompleted(Boolean deleteResult) {
                proceduresInterface.addAction(deleteResult);
            }

            @Override
            public void onRequestCompleted(String result) {

            }
        };
        repository.addAction(action, listener);
    }

    public void getPdfName(int taskID, int actionID){
        OnRequestCompletedListenerProcedures listener = new OnRequestCompletedListenerProcedures() {
            @Override
            public void onRequestCompleted(NEW_TASKS_ACTION[] actions) {

            }

            @Override
            public void onRequestCompleted(stp_users[] stp_users) {

            }

            @Override
            public void onRequestCompleted(SYSCODMTI[] SYSCODMTIS) {

            }

            @Override
            public void onRequestCompleted(Boolean deleteResult) {

            }

            @Override
            public void onRequestCompleted(String result) {
                proceduresInterface.pdfName(result);
            }
        };
        repository.pdfName(taskID, actionID, listener);
    }
}
