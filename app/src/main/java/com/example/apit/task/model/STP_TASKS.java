package com.example.apit.task.model;

public class STP_TASKS {
    public int TASK_ID;
    public String TASK_NAME;
    public String TASK_DATE_TYPE;
    public Integer TASK_DATE;
    public Integer TASK_TYPE;
    public Integer TASK_CATEGORY;
    public boolean TASK_DALEY;
    public boolean ACTIVE;

    @Override
    public String toString() {
        return TASK_NAME;
    }
}
