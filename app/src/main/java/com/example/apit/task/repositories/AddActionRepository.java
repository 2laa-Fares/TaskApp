package com.example.apit.task.repositories;

import com.example.apit.task.repositories.imp.OnRequestCompletedListenerProcedure;

public interface AddActionRepository {
    void procedures(OnRequestCompletedListenerProcedure listenerProcedure);
    void by(OnRequestCompletedListenerProcedure listenerProcedure);
}
