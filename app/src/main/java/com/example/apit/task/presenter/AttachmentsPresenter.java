package com.example.apit.task.presenter;

import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.FileInfo;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.Tasks;
import com.example.apit.task.model.stp_users;
import com.example.apit.task.repositories.AttachmentsRepository;
import com.example.apit.task.repositories.OnRequestCompletedListener;
import com.example.apit.task.view.AttachmentsInterface;

public class AttachmentsPresenter {
    AttachmentsRepository attachmentsRepository;
    AttachmentsInterface attachmentsInterface;

    public AttachmentsPresenter(AttachmentsRepository attachmentsRepository, AttachmentsInterface attachmentsInterface){
        this.attachmentsRepository = attachmentsRepository;
        this.attachmentsInterface = attachmentsInterface;
    }

    public void getAttachments(String taskNo){
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

            }

            @Override
            public void onRequestComplete(FileInfo[] fileInfos) {
                if (fileInfos != null){
                    attachmentsInterface.result(fileInfos);
                }else {
                    attachmentsInterface.result(null);
                }
            }

            @Override
            public void onRequestComplete(FailureFixing03_03[] _03_03Files) {

            }
        };
        attachmentsRepository.getAttachments(listener, taskNo);
    }
}
