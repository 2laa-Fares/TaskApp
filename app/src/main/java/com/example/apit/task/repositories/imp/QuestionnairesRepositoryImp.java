package com.example.apit.task.repositories.imp;

import com.example.apit.task.app.App;
import com.example.apit.task.network.ApiClient;
import com.example.apit.task.network.ApiService;
import com.example.apit.task.repositories.OnRequestCompletedListener;
import com.example.apit.task.repositories.QuestionnairesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class QuestionnairesRepositoryImp implements QuestionnairesRepository{
    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();

    public QuestionnairesRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    @Override
    public void questionnaires(String object, String objType, String actionType,  final OnRequestCompletedListener listener) {
        disposable.add(
                apiService.questionnaires(object , objType, actionType)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Boolean>() {

                            @Override
                            public void onSuccess(Boolean s) {
                                listener.onRequestCompleted(s);
                            }

                            @Override
                            public void onError(Throwable e) {
                                listener.onRequestCompleted(false);
                            }
                        })
        );
    }

    public void dispose(){
        disposable.dispose();
    }
}
