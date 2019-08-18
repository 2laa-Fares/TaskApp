package com.example.apit.task.repositories.imp;

import android.content.Context;

import com.example.apit.task.app.App;
import com.example.apit.task.model.FileInfo;
import com.example.apit.task.network.ApiClient;
import com.example.apit.task.network.ApiService;
import com.example.apit.task.repositories.AttachmentsRepository;
import com.example.apit.task.repositories.OnRequestCompletedListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.apit.task.utils.StringToSubstring.StringToSubstring2;

public class AttachmentsRepositoryImp implements AttachmentsRepository {

    private ApiService apiService;
    private Context context= App.getContext();

    private CompositeDisposable disposable = new CompositeDisposable();

    public AttachmentsRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    @Override
    public void getAttachments(final OnRequestCompletedListener listener, String taskNo) {
        disposable.add(
                apiService.getFiles(Integer.valueOf(taskNo))
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
                                FileInfo[] files = new FileInfo[result.length];
                                for(int i=0; i<result.length; i++ ) {
                                    try {
                                        obj = new JSONObject(result[i]);
                                        ObjectMapper m = new ObjectMapper();
                                        files[i] = (m.readValue(obj.toString(), FileInfo.class));
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                listener.onRequestComplete(files);
                            }

                            @Override
                            public void onError(Throwable e) {
                                listener.onRequestComplete((FileInfo[])null);
                            }
                        })
        );
    }
}
