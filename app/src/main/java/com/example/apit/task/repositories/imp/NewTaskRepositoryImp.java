package com.example.apit.task.repositories.imp;

import android.content.Context;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.network.ApiClient;
import com.example.apit.task.network.ApiService;
import com.example.apit.task.repositories.NewTaskRepository;
import com.example.apit.task.repositories.OnRequestCompletedListener;
import com.example.apit.task.utils.PrefUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.apit.task.utils.StringToSubstring.StringToSubstring2;

public class NewTaskRepositoryImp implements NewTaskRepository {
    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();
    Context context;

    public NewTaskRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
        context = App.getContext();
    }

    @Override
    public void task(String taskID, final String type , final OnRequestCompletedListener listener){
        String userKey = PrefUtils.getKeys(context, context.getString(R.string.user_id));
        disposable.add(
                apiService.task(taskID , userKey , type)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String tempTask) {
                                if (type.equals("Show")) {
                                    String response = StringToSubstring2(tempTask);
                                    JSONObject obj = null;
                                    NEW_TASKS task = null;
                                    try {
                                        Context context = App.getContext();
                                        obj = new JSONObject(response);
                                        ObjectMapper m = new ObjectMapper();
                                        task = m.readValue(obj.toString(), NEW_TASKS.class);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (JsonParseException e) {
                                        e.printStackTrace();
                                    } catch (JsonMappingException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    listener.onRequestCompleted(task);
                                }else {
                                    listener.onRequestCompleted(tempTask);
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                if (type.equals("Show")) {
                                    listener.onRequestCompleted((NEW_TASKS) null);
                                }else {
                                    listener.onRequestCompleted("false");
                                }
                            }
                        })
        );
    }

    @Override
    public void getTask(String taskID, final OnRequestCompletedListener listener) {
        disposable.add(
                apiService.getTask(taskID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String tempTask) {
                                    String response = StringToSubstring2(tempTask);
                                    JSONObject obj = null;
                                    NEW_TASKS task = null;
                                    try {
                                        Context context = App.getContext();
                                        obj = new JSONObject(response);
                                        ObjectMapper m = new ObjectMapper();
                                        task = m.readValue(obj.toString(), NEW_TASKS.class);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (JsonParseException e) {
                                        e.printStackTrace();
                                    } catch (JsonMappingException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    listener.onRequestCompleted(task);
                            }
                            @Override
                            public void onError(Throwable e) {
                                listener.onRequestCompleted((NEW_TASKS) null);
                            }
                        })
        );
    }

    @Override
    public void category(final OnRequestCompletedListener listener) {
        final String catKey = PrefUtils.getKeys(context, context.getString(R.string.user_task_category));
        disposable.add(
                apiService.category(catKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String tempCat) {
                                    String response = StringToSubstring2(tempCat);
                                    JSONObject obj = null;
                                    Category category = null;
                                    try {
                                        Context context = App.getContext();
                                        obj = new JSONObject(response);
                                        ObjectMapper m = new ObjectMapper();
                                        category = m.readValue(obj.toString(), Category.class);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (JsonParseException e) {
                                        e.printStackTrace();
                                    } catch (JsonMappingException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    listener.onRequestCompleted(category);
                            }
                            @Override
                            public void onError(Throwable e) {
                                listener.onRequestCompleted((Category) null);
                            }
                        })
        );
    }

    public void dispose() {
        disposable.dispose();
    }
}
