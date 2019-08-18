package com.example.apit.task.network;

import java.util.Date;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("login/")
    Single<String> login(@Query("name") String userName, @Query("pass") String pass);

    @GET("Tasks/")
    Single<String[]> tasks(@Query("User_ID") String userID , @Query("Category_ID") String categoryID , @Query("Task_Type") String taskType);

    @GET("Task/")
    Single<String> task(@Query("taskID") String taskID , @Query("userID") String  userID , @Query("type") String type);

    @GET("Task/")
    Single<String> getTask(@Query("taskID") String taskID );

    @GET("Category/")
    Single<String> category(@Query("category_No") String categoryNo);

    @POST("EditTask/")
    Single<String> editTask(@Query("Task_ID") String taskID);

    @GET("Actions/")
    Single<String[]> actions(@Query("Task_ID") String taskID);

    @POST("TakeAction/")
    Single<String> takeAction(@Query("TaskNO") String taskID);

    @POST("UploadAttachment")
    Single<String> uploadAttachment(@Query("Task_ID") String taskID);

    @GET("Attachments/")
    Single<String[]> attachments(@Query("Task_ID") String taskID);

    @GET("Dropdown/")
    Single<String[]> drowdown(@Query("type") String type);

    @GET("TaskDropdown?type=Result")
    Single<String[]> taskStatus();

    @GET("TaskDropdown?type=Survey")
    Single<String[]> taskSurvey();

    @GET("TaskDropdown?type=See")
    Single<String[]> checkBoxes();

    @GET("TaskDropdown?type=Category")
    Single<String[]> taskCategory();

    @GET("TaskDropdown")
    Single<String[]> taskNameList(@Query("Task_Category") String category, @Query("Type") String task);

    @GET("TaskDropdown?type=TaskRequest")
    Single<String[]> taskRequest();

    @GET("TaskDropdown?type=Priority")
    Single<String[]> taskPriority();

    @GET("TaskDropdown")
    Single<String[]> mainOfSubTask(@Query("Task_Category") String taskCategory, @Query("Task_ID") String taskID);

    @GET("TaskDropdown?type=House")
    Single<String[]> houseID();

    @GET("TaskDropdown?type=Nationality")
    Single<String[]> nationalities();

    @GET("TaskDropdown")
    Single<String[]> userResponsible(@Query("Task_Category") String taskCategory, @Query("type") String user);

    @GET("TaskDropdown?type=Procedures")
    Single<String[]> procedures();

    @GET("TaskDropdown?type=Users")
    Single<String[]> by();

    @GET("TaskAction")
    Single<String[]> taskActions(@Query("TaskNO") String taskNO);

    @GET("TaskDropdown?type=Guide")
    Single<String[]> guideInfo();

    @GET("Questionnaires")
    Single<Boolean> questionnaires(@Query("obj") String object, @Query("type") String objType , @Query("action") String actionType);

    @GET("Actions")
    Single<String[]> taskAction(@Query("Task_ID") String taskNO);

    @GET("Actions")
    Single<Boolean> deleteActions(@Query("SYSKEY") String syskey, @Query("MODIFIED_USERKEY") String userId, @Query("type") String TYPE);

    @GET("Actions")
    Single<Boolean> addAction(@Query("ACTION") String action);

    @GET("Actions")
    Single<String[]> taskNameList2(@Query("SYSKEY") String syskey, @Query("TYPE") String taskNameList);

    @GET("Eskan")
    Single<String> Eskan(@Query("HouseSyskey") int syskey);

    @GET("HDFiles")
    Single<String[]> getFiles(@Query("task_id") int task_id);

    @GET("LackOfServiceTab")
    Single<String[]> get03_03Files(@Query("HOUSING_SYSKEY") String HOUSING_SYSKEY);

    @GET("Questionnaires")
    Single<String> pdfName(@Query("task_id") int taskID, @Query("action_id") int actionID);
}
