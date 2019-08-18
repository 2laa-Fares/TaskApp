package com.example.apit.task.presenter;

import com.example.apit.task.model.HOUSING_BUILDING_DATA;
import com.example.apit.task.model.NATIONALITY;
import com.example.apit.task.repositories.imp.FormsDropdownRepository;
import com.example.apit.task.repositories.imp.OnRequestCompleteListenerForms;
import com.example.apit.task.view.HousingInterface;

import java.util.List;

public class FormsPresenter {
    HousingInterface housingInterface;
    public FormsPresenter(HousingInterface housingInterface){
        this.housingInterface = housingInterface;
    }
    public void getHouses(int housingId){
        OnRequestCompleteListenerForms forms = new OnRequestCompleteListenerForms() {
            @Override
            public void onRequestComplete(HOUSING_BUILDING_DATA housing_building_data) {
                housingInterface.houses(housing_building_data);
            }

            @Override
            public void onRequestComplete(NATIONALITY[] nationalities) {

            }
        };
        new FormsDropdownRepository().houses(forms, housingId);
    }

    public void getNationality(){
        OnRequestCompleteListenerForms forms = new OnRequestCompleteListenerForms() {
            @Override
            public void onRequestComplete(HOUSING_BUILDING_DATA housing_building_data) {

            }

            @Override
            public void onRequestComplete(NATIONALITY[] nationalities) {
                housingInterface.nationalities(nationalities);
            }
        };
        new FormsDropdownRepository().nationalities(forms);
    }
}
