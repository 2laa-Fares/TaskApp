package com.example.apit.task.repositories;

public interface AttachmentsRepository {
    void getAttachments(OnRequestCompletedListener listener, String taskNo);
}
