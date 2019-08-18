package com.example.apit.task.repositories.imp;

import com.example.apit.task.model.HOUSING_BUILDING_DATA;
import com.example.apit.task.model.NATIONALITY;

public interface OnRequestCompleteListenerForms {
    void onRequestComplete(HOUSING_BUILDING_DATA housing_building_data);
    void onRequestComplete(NATIONALITY[] nationalities);

}
