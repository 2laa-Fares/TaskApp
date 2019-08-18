package com.example.apit.task.view;

import com.example.apit.task.model.HOUSING_BUILDING_DATA;
import com.example.apit.task.model.NATIONALITY;

public interface HousingInterface {
    void houses(HOUSING_BUILDING_DATA housing_building);
    void nationalities(NATIONALITY[] nationalities);
}
