package com.example.apit.task.repositories.imp;

import android.content.Context;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.STP_TASKS;
import com.example.apit.task.model.stp_users;
import com.example.apit.task.network.ApiClient;
import com.example.apit.task.network.ApiService;
import com.example.apit.task.repositories.LoginRepository;
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


public class LoginRepositoryImp implements LoginRepository {
    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();
    String user;

    public LoginRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    @Override
    public void login(String userName, String pass, final OnRequestCompletedListener listener){
        disposable.add(
                apiService.login(userName, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String mUser) {
                        String response = StringToSubstring2(mUser);
                        JSONObject obj = null;
                        try {
                            Context context = App.getContext();
                            obj = new JSONObject(response);
                            ObjectMapper m = new ObjectMapper();
                            stp_users user1 = m.readValue(obj.toString(), stp_users.class);
                            String id = String.valueOf(user1.USER_NO);
                            String cat = String.valueOf(user1.TASK_CATEGORY_NO);
                            PrefUtils.storeKeys(context, context.getString(R.string.user_id), id);
                            PrefUtils.storeKeys(context,context.getString(R.string.user_task_category),cat);
                            PrefUtils.storeKeys(context,context.getString(R.string.search_selected),"1");
                            PrefUtils.storeKeys(context, context.getString(R.string.login_key), "true");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (JsonParseException e) {
                            e.printStackTrace();
                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        listener.onRequestCompleted(mUser);
                    }
                    @Override
                    public void onError(Throwable e) {
                        listener.onRequestCompleted(user);
                    }
                })
        );
    }

    public void dispose(){
        disposable.dispose();
    }
}
