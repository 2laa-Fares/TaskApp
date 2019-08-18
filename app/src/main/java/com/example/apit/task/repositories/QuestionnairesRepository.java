package com.example.apit.task.repositories;

public interface QuestionnairesRepository {
    void questionnaires(String object, String objType,  String actionType, OnRequestCompletedListener listener);
}
