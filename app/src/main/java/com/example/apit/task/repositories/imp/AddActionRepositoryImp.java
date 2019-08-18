package com.example.apit.task.repositories.imp;

import com.example.apit.task.app.App;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.stp_users;
import com.example.apit.task.network.ApiClient;
import com.example.apit.task.network.ApiService;
import com.example.apit.task.repositories.AddActionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.apit.task.utils.StringToSubstring.StringToSubstring2;

public class AddActionRepositoryImp implements AddActionRepository{
    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();

    public AddActionRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }


    @Override
    public void procedures(final OnRequestCompletedListenerProcedure listenerProcedure) {
        disposable.add(
                apiService.procedures()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String[]>() {
                            @Override
                            public void onSuccess(String[] strings) {
                                String[] procedures = new String[strings.length];
                                for(int i = 0; i<strings.length; i++){
                                    String string = StringToSubstring2(strings[i]);
                                    procedures[i] = string;
                                }
                                JSONObject obj;
                                SYSCODMTI[] taskProcedures = new SYSCODMTI[procedures.length];
                                for(int i=0; i<procedures.length; i++ ) {
                                    try {
                                        obj = new JSONObject(procedures[i]);
                                        ObjectMapper m = new ObjectMapper();
                                        taskProcedures[i] = (m.readValue(obj.toString(), SYSCODMTI.class));
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                listenerProcedure.onRequestComplete(taskProcedures);
                            }

                            @Override
                            public void onError(Throwable e) {
                                listenerProcedure.onRequestComplete((SYSCODMTI [])null);
                            }
                        })
        );
    }

    @Override
    public void by(final OnRequestCompletedListenerProcedure listenerProcedure) {
        disposable.add(
                apiService.by()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String[]>() {
                            @Override
                            public void onSuccess(String[] strings) {
                                String[] byUsers = new String[strings.length];
                                for(int i = 0; i<strings.length; i++){
                                    String string = StringToSubstring2(strings[i]);
                                    byUsers[i] = string;
                                }
                                JSONObject obj;
                                stp_users[] by = new stp_users[byUsers.length];
                                for(int i=0; i<byUsers.length; i++ ) {
                                    try {
                                        obj = new JSONObject(byUsers[i]);
                                        ObjectMapper m = new ObjectMapper();
                                        by[i] = (m.readValue(obj.toString(), stp_users.class));
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                listenerProcedure.onRequestComplete(by);
                            }

                            @Override
                            public void onError(Throwable e) {
                                listenerProcedure.onRequestComplete((stp_users[])null);
                            }
                        })
        );
    }
}
