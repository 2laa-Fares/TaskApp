package com.example.apit.task.repositories.imp;

import android.content.Context;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.network.ApiClient;
import com.example.apit.task.network.ApiService;
import com.example.apit.task.repositories.OnRequestCompletedListener;
import com.example.apit.task.repositories.TasksRepository;
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

public class TasksRepositoryImp implements TasksRepository {
    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();
    Context context;

    public TasksRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
        context = App.getContext();
    }

    @Override
    public void tasks(final OnRequestCompletedListener listener) {
        String userKey = PrefUtils.getKeys(context, context.getString(R.string.user_id));
        String category = PrefUtils.getKeys(context, context.getString(R.string.user_task_category));
        String selected = PrefUtils.getKeys(context , context.getString(R.string.search_selected));
        disposable.add(
                apiService.tasks(userKey, category , selected)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String[]>() {
                    @Override
                    public void onSuccess(String[] tasks) {
                        String[] tasksJson = new String[tasks.length];
                        for(int i = 0; i<tasks.length; i++){
                            String string = StringToSubstring2(tasks[i]);
                            tasksJson[i] = string;
                        }
                        JSONObject obj;
                        Tasks[] results = new Tasks[tasksJson.length];
                        for(int i=0; i<tasksJson.length; i++ ) {
                            try {
                                obj = new JSONObject(tasksJson[i]);
                                ObjectMapper m = new ObjectMapper();
                                results[i] = (m.readValue(obj.toString(), Tasks.class));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (JsonParseException e) {
                                e.printStackTrace();
                            } catch (JsonMappingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        listener.onRequestCompleted(results);

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onRequestCompleted((Tasks[]) null);
                    }
                })
        );
    }
}
