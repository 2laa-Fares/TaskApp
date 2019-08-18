package com.example.apit.task.repositories.imp;

import com.example.apit.task.app.App;
import com.example.apit.task.model.HOUSING_BUILDING_DATA;
import com.example.apit.task.model.NATIONALITY;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.network.ApiClient;
import com.example.apit.task.network.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.apit.task.utils.StringToSubstring.StringToSubstring2;

public class FormsDropdownRepository {
    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();

    public FormsDropdownRepository(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    public void houses(final OnRequestCompleteListenerForms forms, int housingId){
        disposable.add(
                apiService.Eskan(housingId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String strings) {
                                String house = StringToSubstring2(strings);
                                JSONObject obj;
                                HOUSING_BUILDING_DATA housing_building = new HOUSING_BUILDING_DATA();
                                    try {
                                        obj = new JSONObject(house);
                                        ObjectMapper m = new ObjectMapper();
                                        housing_building = (m.readValue(obj.toString(), HOUSING_BUILDING_DATA.class));
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                forms.onRequestComplete(housing_building);
                            }

                            @Override
                            public void onError(Throwable e) {
                                forms.onRequestComplete((HOUSING_BUILDING_DATA) null);
                            }
                        })
        );
    }

    public void nationalities(final OnRequestCompleteListenerForms forms) {
        disposable.add(
                apiService.nationalities()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String[]>() {
                            @Override
                            public void onSuccess(String[] strings) {
                                String[] nationalities = new String[strings.length];
                                for(int i = 0; i<strings.length; i++){
                                    String string = StringToSubstring2(strings[i]);
                                    nationalities[i] = string;
                                }
                                JSONObject obj;

                                NATIONALITY[] nationalities1 = new NATIONALITY[nationalities.length];
                                for(int i=0; i<nationalities.length; i++ ) {
                                    try {
                                        obj = new JSONObject(nationalities[i]);
                                        ObjectMapper m = new ObjectMapper();
                                        nationalities1[i] = (m.readValue(obj.toString(), NATIONALITY.class));
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                forms.onRequestComplete(nationalities1);
                            }

                            @Override
                            public void onError(Throwable e) {
                                forms.onRequestComplete((NATIONALITY[]) null);
                            }
                        })
        );
    }
}
