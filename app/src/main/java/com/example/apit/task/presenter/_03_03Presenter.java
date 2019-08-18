package com.example.apit.task.presenter;

import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.FileInfo;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.model.stp_users;
import com.example.apit.task.repositories.OnRequestCompletedListener;
import com.example.apit.task.repositories._03_03Repository;
import com.example.apit.task.view._03_03Interface;

public class _03_03Presenter {
    _03_03Repository _03_03repository;
    _03_03Interface _03_03interface;

    public _03_03Presenter(_03_03Repository _03_03repository, _03_03Interface _03_03interface){
        this._03_03repository = _03_03repository;
        this._03_03interface = _03_03interface;
    }

    public void get03_03(String houseNo){
        OnRequestCompletedListener listener = new OnRequestCompletedListener() {

            @Override
            public void onRequestCompleted(String user) {

            }

            @Override
            public void onRequestCompleted(Tasks[] tasks) {

            }

            @Override
            public void onRequestCompleted(NEW_TASKS task) {

            }

            @Override
            public void onRequestCompleted(Category category) {

            }

            @Override
            public void onRequestCompleted(SYSCODMTI[] dropdowns) {

            }

            @Override
            public void onRequestCompleted(stp_users[] stp_users) {

            }

            @Override
            public void onRequestCompleted(Boolean result) {

            }

            @Override
            public void onRequestComplete(FileInfo[] fileInfos) {
            }

            @Override
            public void onRequestComplete(FailureFixing03_03[] _03_03Files) {
                if (_03_03Files != null){
                    _03_03interface.result(_03_03Files);
                }else {
                    _03_03interface.result(null);
                }

            }
        };
        _03_03repository.get03_03(listener, houseNo);
    }
}
