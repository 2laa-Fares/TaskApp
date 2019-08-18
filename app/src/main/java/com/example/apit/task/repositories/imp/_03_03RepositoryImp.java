package com.example.apit.task.repositories.imp;

import android.content.Context;

import com.example.apit.task.app.App;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.network.ApiClient;
import com.example.apit.task.network.ApiService;
import com.example.apit.task.repositories.OnRequestCompletedListener;
import com.example.apit.task.repositories._03_03Repository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.apit.task.utils.StringToSubstring.StringToSubstring2;

public class _03_03RepositoryImp implements _03_03Repository {

    private ApiService apiService;
    private Context context= App.getContext();

    private CompositeDisposable disposable = new CompositeDisposable();

    public _03_03RepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    @Override
    public void get03_03(final OnRequestCompletedListener listener, String houseNo) {
        disposable.add(
                apiService.get03_03Files(houseNo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String[]>() {
                            @Override
                            public void onSuccess(String[] strings) {
                                String[] result = new String[strings.length];
                                for(int i = 0; i<strings.length; i++){
                                    String string = StringToSubstring2(strings[i]);
                                    result[i] = string;
                                }
                                JSONObject obj;
                                FailureFixing03_03[] _03_03Files = new FailureFixing03_03[result.length];
                                for(int i=0; i<result.length; i++ ) {
                                    try {
                                        obj = new JSONObject(result[i]);
                                        ObjectMapper m = new ObjectMapper();
                                        _03_03Files[i] = (m.readValue(obj.toString(), FailureFixing03_03.class));
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                listener.onRequestComplete(_03_03Files);
                            }

                            @Override
                            public void onError(Throwable e) {
                                listener.onRequestComplete((FailureFixing03_03[])null);
                            }
                        })
        );
    }
}
