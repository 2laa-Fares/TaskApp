package com.example.apit.task.repositories.imp;

import com.example.apit.task.app.App;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.NEW_TASKS_ACTION;
import com.example.apit.task.network.ApiClient;
import com.example.apit.task.network.ApiService;
import com.example.apit.task.repositories.PoceduresRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.apit.task.utils.StringToSubstring.StringToSubstring2;

public class ProceduresRepositoryImp implements PoceduresRepository{
    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ProceduresRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }


    @Override
    public void procedures(String taskNO, final OnRequestCompletedListenerProcedures listener) {
        disposable.add(
                apiService.taskActions(taskNO)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String[]>() {
                            @Override
                            public void onSuccess(String[] strings) {
                                String[] taskActions = new String[strings.length];
                                for(int i = 0; i<strings.length; i++){
                                    String string = StringToSubstring2(strings[i]);
                                    taskActions[i] = string;
                                }
                                JSONObject obj;
                                NEW_TASKS_ACTION[] actions = new NEW_TASKS_ACTION[taskActions.length];
                                for(int i=0; i<taskActions.length; i++ ) {
                                    try {
                                        obj = new JSONObject(taskActions[i]);
                                        ObjectMapper m = new ObjectMapper();
                                        actions[i] = (m.readValue(obj.toString(), NEW_TASKS_ACTION.class));
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                listener.onRequestCompleted(actions);
                            }

                            @Override
                            public void onError(Throwable e) {

                                listener.onRequestCompleted((NEW_TASKS_ACTION[]) null);
                            }
                        })
        );
    }

    @Override
    public void taskNameList2(String syskey, final OnRequestCompletedListenerProcedures listener) {
        disposable.add(
                apiService.taskNameList2(syskey, "TASK_NAME_LIST")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String[]>() {
                            @Override
                            public void onSuccess(String[] strings) {
                                String[] taskActions = new String[strings.length];
                                for(int i = 0; i<strings.length; i++){
                                    String string = StringToSubstring2(strings[i]);
                                    taskActions[i] = string;
                                }
                                JSONObject obj;
                                SYSCODMTI[] actions = new SYSCODMTI[taskActions.length];
                                for(int i=0; i<taskActions.length; i++ ) {
                                    try {
                                        obj = new JSONObject(taskActions[i]);
                                        ObjectMapper m = new ObjectMapper();
                                        actions[i] = (m.readValue(obj.toString(), SYSCODMTI.class));
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                listener.onRequestCompleted(actions);
                            }

                            @Override
                            public void onError(Throwable e) {

                                listener.onRequestCompleted((SYSCODMTI[]) null);
                            }
                        })
        );
    }

    @Override
    public void deleteAction(String syskey, String userId, final OnRequestCompletedListenerProcedures listener) {
        disposable.add(
                apiService.deleteActions(syskey, userId, "DELETE")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Boolean>() {

                            @Override
                            public void onSuccess(Boolean aBoolean) {
                                listener.onRequestCompleted(aBoolean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                listener.onRequestCompleted(false);
                            }
                        })
        );
    }


    @Override
    public void addAction(String action, final OnRequestCompletedListenerProcedures listener) {
        disposable.add(
                apiService.addAction(action)
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



    @Override
    public void pdfName(int taskID, int actionID, final OnRequestCompletedListenerProcedures listener) {
        disposable.add(
                apiService.pdfName(taskID,actionID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String s) {
                                listener.onRequestCompleted(s);
                            }

                            @Override
                            public void onError(Throwable e) {
                                listener.onRequestCompleted("failed to get pdf name");
                            }
                        })
        );
    }
}
