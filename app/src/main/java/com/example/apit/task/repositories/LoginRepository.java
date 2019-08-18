package com.example.apit.task.repositories;


public interface LoginRepository {

    void login(String userName, String pass, OnRequestCompletedListener listener);
}
