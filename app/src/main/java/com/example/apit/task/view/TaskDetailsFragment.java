package com.example.apit.task.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.apit.task.R;
import com.example.apit.task.model.NEW_TASKS;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskDetailsFragment extends Fragment {

    @BindView(R.id.taskcategorySpinner)
    TextView taskCategorySpinner;
    @BindView(R.id.taskNameListSpinner)
    TextView  taskNameSpinner;
    @BindView(R.id.taskDescriptionEditText)
    TextView  taskDescriptionET;
    @BindView(R.id.taskRequestSpinner)
    TextView  taskRequestSpinner;
    @BindView(R.id.taskPrioritySpinner)
    TextView  taskPrioritySpinner;
    @BindView(R.id.task_start_time)
    TextView  task_start_time;
    @BindView(R.id.task_end_time)
    TextView  task_end_time;
    @BindView(R.id.dayStartSpinner)
    TextView  dayStartSpinner;
    @BindView(R.id.monthStartSpinner)
    TextView  monthStartSpinner;
    @BindView(R.id.YearStartSpinner)
    TextView  yearStartSpinner;
    @BindView(R.id.dayEndSpinner)
    TextView  dayEndSpinner;
    @BindView(R.id.monthEndSpinner)
    TextView  monthEndSpinner;
    @BindView(R.id.yearEndSpinner)
    TextView  yearEndSpinner;
    @BindView(R.id.followTaskCheckBox)
    CheckBox followTaskCheckBox;
    @BindView(R.id.taskChoices)
    TextView taskChoiceTV;
    @BindView(R.id.taskChoicesName)
    TextView taskChoiceSpinner;
    @BindView(R.id.guideNameSpinner)
    TextView  guideNameSpinner;
    @BindView(R.id.guidePhoneEditText)
    TextView  guidePhoneET;
    @BindView(R.id.houseIDsSpinner)
    TextView  houseIDsSpinner;
    @BindView(R.id.pilgrimsNoEditText)
    TextView  pilgrimsNoET;
    @BindView(R.id.arrivalTimeEditText)
    TextView  arrivalTimeET;
    @BindView(R.id.userResponsibleSpinner)
    TextView  userResponsibleSpinner;
    @BindView(R.id.notes)
    TextView  notesEditText;
    @BindView(R.id.textViewGuideGame)
    TextView guideNameTV;
    @BindView(R.id.textViewGuidePhone)
    TextView guidePhoneTV;
    NEW_TASKS task;

    HashMap<String, String> month = new HashMap<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_details_fragment, container, false);
        ButterKnife.bind(this, view);
/*
        TextView toolbatTitle =((TaskActivity) getActivity()).findViewById(R.id.mytext);
        toolbatTitle.setText(" تفصيل المهمة");*/

            task = (NEW_TASKS) getArguments().getSerializable("Task");

            if(task != null) {
                if (task.CATEGORY!=null)
                    taskCategorySpinner.setText(task.CATEGORY.toString());
                if (task.NAME!=null)
                    taskNameSpinner.setText(task.NAME.toString());
                if (task.TASK_DESCRIPTION!=null)
                    taskDescriptionET.setText(task.TASK_DESCRIPTION.toString());
                if (task.TASK_REQUEST!=null)
                    taskRequestSpinner.setText(task.TASK_REQUEST.toString());
                if (task.TASK_PRIORITY!=null)
                    taskPrioritySpinner.setText(task.TASK_PRIORITY.toString());

                SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
                if (task.StartTime != null) {
                    String start_time = localDateFormat.format(task.StartTime);
                    task_start_time.setText(start_time);
                }

                if (task.FinishedTime != null) {
                    String end_time = localDateFormat.format(task.FinishedTime);
                    task_end_time.setText(end_time);
                }

                if(task.TASK_START_DATE_HIJRI != null){
                    String[] separated = task.TASK_START_DATE_HIJRI.split("/");
                    yearStartSpinner.setText(separated[0]);
                    monthStartSpinner.setText(getMonth(separated[1]));
                    dayStartSpinner.setText(separated[2]);
                }

                if(task.TASK_END_DATE_HIJRI != null){
                    String[] separated = task.TASK_END_DATE_HIJRI.split("/");
                    yearEndSpinner.setText(separated[0]);
                    String month = getMonth(separated[1]);
                    monthEndSpinner.setText(month);
                    dayEndSpinner.setText(separated[2]);
                }

                if(task.SUBTASK != null && task.SUBTASK == true){
                    followTaskCheckBox.setChecked(true);
                    taskChoiceTV.setVisibility(View.VISIBLE);
                    taskChoiceSpinner.setVisibility(View.VISIBLE);
                    taskChoiceSpinner.setText(task.MAIN_TASK);
                }

                if(task.CATEGORY.equals("قطاع النقل والتصعيد")){
                    guideNameSpinner.setVisibility(View.VISIBLE);
                    guideNameTV.setVisibility(View.VISIBLE);
                    guidePhoneET.setVisibility(View.VISIBLE);
                    guidePhoneTV.setVisibility(View.VISIBLE);
                    if(task.Guide_Name != null) {
                        guideNameSpinner.setText(task.Guide_Name.toString());
                    }
                    if(task.Guide_Phone_No != null) {
                        guidePhoneET.setText(task.Guide_Phone_No.toString());
                    }
                }

                if(task.HOUSING_BUILDING_ID != null) {
                    houseIDsSpinner.setText(task.HOUSING_BUILDING_ID.toString());
                }

                if(task.PILGRIMS_Num != null) {
                    pilgrimsNoET.setText(task.PILGRIMS_Num.toString());
                }

                if (task.Tour_Arrival_Time != null) {
                    String arrival_time = localDateFormat.format(task.Tour_Arrival_Time);
                    task_end_time.setText(arrival_time);
                }

                if(task.USER_NAME != null) {
                    userResponsibleSpinner.setText(task.USER_NAME.toString());
                }

                if(task.NOTES != null) {
                    notesEditText.setText(task.NOTES.toString());
                }
            }


        return view;
    }

    private String getMonth(String no){
        if(no.trim().equals("01"))
            return "محرم";
        if(no.trim().equals("02"))
            return "صفر";
        if(no.trim().equals("03"))
            return "ربيع الأول";
        if(no.trim().equals("04"))
            return "ربيع الثانى";
        if(no.trim().equals("05"))
            return "جمادي الأولى";
        if(no.trim().equals("06"))
            return "جمادي الآخرة";
        if(no.trim().equals("07"))
            return "رجب";
        if(no.trim().equals("08"))
            return "شعبان";
        if(no.trim().equals("09"))
            return "رمضان";
        if(no.trim().equals("10"))
            return "شوال";
        if(no.trim().equals("11"))
            return "ذو القعدة";
        if(no.trim().equals("12"))
            return "ذو الحجة";
        return null;
    }

}
