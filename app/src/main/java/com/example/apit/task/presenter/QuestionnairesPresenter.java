package com.example.apit.task.presenter;

import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.FileInfo;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.model.stp_users;
import com.example.apit.task.repositories.OnRequestCompletedListener;
import com.example.apit.task.repositories.QuestionnairesRepository;
import com.example.apit.task.view.QuestionnairesInterface;

public class QuestionnairesPresenter {
    QuestionnairesInterface questionnairesInterface;
    QuestionnairesRepository questionnairesRepository;

    public QuestionnairesPresenter(QuestionnairesInterface questionnairesInterface, QuestionnairesRepository questionnairesRepository){
        this.questionnairesInterface = questionnairesInterface;
        this.questionnairesRepository = questionnairesRepository;
    }

    public void questionnaires(String obj, String type, String action){
        OnRequestCompletedListener listener = new OnRequestCompletedListener() {
            @Override
            public void onRequestCompleted(String user) {
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
                if(!result){
                    questionnairesInterface.NotSaved("من فضلك ادخل جميع الحقول بشكل صحيح.");
                }else {
                    questionnairesInterface.SaveObject("");
                }
            }

            @Override
            public void onRequestComplete(FileInfo[] fileInfos) {

            }

            @Override
            public void onRequestComplete(FailureFixing03_03[] _03_03Files) {

            }
        };
        questionnairesRepository.questionnaires(obj, type, action,  listener);

    }
}
