package com.example.apit.task.repositories;

import com.example.apit.task.model.Guide_Info;
import com.example.apit.task.model.HOUSING_BUILDING_DATA;
import com.example.apit.task.model.STP_TASKS;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.stp_users;

public interface OnRequestCompleteListenerDetails {
    void onRequestCompleted(SYSCODMTI[] dropdown);
    void onRequestCompleted(STP_TASKS[] stp_tasks);
    void onRequestCompleted(HOUSING_BUILDING_DATA[] housing_building_data);
    void onRequestCompleted(Guide_Info[] guide_info);
}
