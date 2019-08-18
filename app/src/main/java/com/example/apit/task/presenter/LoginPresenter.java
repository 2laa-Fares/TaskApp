package com.example.apit.task.presenter;

import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.FileInfo;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.model.stp_users;
import com.example.apit.task.repositories.LoginRepository;
import com.example.apit.task.repositories.OnRequestCompletedListener;
import com.example.apit.task.view.LoginInterface;

public class LoginPresenter {
    LoginInterface loginInterface;
    LoginRepository loginRepository;

    public LoginPresenter(LoginInterface loginInterface, LoginRepository loginRepository){
        this.loginInterface = loginInterface;
        this.loginRepository = loginRepository;
    }

    public void loginUser(String userName, String pass){
        OnRequestCompletedListener listener = new OnRequestCompletedListener() {
            @Override
            public void onRequestCompleted(String user) {
                if(user == null){
                    loginInterface.NotUserFound("من فضلك ادخل اسم المستخدم الخاص بك والبسورد بشكل صحيح.");
                }else {
                    loginInterface.RetrieveUser(user);
                }
            }

            @Override
            public void onRequestCompleted(Tasks[] tasks) {

            }

            @Override
            public void onRequestCompleted(NEW_TASKS task) {

            }

            @Override
            public void onRequestCompleted(Category category) {

            }

            @Override
            public void onRequestCompleted(SYSCODMTI[] dropdowns) {

            }

            @Override
            public void onRequestCompleted(stp_users[] stp_users) {

            }

            @Override
            public void onRequestCompleted(Boolean result) {

            }

            @Override
            public void onRequestComplete(FileInfo[] fileInfos) {

            }

            @Override
            public void onRequestComplete(FailureFixing03_03[] _03_03Files) {

            }
        };
        loginRepository.login(userName, pass, listener);

    }
}
